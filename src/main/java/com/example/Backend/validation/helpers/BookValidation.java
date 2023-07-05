package com.example.Backend.validation.helpers;


import com.example.Backend.model.Book;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookValidation {
    @Autowired
    private ValidationUtils validationUtils;

    public void checkForBookMandatoryData(BookInfo bookInfo) throws InputNotLogicallyValidException {
        checkForValidTitle(true, bookInfo.getTitle());
    }

    public Book checkForBookOptionalData(boolean update ,BookInfo bookInfo) throws InputNotLogicallyValidException {
        validationUtils.checkForValidPrice(bookInfo.getPrice());
        Map<String, Object> data = createOptionalBookFields(bookInfo);
        validationUtils.checkForEmptyBlankItems(
                (List<String>) data.get("names"),
                (List<String>) data.get("values"));
        validationUtils.checkForValidDateFormat(bookInfo.getPublishDate());
        validationUtils.checkForValidBinaryPhoto(
                "bookCoverPhotoAsBinaryString",
                bookInfo.getBookCoverPhotoAsBinaryString()
        );
        if (update) {
            return validationUtils.checkBookIsExisted(bookInfo.getBookId());
        } else return null;
    }

    private Map<String, Object> createOptionalBookFields(BookInfo bookInfo) {
        Map<String, Object> result = new HashMap<>();
        List<String> fieldsNames = new ArrayList<>();
        List<String> fieldsValues = new ArrayList<>();
        fieldsNames.addAll(Arrays.asList(
                "title",
                "bookAbstract",
                "publishDate"));
        fieldsValues.addAll(Arrays.asList(
                bookInfo.getTitle(),
                bookInfo.getBookAbstract(),
                bookInfo.getPublishDate()
        ));
        for (int i = 0; i < bookInfo.getTags().size(); i++) {
            fieldsNames.add("tags[" + i + "]");
            fieldsValues.add(bookInfo.getTags().get(i));
        }
        result.put("names", fieldsNames);
        result.put("values", fieldsValues);
        return result;
    }

    public void checkForValidTitle(boolean mandatory, String title) throws InputNotLogicallyValidException {
        if (mandatory) {
            validationUtils.checkForNullEmptyAndBlankString("title", title);
        } else {
            validationUtils.checkForEmptyAndBlankString("title", title);
        }
    }
}
