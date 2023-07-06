package com.example.Backend.validation.helpers;

import com.example.Backend.model.Author;
import com.example.Backend.model.Book;
import com.example.Backend.model.Chapter;
import com.example.Backend.schema.AuthorProfile;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.schema.ChapterInfo;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ChapterValidation {

    @Autowired
    private ValidationUtils validationUtils;
    public Book checkForCreationMandatoryData(ChapterInfo chapterInfo) throws InputNotLogicallyValidException {
//        validationUtils.checkForNull("bookId",chapterInfo.getBookId());
        validationUtils.checkForNullEmptyAndBlankString("title",chapterInfo.getTitle());
        Book book = validationUtils.checkBookIsExisted(chapterInfo.getBookId());
        Author author = validationUtils.checkAuthorIsExisted(chapterInfo.getOwnerId());
        validationUtils.checkForBookOwner(author,book);
        return book;
    }
    public Chapter checkForUpdateMandatoryData(ChapterInfo chapterInfo) throws InputNotLogicallyValidException {
//        validationUtils.checkForNull("chapterId",chapterInfo.getChapterId());
//        Book book = validationUtils.checkBookIsExisted(chapterInfo.getBookId());
        Author author = validationUtils.checkAuthorIsExisted(chapterInfo.getOwnerId());
        Chapter chapter = validationUtils.checkChapterIsExisted(chapterInfo.getChapterId());
        validationUtils.checkForChapterOwner(author,chapter);
        return chapter;
    }
    private Map<String, Object> createOptionalChapterFields(ChapterInfo chapterInfo) {
        Map<String, Object> result = new HashMap<>();
        List<String> fieldsNames = new ArrayList<>();
        List<String> fieldsValues = new ArrayList<>();
        fieldsNames.add("title");
        fieldsValues.add(chapterInfo.getTitle());
        if(chapterInfo.getTags() != null){
            for (int i = 0; i < chapterInfo.getTags().size(); i++) {
                fieldsNames.add("tags[" + i + "]");
                fieldsValues.add(chapterInfo.getTags().get(i));
            }
        }
        result.put("names", fieldsNames);
        result.put("values", fieldsValues);
        return result;
    }
    public void checkForChapterOptionalData(ChapterInfo chapterInfo) throws InputNotLogicallyValidException {
        validationUtils.checkForEmptyList("tags",chapterInfo.getTags());
        Map<String, Object> data = createOptionalChapterFields(chapterInfo);
        validationUtils.checkForEmptyBlankItems(
                (List<String>) data.get("names"),
                (List<String>) data.get("values"));
    }
    public Book validateChapterCreation(ChapterInfo chapterInfo) throws InputNotLogicallyValidException {
        Book book = checkForCreationMandatoryData(chapterInfo);
        checkForChapterOptionalData(chapterInfo);
        return book;
    }
    public Chapter validateChapterUpdate(ChapterInfo chapterInfo) throws InputNotLogicallyValidException {
        Chapter chapter = checkForUpdateMandatoryData(chapterInfo);
        checkForChapterOptionalData(chapterInfo);
        return chapter;
    }
}
