package com.example.Backend.service;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.s3Connection.AccessType;
import com.example.Backend.s3Connection.S3PreSignedURL;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.*;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.example.Backend.validation.helpers.BookValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookService {
    @Autowired
    private BookValidation bookValidation;
    @Autowired
    private ValidationUtils validationUtils;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private S3fileSystem s3fileSystem;
    @Autowired
    private Utils utils;

    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private S3PreSignedURL s3PreSignedURL;

    public BookInfo saveNewBook(BookInfo bookInfo) throws Exception {
        bookValidation.checkForBookMandatoryData(bookInfo);
        bookValidation.checkForBookOptionalData(false,bookInfo);
        Book book = createNewBook(bookInfo);
        Author author = authorRepository.findById(bookInfo.getAuthorId()).get();
        author.addBook(book);
        authorRepository.save(author);
        return createBookInfoResponse(
                bookRepository.save(updateLastEditDate(book)));
    }

    public BookInfo updateBookInfo(BookInfo bookInfo) throws Exception {
        Book book = bookValidation.checkForBookOptionalData(true,bookInfo);
        Author author = authorRepository.findById(bookInfo.getAuthorId()).get();
        if (!author.getAuthorBooksList().contains(book)){
            throw new InputNotLogicallyValidException(
                    "bookId",
                    "this author is not the owner of this book");
        }
        updateBookData(book, bookInfo);
        return createBookInfoResponse(
                bookRepository.save(updateLastEditDate(book)));
    }

    public BookInfo getBookInfo(UUID bookId) throws InputNotLogicallyValidException {
        validationUtils.checkForNull("bookId",bookId);
        return createBookInfoResponse(
                validationUtils.checkBookIsExisted(bookId));
    }

    private Book createNewBook(BookInfo bookInfo) throws Exception {
        Book book = new Book(
                bookInfo.getTitle(),
                bookInfo.getPrice(),
                bookInfo.getBookAbstract()
        );
        bookRepository.save(book);
        updateBookData(book, bookInfo);
        return book;
    }

    public String getPreSignedAsString(String bookCoverPath) throws InputNotLogicallyValidException {
//        validationUtils.checkForValidBookCoverPath(bookCoverPath);
        return s3PreSignedURL.generatePreSignedUrl(
                bookCoverPath,
                60,
                AccessType.READ
        ).toString();
    }

    private void updateBookData(Book book, BookInfo bookInfo) throws Exception {
        for (Field field : bookInfo.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(bookInfo) != null) {
                System.out.println(field.getName());
                switch (field.getName()) {
                    case "tags": {
                        setupBookTags(book, bookInfo.getTags());
                        break;
                    }
                    case "publishDate":
                    case "lastEditDate": {
                        utils.getMethodBySignature("set", field, book, LocalDateTime.class)
                                .invoke(book, LocalDateTime.parse(field.get(bookInfo).toString(), Utils.formatter));
                        break;
                    }
                    case "authorId": {
                        book.setAuthor(authorRepository.findById(bookInfo.getAuthorId()).get());
                        break;
                    }
                    case "bookCoverPhotoAsBinaryString": {
                        break;
                    }
                    default: {
                        utils.getMethodBySignature("set", field, book, field.getType())
                                .invoke(book, field.get(bookInfo));
                    }
                }
            }
        }
        setupBookCover(book, bookInfo);
    }

    private BookInfo createBookInfoResponse(Book book) {
        BookInfo response = new BookInfo(
                book.getAuthor().getAuthorId(),
                book.getBookId(),
                book.getTitle(),
                null,
                book.getBookAbstract(),
                book.getTagsNames(),
                book.getBookCover(),
                book.getPublishDateAsString(),
                book.getLastEditDateAsString(),
                book.getPrice(),
                book.calculateAvgRating(),
                createChapterHeaders(book),
                book.getContributorsEmails());
        return response;
    }

    protected Book updateLastEditDate(Book book) {
        book.setLastEditDate(LocalDateTime.parse(LocalDateTime.now().
                format(Utils.formatter), Utils.formatter));
        return book;
    }

    private String setupBookCover(Book book, BookInfo bookInfo) {
        String photoPath = storeCoverPhotoPath(book);
        if (bookInfo.getBookCoverPhotoAsBinaryString() != null) {
            InputStream inputStream = imageConverter.convertImgToFile(
                    bookInfo.getBookCoverPhotoAsBinaryString());
            s3fileSystem.uploadPhoto(photoPath, inputStream);
        }
        return photoPath;
    }

    private String storeCoverPhotoPath(Book book) {
        String photoPath = ("Books/" + book.getBookId().toString() + "/coverPhoto.png");
        s3fileSystem.reserveEmptyPlace(photoPath);
        book.setBookCover(photoPath);
        return photoPath;
    }

    private Book setupBookTags(Book book, List<String> tags) {
        book.clearTags();
        Tag tag = null;
        for (String tagName : tags) {
            if (tagRepository.findByName(tagName).isEmpty()) {
                tag = tagRepository.save(new Tag(tagName));
            } else {
                tag = tagRepository.findByName(tagName).get();
                tag.removeBook(book);
            }
            tag.addBook(book);
        }
        tagRepository.save(tag);
        Book b = bookRepository.save(book);
        Tag newTag = tagRepository.findByName(tag.getTagName()).get();
        System.out.println("____________________________________________");
        for (Tag t : b.getTags()) {
            System.out.println(t.getTagName());
        }
        System.out.println("____________________________________________");
        for (Book bb : newTag.getBookList()) {
            System.out.println(bb.getTitle());
        }
        System.out.println("____________________________________________");
        return b;
    }

    public BookInfo addContribution(ContributionInfo contributionInfo) throws Exception {
        Book book = bookRepository.findById(
                contributionInfo.getBookId()).get();
        if (book.getAuthor().getAuthorId().equals(
                contributionInfo.getOwnerId())) {
            Author author = authorRepository.
                    findByAuthorEmail(
                            contributionInfo.getContributorEmail()).get();
            setupNewContribution(book, author, contributionInfo);
        } else throw new Exception();
        return createBookInfoResponse(book);
    }

    public BookInfo updateContribution(ContributionInfo contributionInfo) throws Exception {
        Book book = bookRepository.findById(
                contributionInfo.getBookId()).get();
        if (book.getAuthor().getAuthorId().equals(
                contributionInfo.getOwnerId())) {
            Author author = authorRepository.
                    findByAuthorEmail(
                            contributionInfo.getContributorEmail()).get();
            updateContributionData(book, author, contributionInfo);
        } else throw new Exception();
        return createBookInfoResponse(book);
    }

    public BookInfo removeContribution(ContributionInfo contributionInfo) throws Exception {
        Book book = bookRepository.findById(
                contributionInfo.getBookId()).get();
        if (book.getAuthor().getAuthorId().equals(
                contributionInfo.getOwnerId())) {
            Author author = authorRepository.
                    findByAuthorEmail(
                            contributionInfo.getContributorEmail()).get();
            deleteContribution(book, author);
        } else throw new Exception();
        return createBookInfoResponse(book);
    }

    private void setupNewContribution(Book book, Author author, ContributionInfo contributionInfo) {
        Contribution contribution = new Contribution();
        contribution.setChaptersIds(getChapterIds(
                book, contributionInfo));
        author.addContribution(contribution);
        book.addContribution(contribution);
        bookRepository.save(book);
        authorRepository.save(author);
        contributionRepository.save(contribution);
    }

    private void updateContributionData(Book book, Author author, ContributionInfo contributionInfo) {
        Contribution contribution = contributionRepository
                .findByAuthorAndBook(author, book).get();
        contribution.setChaptersIds(getChapterIds(
                book, contributionInfo));
        contributionRepository.save(contribution);
    }

    private void deleteContribution(Book book, Author author) {
        Contribution contribution = contributionRepository
                .findByAuthorAndBook(author, book).get();
        author.removeContribution(contribution);
        book.removeContribution(contribution);
        contributionRepository.delete(contribution);
    }

    private List<UUID> getChapterIds(Book book, ContributionInfo contributionInfo) {
        List<UUID> chapterIds = new ArrayList<>();
        for (int order : contributionInfo.getChaptersOrders()) {
            chapterIds
                    .add(chapterRepository
                            .findByChapterOrderAndBook(order, book)
                            .get()
                            .getChapterId()
                    );
        }
        return chapterIds;
    }

    public BookPage getNextPage(String next, String prev, int limit) {
        List<Book> books = null;
        BookPage page = new BookPage();
        if (next == null && prev == null) {
            books = bookRepository.findTop3ByBookIdGreaterThanOrderByBookId(
                    UUID.fromString("00000000-0000-0000-8000-000000000000"));
            page.setPrev(null);
            if (books.size() == 0 || books.size() != limit + 1) { // the last page or the database is empty
                page.setNext(null);
            } else {
                page.setNext(books.get(books.size() - 2).getBookId());
                books.remove(books.size() - 1);
            }
            return createPageBookHeaders(page, books);
        } else if (next == null) {
            page.setNext(null);
            page.setPrev(UUID.fromString(prev));
            return page;
        } else {
            books = bookRepository.findTop3ByBookIdGreaterThanOrderByBookId(
                    UUID.fromString(next));
            page.setPrev(books.get(0).getBookId());
            if (books.size() != limit + 1) { // the last page
                page.setNext(null);
            } else {
                page.setNext(books.get(books.size() - 2).getBookId());
                books.remove(books.size() - 1);
            }
            return createPageBookHeaders(page, books);
        }
    }

    public BookPage getPrevPage(String next, String prev, int limit) {
        List<Book> books = null;
        BookPage page = new BookPage();
        if (next == null && prev == null) { // always get the first page
            books = bookRepository.findTop3ByBookIdLessThanOrderByBookId(
                    UUID.fromString("ffffffff-ffff-ffff-ffff-ffffffffffff"));
            page.setPrev(null);
            if (books.size() == 0 || books.size() != limit + 1) { // the last page or the database is empty
                page.setNext(null);
            } else {
                page.setNext(books.get(books.size() - 2).getBookId());
                books.remove(books.size() - 1);
            }
            return createPageBookHeaders(page, books);
        } else if (prev == null) { // back to the first page while scrolling down to the top
            page.setPrev(null);
            page.setNext(UUID.fromString(next));
            return page;
        } else {
            books = bookRepository.findTop3ByBookIdLessThanOrderByBookIdDesc(
                    UUID.fromString(prev));
            Collections.reverse(books);
            page.setNext(books.get(books.size() - 1).getBookId());
            if (books.size() != limit + 1) { // the first page
                page.setPrev(null);
            } else {
                books.remove(0);
                page.setPrev(books.get(0).getBookId());
            }
            return createPageBookHeaders(page, books);
        }
    }


    public BookPage getNextPageWithSearch(
            String next,
            String prev,
            int limit,
            String title,
            String tagName,
            String operation) {
        List<Book> books = null;
        BookPage page = new BookPage();
        if (next == null && prev == null) {
            books = getSearchResult(books,
                    "00000000-0000-0000-8000-000000000000",
                    prev, title, tagName, operation, true, true);
            page.setPrev(null);
            if (books.size() == 0 || books.size() != limit + 1) { // the last page or the database is empty
                page.setNext(null);
            } else {
                page.setNext(books.get(books.size() - 2).getBookId());
                books.remove(books.size() - 1);
            }
            return createPageBookHeaders(page, books);
        } else if (next == null) {
            page.setNext(null);
            page.setPrev(UUID.fromString(prev));
            return page;
        } else {
            books = getSearchResult(books, next,
                    prev, title, tagName, operation, true, true);
            page.setPrev(books.get(0).getBookId());
            if (books.size() != limit + 1) { // the last page
                page.setNext(null);
            } else {
                page.setNext(books.get(books.size() - 2).getBookId());
                books.remove(books.size() - 1);
            }
            return createPageBookHeaders(page, books);
        }
    }

    public BookPage getPrevPageWithSearch(String next,
                                          String prev,
                                          int limit,
                                          String title,
                                          String tagName,
                                          String operation) {
        List<Book> books = null;
        BookPage page = new BookPage();
        if (next == null && prev == null) { // always get the first page
            books = getSearchResult(books, next, "ffffffff-ffff-ffff-ffff-ffffffffffff",
                    title, tagName, operation, false, true);
            page.setPrev(null);
            if (books.size() == 0 || books.size() != limit + 1) { // the last page or the database is empty
                page.setNext(null);
            } else {
                page.setNext(books.get(books.size() - 2).getBookId());
                books.remove(books.size() - 1);
            }
            return createPageBookHeaders(page, books);
        } else if (prev == null) { // back to the first page while scrolling down to the top
            page.setPrev(null);
            page.setNext(UUID.fromString(next));
            return page;
        } else {
            books = getSearchResult(books, next, prev,
                    title, tagName, operation, false, false);
            Collections.reverse(books);
            page.setNext(books.get(books.size() - 1).getBookId());
            if (books.size() != limit + 1) { // the first page
                page.setPrev(null);
            } else {
                books.remove(0);
                page.setPrev(books.get(0).getBookId());
            }
            return createPageBookHeaders(page, books);
        }
    }

    private List<Book> getSearchResult(
            List<Book> books,
            String next,
            String prev,
            String title,
            String tagName,
            String operation,
            boolean isNext,
            boolean asc) {
        if (title != null && tagName != null) {
            if (operation.equals("AND")) { // searching with tag and title
                return searchTitleAndTag(title, tagName, isNext, asc, next, prev);
            } else { // searching with tag or title
                return searchTitleOrTag(title, tagName, isNext, asc, next, prev);
            }
        } else if (title == null && tagName != null) { // searching only with tagName
            return searchTag(title, tagName, isNext, asc, next, prev);
        } else if (title != null) { // searching with title only
            return searchTitle(title, tagName, isNext, asc, next, prev);
        } else { // will not search
            return notSearch(title, tagName, isNext, asc, next, prev);
        }
    }

    private List<Book> searchTitleOrTag(String title,
                                        String tagName,
                                        Boolean isNext,
                                        boolean asc,
                                        String next,
                                        String prev) {
        if (isNext) {
            return bookRepository
                    .findTop3ByTitleContainingOrTags_TagNameAndBookIdGreaterThanOrderByBookId(
                            title, tagName, UUID.fromString(next));
        } else {
            if (asc) {
                return bookRepository.findTop3ByTitleContainingOrTags_TagNameAndBookIdLessThanOrderByBookId(
                        title, tagName, UUID.fromString(prev));
            } else {
                return bookRepository.findTop3ByTitleContainingOrTags_TagNameAndBookIdLessThanOrderByBookIdDesc(
                        title, tagName, UUID.fromString(prev));
            }
        }
    }

    private List<Book> searchTitle(String title,
                                   String tagName,
                                   Boolean isNext,
                                   boolean asc,
                                   String next,
                                   String prev) {
        if (isNext) {
            return bookRepository
                    .findTop3ByTitleContainingAndBookIdGreaterThanOrderByBookId(
                            title, UUID.fromString(next));
        } else {
            if (asc) {
                return bookRepository.findTop3ByTitleContainingAndBookIdLessThanOrderByBookId(
                        title, UUID.fromString(prev));
            } else {
                return bookRepository.findTop3ByTitleContainingAndBookIdLessThanOrderByBookIdDesc(
                        title, UUID.fromString(prev));
            }
        }
    }

    private List<Book> searchTag(String title,
                                 String tagName,
                                 Boolean isNext,
                                 boolean asc,
                                 String next,
                                 String prev) {
        if (isNext) {
            return bookRepository
                    .findTop3ByTags_TagNameAndBookIdGreaterThanOrderByBookId(
                            tagName, UUID.fromString(next));
        } else {
            if (asc) {
                return bookRepository.findTop3ByTags_TagNameAndBookIdLessThanOrderByBookId(
                        tagName, UUID.fromString(prev));
            } else {
                return bookRepository.findTop3ByTags_TagNameAndBookIdLessThanOrderByBookIdDesc(
                        tagName, UUID.fromString(prev));
            }
        }
    }

    private List<Book> searchTitleAndTag(String title,
                                         String tagName,
                                         Boolean isNext,
                                         boolean asc,
                                         String next,
                                         String prev) {
        if (isNext) {
            return bookRepository
                    .findTop3ByTitleContainingAndTags_TagNameAndBookIdGreaterThanOrderByBookId(
                            title, tagName, UUID.fromString(next));
        } else {
            if (asc) {
                return bookRepository.findTop3ByTitleContainingAndTags_TagNameAndBookIdLessThanOrderByBookId(
                        title, tagName, UUID.fromString(prev));
            } else {
                return bookRepository.findTop3ByTitleContainingAndTags_TagNameAndBookIdLessThanOrderByBookIdDesc(
                        title, tagName, UUID.fromString(prev));
            }
        }
    }

    private List<Book> notSearch(String title,
                                 String tagName,
                                 Boolean isNext,
                                 boolean asc,
                                 String next,
                                 String prev) {
        if (isNext) {
            return bookRepository
                    .findTop3ByBookIdGreaterThanOrderByBookId(
                            UUID.fromString(next));
        } else {
            if (asc) {
                return bookRepository.findTop3ByBookIdLessThanOrderByBookId(
                        UUID.fromString(prev));
            } else {
                return bookRepository.findTop3ByBookIdLessThanOrderByBookIdDesc(
                        UUID.fromString(prev));
            }
        }
    }

    private BookPage createPageBookHeaders(BookPage page, List<Book> books) {
        for (Book book : books) {
            page.getBookHeaders().add(new BookHeader(
                    book.getBookId(),
                    book.getBookCover(),
                    book.getTitle()));
        }
        return page;
    }

    public List<UUID> getAllBookIds() {
        List<UUID> uuids = new ArrayList<>();
        for (Book book : bookRepository.findALlBooks()) {
            uuids.add(book.getBookId());
        }
        return uuids;
    }

    public List<Map<String, Object>> getAllBookWithTagName(String tagName) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (Book book : bookRepository.findByTags_TagNameOrderByBookId(tagName)) {
            Map<String, Object> map = new HashMap<>();
            map.put("UUID", book.getBookId());
            map.put("title", book.getTitle());
            map.put("tags", book.getTagsNames());
            out.add(map);
        }
        return out;
    }

    public List<Map<String, Object>> getAllBookWithTitle(String title) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (Book book : bookRepository.findByTitleContainingOrderByBookId(title)) {
            Map<String, Object> map = new HashMap<>();
            map.put("UUID", book.getBookId());
            map.put("title", book.getTitle());
            map.put("tags", book.getTagsNames());
            out.add(map);
        }
        return out;
    }

    public List<Map<String, Object>> getAllBookWithTitleAndTag(String title, String tagName) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (Book book : bookRepository.findByTags_TagNameAndTitleContainingOrderByBookId(tagName, title)) {
            Map<String, Object> map = new HashMap<>();
            map.put("UUID", book.getBookId());
            map.put("title", book.getTitle());
            map.put("tags", book.getTagsNames());
            out.add(map);
        }
        return out;
    }

    public List<Map<String, Object>> getAllBookWithTitleOrTag(String title, String tagName) {
        List<Map<String, Object>> out = new ArrayList<>();
        for (Book book : bookRepository.findByTags_TagNameOrTitleContainingOrderByBookId(tagName, title)) {
            Map<String, Object> map = new HashMap<>();
            map.put("UUID", book.getBookId());
            map.put("title", book.getTitle());
            map.put("tags", book.getTagsNames());
            out.add(map);
        }
        return out;
    }

    public StudentBookInfo getStudentBookInfo(
            UUID studentId, UUID bookId) throws InputNotLogicallyValidException {
        validationUtils.checkForNull("bookId",bookId);
        validationUtils.checkForNull("studentId",studentId);
        Student student = validationUtils.checkStudentIsExisted(studentId);
        Book book = validationUtils.checkBookIsExisted(bookId);
        Payment payment = paymentRepository.findByStudentAndBook(
                student, book);
        return createStudentBookInfo(book, payment);
    }

    private StudentBookInfo createStudentBookInfo(Book book
            , Payment payment) {
        if (payment != null) {
            return new StudentBookInfo(
                    book.getBookId(),
                    book.getAuthor().getAuthorId(),
                    book.getTitle(),
                    null,
                    book.getBookAbstract(),
                    book.getTagsNames(),
                    book.getBookCover(),
                    book.getPublishDateAsString(),
                    book.getLastEditDateAsString(),
                    book.getPrice(),
                    book.calculateAvgRating(),
                    createChapterHeaders(book),
                    book.getContributorsEmails(),
                    true,
                    payment.getRecentOpenedDate().format(Utils.formatter),
                    payment.getRecentOpenedChapterId(),
                    payment.getRatingValue());
        } else {
            return new StudentBookInfo(
                    book.getBookId(),
                    book.getAuthor().getAuthorId(),
                    book.getTitle(),
                    null,
                    book.getBookAbstract(),
                    book.getTagsNames(),
                    book.getBookCover(),
                    book.getPublishDateAsString(),
                    book.getLastEditDateAsString(),
                    book.getPrice(),
                    book.calculateAvgRating(),
                    createChapterHeaders(book),
                    book.getContributorsEmails(),
                    false,
                    null,
                    null,
                    null);
        }
    }

    private List<ChapterHeader> createChapterHeaders(Book book) {
        List<ChapterHeader> chapterHeaders = new ArrayList<>();
        for (Chapter chapter : book.getChapters()) {
            chapterHeaders.add(
                    new ChapterHeader(
                            chapter.getChapterId(),
                            chapter.getTitle(),
                            chapter.getChapterOrder()));
        }
        return chapterHeaders;
    }
}
