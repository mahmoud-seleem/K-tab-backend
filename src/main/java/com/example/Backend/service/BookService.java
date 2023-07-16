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
import com.example.Backend.validation.helpers.StudentValidation;
import com.google.j2objc.annotations.AutoreleasePool;
import jdk.jshell.execution.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import javax.imageio.ImageIO;
import java.awt.font.LineMetrics;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class BookService {
    private final int LIMIT = 8;
    private final String[] IMAGES = {Utils.image1,Utils.image2,Utils.image3,Utils.image4,Utils.image5};
    private SecureRandom random = null;
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
    @Autowired
    private StudentValidation studentValidation;

    public BookInfo saveNewBook(BookInfo bookInfo) throws Exception {
        bookValidation.checkForBookMandatoryData(bookInfo);
        bookValidation.checkForBookOptionalData(false, bookInfo);
        Book book = createNewBook(bookInfo);
        Author author = authorRepository.findById(bookInfo.getAuthorId()).get();
        author.addBook(book);
        authorRepository.save(author);
        return createBookInfoResponse(
                bookRepository.save(updateLastEditDate(book)));
    }

    public BookInfo updateBookInfo(BookInfo bookInfo) throws Exception {
        Book book = bookValidation.checkForBookOptionalData(true, bookInfo);
        Author author = authorRepository.findById(bookInfo.getAuthorId()).get();
        validationUtils.checkForBookOwner(author, book);
        updateBookData(book, bookInfo);
        return createBookInfoResponse(
                bookRepository.save(updateLastEditDate(book)));
    }

    public BookInfo getBookInfo(UUID bookId) throws InputNotLogicallyValidException {
        validationUtils.checkForNull("bookId", bookId);
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
    public String downloadAndEncodeImage() throws IOException {
//        URL url = new URL("https://picsum.photos/200/300");
//        URLConnection connection = url.openConnection();
//        InputStream inputStream =  connection.getInputStream();
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        byte[] buffer = new byte[1024];
//        int length;
//
//        while ((length = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, length);
//        }
//
//        byte[] byteArray = outputStream.toByteArray();
//        return Base64.getEncoder().encodeToString(byteArray);
        Random random = new Random();
        int x = random.nextInt(4);
        return IMAGES[x];
    }
    public String generateRandomTitle(){
        random = new SecureRandom();
        String[] titles = {"the","Art","Science","Mathematics","behind","AI"};
        int x = random.nextInt(6);
        int y = random.nextInt(6);
        int z = random.nextInt(6);
        random = null;
        return titles[x] + " " + titles[y] + " " + titles[z];
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
                    case "bookCoverPhotoAsBinaryString":
                    case "bookId": {
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

    public BookInfo createBookInfoResponse(Book book) {
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
                book.getContributionsHeaders());
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
        Book book = validationUtils.checkBookIsExisted(
                contributionInfo.getBookId());
        Author author = validationUtils.checkAuthorIsExisted(
                contributionInfo.getContributorId());
        Author owner = validationUtils.checkAuthorIsExisted(
                contributionInfo.getOwnerId());
        validationUtils.checkForBookOwner(owner, book);
        setupNewContribution(book, author, contributionInfo);
        return createBookInfoResponse(book);
    }

    public BookInfo updateContribution(ContributionInfo contributionInfo) throws Exception {
        Book book = validationUtils.checkBookIsExisted(
                contributionInfo.getBookId());
        Author author = validationUtils.checkAuthorIsExisted(
                contributionInfo.getContributorId());
        Author owner = validationUtils.checkAuthorIsExisted(
                contributionInfo.getOwnerId());
        validationUtils.checkForBookOwner(owner, book);
        updateContributionData(book, author, contributionInfo);
        return createBookInfoResponse(book);
    }

    public BookInfo removeContribution(ContributionInfo contributionInfo) throws Exception {
        Book book = validationUtils.checkBookIsExisted(
                contributionInfo.getBookId());
        Author author = validationUtils.checkAuthorIsExisted(
                contributionInfo.getContributorId());
        Author owner = validationUtils.checkAuthorIsExisted(
                contributionInfo.getOwnerId());
        validationUtils.checkForBookOwner(owner, book);
        deleteContribution(book, author);
        return createBookInfoResponse(book);
    }

    private void setupNewContribution(Book book, Author author, ContributionInfo contributionInfo) throws InputNotLogicallyValidException {
        Contribution contribution = new Contribution();
        contribution.setChaptersIds(getChapterIds(contributionInfo));
        author.addContribution(contribution);
        book.addContribution(contribution);
        bookRepository.save(book);
        authorRepository.save(author);
        contributionRepository.save(contribution);
    }

    private void updateContributionData(Book book, Author author, ContributionInfo contributionInfo) throws InputNotLogicallyValidException {
        Contribution contribution = validationUtils.checkForBookContributor(author, book);
        contribution.setChaptersIds(getChapterIds(
                contributionInfo));
        contributionRepository.save(contribution);
    }

    private void deleteContribution(Book book, Author author) throws InputNotLogicallyValidException {
        Contribution contribution = validationUtils.checkForBookContributor(author, book);
        author.removeContribution(contribution);
        book.removeContribution(contribution);
        contributionRepository.delete(contribution);
    }

    private List<UUID> getChapterIds(ContributionInfo contributionInfo) throws InputNotLogicallyValidException {
        List<UUID> chapterIds = new ArrayList<>();
        validationUtils.checkForNull("chaptersIds",
                contributionInfo.getChaptersIds());
        for (String id : contributionInfo.getChaptersIds()) {
            chapterIds.add(validationUtils.checkChapterIsExisted(
                    UUID.fromString(id)).getChapterId());
        }
        return chapterIds;
    }

    public BookPage getNextPage(String next, String prev, int limit) {
        List<Book> books = null;
        BookPage page = new BookPage();
        if (next == null && prev == null) {
            books = bookRepository.findTop9ByBookIdGreaterThanOrderByBookId(
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
            books = bookRepository.findTop9ByBookIdGreaterThanOrderByBookId(
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
            books = bookRepository.findTop9ByBookIdLessThanOrderByBookId(
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
            books = bookRepository.findTop9ByBookIdLessThanOrderByBookIdDesc(
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

    public BookPage getStudentHome(UUID next,
                                   UUID prev,
                                   int limit,
                                   String title,
                                   String tagName,
                                   String operation,
                                   String filter) throws InputNotLogicallyValidException {
        studentValidation.validateStudentHomeInputs(next,
                prev, limit, title, tagName, operation, filter);
        BookPage res = null;
        try {
            if (operation.equals("next")) {
                res = getNextPageWithSearch(
                        (next == null) ? null : next.toString(),
                        (prev == null) ? null : prev.toString(),
                        LIMIT, title, tagName, filter);
            } else {
                res = getPrevPageWithSearch(
                        (next == null) ? null : next.toString(),
                        (prev == null) ? null : prev.toString(),
                        LIMIT, title, tagName, filter);
            }
        } catch (Exception e) {
            throw new InputNotLogicallyValidException(
                    "prev/next",
                    "bad input ! make sure you pass the correct values or" +
                            " discard them to go back to the first page"
            );
        }
        return res;
    }

    public BookPage getNextPageWithSearch(
            String next,
            String prev,
            int limit,
            String title,
            String tagName,
            String filter) {
        List<Book> books = null;
        BookPage page = new BookPage();
        if (next == null && prev == null) {
            books = getSearchResult(books,
                    "00000000-0000-0000-8000-000000000000",
                    prev, title, tagName, filter, true, true);
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
                    prev, title, tagName, filter, true, true);
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
                                          String filter) {
        List<Book> books = null;
        BookPage page = new BookPage();
        if (next == null && prev == null) { // always get the first page
            books = getSearchResult(books, next, "ffffffff-ffff-ffff-ffff-ffffffffffff",
                    title, tagName, filter, false, true);
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
                    title, tagName, filter, false, false);
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
            String filter,
            boolean isNext,
            boolean asc) {
        if (title != null && tagName != null) {
            if (filter == null || filter.equals("AND")) { // default behavior is AND
                return searchTitleAndTag(title, tagName, isNext, asc, next, prev);
            } else {
                // searching with tag or title
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
                    .findTop9ByTitleContainingOrTags_TagNameAndBookIdGreaterThanOrderByBookId(
                            title, tagName, UUID.fromString(next));
        } else {
            if (asc) {
                return bookRepository.findTop9ByTitleContainingOrTags_TagNameAndBookIdLessThanOrderByBookId(
                        title, tagName, UUID.fromString(prev));
            } else {
                return bookRepository.findTop9ByTitleContainingOrTags_TagNameAndBookIdLessThanOrderByBookIdDesc(
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
                    .findTop9ByTitleContainingAndBookIdGreaterThanOrderByBookId(
                            title, UUID.fromString(next));
        } else {
            if (asc) {
                return bookRepository.findTop9ByTitleContainingAndBookIdLessThanOrderByBookId(
                        title, UUID.fromString(prev));
            } else {
                return bookRepository.findTop9ByTitleContainingAndBookIdLessThanOrderByBookIdDesc(
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
                    .findTop9ByTags_TagNameAndBookIdGreaterThanOrderByBookId(
                            tagName, UUID.fromString(next));
        } else {
            if (asc) {
                return bookRepository.findTop9ByTags_TagNameAndBookIdLessThanOrderByBookId(
                        tagName, UUID.fromString(prev));
            } else {
                return bookRepository.findTop9ByTags_TagNameAndBookIdLessThanOrderByBookIdDesc(
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
                    .findTop9ByTitleContainingAndTags_TagNameAndBookIdGreaterThanOrderByBookId(
                            title, tagName, UUID.fromString(next));
        } else {
            if (asc) {
                return bookRepository.findTop9ByTitleContainingAndTags_TagNameAndBookIdLessThanOrderByBookId(
                        title, tagName, UUID.fromString(prev));
            } else {
                return bookRepository.findTop9ByTitleContainingAndTags_TagNameAndBookIdLessThanOrderByBookIdDesc(
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
                    .findTop9ByBookIdGreaterThanOrderByBookId(
                            UUID.fromString(next));
        } else {
            if (asc) {
                return bookRepository.findTop9ByBookIdLessThanOrderByBookId(
                        UUID.fromString(prev));
            } else {
                return bookRepository.findTop9ByBookIdLessThanOrderByBookIdDesc(
                        UUID.fromString(prev));
            }
        }
    }

    private BookPage createPageBookHeaders(BookPage page, List<Book> books) {
        for (Book book : books) {
            page.getBookHeaders().add(new BookHeader(
                    book.getBookId(),
                    book.getAuthor().getAuthorId(),
                    book.getAuthor().getAuthorName(),
                    book.getBookCover(),
                    book.getTitle(),
                    book.getTagsNames(),
                    book.getBookAbstract(),
                    book.calculateAvgRating(),
                    book.getPrice()));
        }
        page.setNumOfPages(getNumberOfBookPages());
        return page;
    }
    private int getNumberOfBookPages(){
        long books = bookRepository.count();
        if (books == 0){
            return 0;
        }else {
            return ((int)Math.ceil(
                    ((double) books) /  LIMIT));
        }
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
        validationUtils.checkForNull("bookId", bookId);
        validationUtils.checkForNull("studentId", studentId);
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
                    book.getContributionsHeaders(),
                    true,
                    payment.getRecentOpenedDate().format(Utils.formatter),
                    payment.getRecentOpenedChapterId(),
                    payment.getRatingValue());
        } else {
            return new StudentBookInfo(
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
                    book.getContributionsHeaders(),
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

    public StudentBookInfo addRatingValue(UUID studentId, UUID bookId, int rating) throws InputNotLogicallyValidException {
        Student student = validationUtils.checkStudentIsExisted(studentId);
        Book book = validationUtils.checkBookIsExisted(bookId);
        Payment payment = validationUtils.checkPaymentIsExisted(student, book);
        validationUtils.checkForValidRatingValue(rating);
        payment.setRatingValue(rating);
        return createStudentBookInfo(book, paymentRepository.save(payment));
    }

    public List<String> getAllBookTags() {
        List<String> tagsNames = new ArrayList<>();
        List<Tag> tags = tagRepository.findAll();
        for (Tag tag : tags){
            tagsNames.add(tag.getTagName());
        }
        return tagsNames;
    }
}
