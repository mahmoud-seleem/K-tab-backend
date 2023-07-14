package com.example.Backend.validation;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.schema.DisabilityHeader;
import com.example.Backend.security.AppUser;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class ValidationUtils {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ImageConverter imageConverter;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private ContributionRepository contributionRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private InteractionRepository interactionRepository;
    @Autowired
    private CommentRepository commentRepository;

    public <T> void checkForNull(String fieldName, T fieldValue) throws InputNotLogicallyValidException {
        if (fieldValue == null) {
            throw new InputNotLogicallyValidException(
                    fieldName,
                    fieldName + " can't be NULL !");
        }
    }

    public <T> void checkForNullItems(List<String> fieldNames, List<T> fieldValues) throws InputNotLogicallyValidException {
        for (int i = 0; i < fieldNames.size(); i++) {
            String name = fieldNames.get(i);
            if (fieldValues.get(i) == null) {
                throw new InputNotLogicallyValidException(
                        name,
                        name + " can't be NULL !");
            }
        }
    }

    public void checkForBlankItems(List<String> fieldNames, List<String> fieldValues) throws InputNotLogicallyValidException {
        for (int i = 0; i < fieldNames.size(); i++) {
            if (fieldValues.get(i) != null) {
                String name = fieldNames.get(i);
                if (fieldValues.get(i).replace(" ", "").length() == 0) {
                    throw new InputNotLogicallyValidException(
                            name,
                            name + " can't be BLANK \" \" !");
                }
            }
        }
    }

    public void checkForEmptyItems(List<String> fieldNames, List<String> fieldValues) throws InputNotLogicallyValidException {
        for (int i = 0; i < fieldNames.size(); i++) {
            if (fieldValues.get(i) != null) {
                String name = fieldNames.get(i);
                if (fieldValues.get(i).length() == 0) {
                    throw new InputNotLogicallyValidException(
                            name,
                            name + " can't be EMPTY \"\" !");
                }
            }
        }
    }

    public void checkForEmptyString(String fieldName, String fieldValue) throws InputNotLogicallyValidException {
        if (fieldValue.equals("")) {
            throw new InputNotLogicallyValidException(
                    fieldName,
                    fieldName + " can't be EMPTY \"\" !");
        }
    }

    public void checkForBlankString(String fieldName, String fieldValue) throws InputNotLogicallyValidException {
        if (fieldValue.replace(" ", "").length() == 0) {
            throw new InputNotLogicallyValidException(
                    fieldName,
                    fieldName + " can't be BLANK \" \" !");
        }
    }

    public void checkForPasswordLength(String fieldName, String fieldValue) throws InputNotLogicallyValidException {
        if (fieldValue.length() < 8) {
            throw new InputNotLogicallyValidException(
                    fieldName,
                    fieldName + " can't be Less than 8 characters !");
        }
    }

    public void checkForNullEmptyBlankItems(List<String> fieldNames, List<String> fieldValues) throws InputNotLogicallyValidException {
        checkForNullItems(fieldNames, fieldValues);
        checkForEmptyItems(fieldNames, fieldValues);
        checkForBlankItems(fieldNames, fieldValues);
    }

    public void checkForEmptyBlankItems(List<String> fieldNames, List<String> fieldValues) throws InputNotLogicallyValidException {
        checkForEmptyItems(fieldNames, fieldValues);
        checkForBlankItems(fieldNames, fieldValues);
    }

    public void checkAuthorEmailIsNotExisted(String email) throws InputNotLogicallyValidException {
        Author author = null;
        try {
            author = authorRepository.findByAuthorEmail(email).get();
        } catch (Exception ignored) {
        }
        if (author != null) {
            throw new InputNotLogicallyValidException(
                    "author email",
                    "This email is already in use ! pleas, choose another one ");
        }
    }
    public void checkStudentEmailIsNotExisted(String email) throws InputNotLogicallyValidException {
        Student student = null;
        try {
            student = studentRepository.findByStudentEmail(email).get();
        } catch (Exception ignored) {
        }
        if (student != null) {
            throw new InputNotLogicallyValidException(
                    "student email",
                    "This email is already in use ! pleas, choose another one ");
        }
    }

    public Author checkAuthorIsExisted(UUID authorId) throws InputNotLogicallyValidException {
        Author author = null;
        try {
            author = authorRepository.findById(authorId).get();
        } catch (Exception e) {
            throw new InputNotLogicallyValidException(
                    "authorId",
                    "Author does not exist !");
        }
        return author;
    }

    public void checkForEmptyAndBlankString(String fieldName, String fieldValue) throws InputNotLogicallyValidException {
        if (fieldValue != null) {
            checkForEmptyString(fieldName, fieldValue);
            checkForBlankString(fieldName, fieldValue);
        }
    }

    public void checkForNullEmptyAndBlankString(String fieldName, String fieldValue) throws InputNotLogicallyValidException {
        checkForNull(fieldName, fieldValue);
        checkForEmptyString(fieldName, fieldValue);
        checkForBlankString(fieldName, fieldValue);
    }

    public void checkForValidContact(String contact) throws InputNotLogicallyValidException {
        if (contact != null) {
            checkForEmptyAndBlankString("contact", contact);
            try {
                long number = Long.parseLong(contact);
            } catch (Exception e) {
                throw new InputNotLogicallyValidException(
                        "contact",
                        "contact must be valid number ");
            }
        }
    }

    public void checkForValidBinaryPhoto(String fieldName, String binary) throws InputNotLogicallyValidException {
        if (binary != null) {
            checkForEmptyAndBlankString(fieldName, binary);
            imageConverter.checkForValidImage(fieldName, binary);
        }
    }

    public void checkForValidPassword(String password) throws InputNotLogicallyValidException {
        if (password != null) {
            checkForEmptyAndBlankString("password", password);
            checkForPasswordLength("password", password);
        }
    }

    public void checkForValidAuthorName(String authorName) throws InputNotLogicallyValidException {
        if (authorName != null) {
            checkForEmptyAndBlankString("authorName", authorName);
        }
    }

    public void checkForValidAuthorEmail(String authorEmail) throws InputNotLogicallyValidException {
        if (authorEmail != null) {
            checkForEmptyAndBlankString("authorEmail", authorEmail);
        }
    }

    public void checkForValidBookCoverPath(String bookCoverPath) throws InputNotLogicallyValidException {
        if (
                bookCoverPath.startsWith("Books/") &&
                        bookCoverPath.endsWith("/coverPhoto.png")) {
        } else {
            throw new InputNotLogicallyValidException(
                    "bookCoverPath",
                    "Not a valid book cover in s3 bucket ! "
            );
        }
        checkBookIsExisted(getIdFromBookCoverPath(bookCoverPath));
    }

    private UUID getIdFromBookCoverPath(String bookCoverPath) throws InputNotLogicallyValidException {
        bookCoverPath = bookCoverPath.replace("Books/", "");
        bookCoverPath = bookCoverPath.replace("/coverPhoto.png", "");
        try {
            return UUID.fromString(bookCoverPath);
        } catch (Exception e) {
            throw new InputNotLogicallyValidException(
                    "bookCoverPath",
                    "Not a valid book cover in s3 bucket ! "
            );
        }
    }

    public Book checkBookIsExisted(UUID bookId) throws InputNotLogicallyValidException {
        Book book = null;
        try {
            book = bookRepository.findById(bookId).get();
        } catch (Exception e) {
            throw new InputNotLogicallyValidException(
                    "bookId",
                    "Book does not exist !");
        }
        return book;
    }

    public void checkForValidPrice(Double price) throws InputNotLogicallyValidException {
        if (price != null) {
            if (price < 0) {
                throw new InputNotLogicallyValidException(
                        "price",
                        "price must be positive number of zero !"
                );
            }
        }
    }

    public void checkForValidDateFormat(String date) throws InputNotLogicallyValidException {
        if (date != null) {
            try {
                LocalDateTime localDateTime =
                        LocalDateTime.parse(date, Utils.formatter);
            } catch (Exception e) {
                throw new InputNotLogicallyValidException(
                        "Date",
                        "Date must be in this format: " +
                                "yyyy-MM-dd HH:mm:ss"
                );
            }
        }
    }

    public void checkForValidNextAndPrevDates(String next,String prev) throws InputNotLogicallyValidException {
        LocalDateTime nextDate =
                LocalDateTime.parse(next, Utils.formatter);
        LocalDateTime prevData =
                LocalDateTime.parse(prev, Utils.formatter);
        if (!nextDate.isAfter(prevData)){
        throw new InputNotLogicallyValidException(
                        "next/prev",
                        "next pointer must be less than prev pointer "
                );
            }
        }



    public Student checkStudentIsExisted(UUID studentId) throws InputNotLogicallyValidException {
        Student student = null;
        try {
            student = studentRepository.findById(studentId).get();
        } catch (Exception e) {
            throw new InputNotLogicallyValidException(
                    "studentId",
                    "student does not exist !");
        }
        return student;
    }
    public Chapter checkChapterIsExisted(UUID chapterId) throws InputNotLogicallyValidException {
        Chapter chapter = null;
        try {
            chapter = chapterRepository.findById(chapterId).get();
        } catch (Exception e) {
            throw new InputNotLogicallyValidException(
                    "chapter",
                    "chapter does not exist !");
        }
        return chapter;
    }

    public <T> void checkForEmptyList(String listName, List<T> list) throws InputNotLogicallyValidException {
        if (list != null) {
            if (list.size() == 0) {
                throw new InputNotLogicallyValidException(
                        listName,
                        listName + " can't be empty list " +
                                "if you don't want to provide items in the list" +
                                " please don't send the entire field"
                );
            }
        }
    }
    public <T> void checkForListSize(String listName, List<T> list,int size) throws InputNotLogicallyValidException {
        if (list != null) {
            if (list.size() > size) {
                throw new InputNotLogicallyValidException(
                        listName,
                        listName + " size can't be more than " + size
                );
            }
        }
    }
    public <T>void checkForDuplicationInList(String listName , List<T> list) throws InputNotLogicallyValidException {
        if(list != null){
            Set<T> items = new HashSet<T>();
            items.addAll(list);
            if (items.size() != list.size()) {
                throw new InputNotLogicallyValidException(
                        listName,
                        listName + " can't have duplicate items !"
                );
            }
        }
    }

    public UUID checkForValidUUIDString(String fieldName, String id) throws InputNotLogicallyValidException {
        UUID res = null;
        try {
            res = UUID.fromString(id);
        } catch (Exception e) {
            throw new InputNotLogicallyValidException(
                    fieldName,
                    fieldName + " is NOT a Valid UUID"
            );
        }
        return res;
    }
    public void checkForBookOwner(Author author, Book book) throws InputNotLogicallyValidException {
        if (!author.getAuthorBooksList().contains(book)){
            throw new InputNotLogicallyValidException(
                    "book/author",
                    "this author is not the owner of this book");
        }
    }
    public void checkForBookOwnerOrContributor(Author author, Book book) throws InputNotLogicallyValidException {
        if (!author.getAuthorBooksList().contains(book)  && !(book.getContributorsIds().contains(author.getAuthorId().toString()))){
            throw new InputNotLogicallyValidException(
                    "book/author",
                    "this author is not the owner or contributor of this book");
        }
    }
    public Contribution checkForBookContributor(Author author, Book book) throws InputNotLogicallyValidException {
        Contribution contribution = null;
        try{
            contribution = contributionRepository
                    .findByAuthorAndBook(author, book).get();
        }catch (Exception e){
            throw new InputNotLogicallyValidException(
                    "book/author",
                    "this author is not a contributor of this book");
        }
        return contribution;
    }
    public void checkForChapterOwner(Author author, Chapter chapter) throws InputNotLogicallyValidException {
        if (!author.getAuthorBooksList().contains(chapter.getBook())){
            throw new InputNotLogicallyValidException(
                    "chapter/author",
                    "this author is not the owner of this chapter or");
        }
    }

    private void checkForValidDisabilityName(String name) throws InputNotLogicallyValidException {
        checkForNullEmptyAndBlankString("disability name",name);
        checkForKnownDisability(name);
    }
    private void checkForKnownDisability(String name) throws InputNotLogicallyValidException {
        if (!(name.equals("Blind") || name.equals("Visually_Impaired") || name.equals("Dyslexia"))){
            throw new InputNotLogicallyValidException(
                    "disability name",
                    "disability name must be one of these values : [Blind ,Visually_Impaired ,Dyslexia] !"
            );
        }
    }
    public void checkForValidDisabilityHeader(DisabilityHeader header) throws InputNotLogicallyValidException {
        checkForValidDisabilityName(header.getName());
        checkForEmptyAndBlankString("disability details",header.getDetails());
    }
    public void checkForValidOperationName(String operation) throws InputNotLogicallyValidException {
        checkForNullEmptyAndBlankString("operation",operation);
        if (!(operation.equals("next") || operation.equals("prev"))){
            throw new InputNotLogicallyValidException(
                    "operation",
                    "operation name must be next or prev"
            );
        }
    }
    public void checkForValidFilterName(String filter) throws InputNotLogicallyValidException {
        checkForEmptyAndBlankString("filter",filter);
        if (filter != null){
            if (!(filter.equals("AND") || filter.equals("OR"))){
                throw new InputNotLogicallyValidException(
                        "filter",
                        "filter name must be AND / OR [case sensitive]"
                );
            }
        }
    }
    public void checkForPositiveQuantity(String fieldName,int value) throws InputNotLogicallyValidException {
        if (value < 0){
            throw new InputNotLogicallyValidException(
                    fieldName,
                    fieldName +" can't be negative !"
            );
        }
    }
    public void validateNextIsGreaterThanPrev(UUID next,UUID prev) throws InputNotLogicallyValidException {
        if (next.compareTo(prev) < 0){
            throw new InputNotLogicallyValidException(
                    "next/prev",
                    "next pointer can't be less than prev pointer "
            );
        }
    }
    public void checkPaymentIsNotExisted(Student student,Book book) throws InputNotLogicallyValidException {
        Payment payment = null;
        try {
            payment = paymentRepository.findByStudentAndBook(student,book);
        }catch (Exception ignored){
        }
        if (payment != null){
            throw new InputNotLogicallyValidException(
                    "student/book",
                    "student have already bought this book !"
            );
        }
    }
    public void checkFavIsNotExisted(Student student,Book book) throws InputNotLogicallyValidException {
        Favourite favourite = null;
        try {
            favourite = favouriteRepository.findByBookAndStudent(book,student);
        }catch (Exception ignored){
        }
        if (favourite != null){
            throw new InputNotLogicallyValidException(
                    "student/book",
                    "this book is already in student's favourites !"
            );
        }
    }
    public Payment checkPaymentIsExisted(Student student,Book book) throws InputNotLogicallyValidException {
        Payment payment = null;
        try {
            payment = paymentRepository.findByStudentAndBook(student,book);
        }catch (Exception ignored){
        }
        if (payment == null){
            throw new InputNotLogicallyValidException(
                    "student/book",
                    "student didn't buy this book !"
            );
        }else return payment;
    }
    public Favourite checkFavIsExisted(Student student,Book book) throws InputNotLogicallyValidException {
        Favourite favourite = null;
        try {
            favourite = favouriteRepository.findByBookAndStudent(book,student);
        }catch (Exception ignored){
        }
        if (favourite == null){
            throw new InputNotLogicallyValidException(
                    "student/book",
                    "this book is not in student's favourites !"
            );
        }else return favourite;
    }
    public Interaction checkInteractionIsExisted(UUID interactionId) throws InputNotLogicallyValidException {
        Interaction interaction = null;
        try {
            interaction = interactionRepository.findById(interactionId).get();
        }catch (Exception ignored){
        }
        if (interaction == null){
            throw new InputNotLogicallyValidException(
                    "interactionId",
                    "interaction is not existed !"
            );
        }else return interaction;
    }
    public Comment checkCommentIsExisted(UUID commentId) throws InputNotLogicallyValidException {
        Comment comment = null;
        try {
            comment = commentRepository.findById(commentId).get();
        }catch (Exception ignored){
        }
        if (comment == null){
            throw new InputNotLogicallyValidException(
                    "commentId",
                    "comment is not existed !"
            );
        }else return comment;
    }

    public String checkUserIsExisted(String userName,UUID userId) throws InputNotLogicallyValidException {
        String type;
        AppUser user = null;
        try{
            user = this.checkAuthorIsExisted(userId);
        }catch (Exception ignored){}
        if (user != null){
            type = "AUTHOR";
        }
        else {
            try{
                user = checkStudentIsExisted(userId);
            }catch (Exception ignored){}
            if (user != null){
                type = "STUDENT";
            }
            else {
                throw new InputNotLogicallyValidException(
                        userName,
                        userName +" is not existed !"
                );
            }
        }
        return type;
    }
    public void checkForValidCommentsLimit(int limit) throws InputNotLogicallyValidException {
        if (limit <= 0){
            throw new InputNotLogicallyValidException(
                    "limit",
                    "comments limit can't be less than 1"
            );
        }
    }

    public void checkForValidRatingValue(int value) throws InputNotLogicallyValidException {
        if (value < 1 || value > 5 ){
            throw new InputNotLogicallyValidException(
                    "rating",
                    "rating must be from 1 to 5 "
            );
        }
    }

}
