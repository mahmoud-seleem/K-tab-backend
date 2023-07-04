package com.example.Backend.validation;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.BookRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.Book;
import com.example.Backend.utils.ImageConverter;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ValidationUtils {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ImageConverter imageConverter;

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
            String name = fieldNames.get(i);
            if (fieldValues.get(i).replace(" ", "").length() == 0) {
                throw new InputNotLogicallyValidException(
                        name,
                        name + " can't be BLANK \" \" !");
            }
        }
    }

    public void checkForEmptyItems(List<String> fieldNames, List<String> fieldValues) throws InputNotLogicallyValidException {
        for (int i = 0; i < fieldNames.size(); i++) {
            String name = fieldNames.get(i);
            if (fieldValues.get(i).length() == 0) {
                throw new InputNotLogicallyValidException(
                        name,
                        name + " can't be EMPTY \"\" !");
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

    private  UUID getIdFromBookCoverPath(String bookCoverPath) throws InputNotLogicallyValidException {
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
    public  Book checkBookIsExisted(UUID bookId) throws InputNotLogicallyValidException {
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
}
