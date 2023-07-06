package com.example.Backend.validation.helpers;

import com.example.Backend.schema.ChapterInfo;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ChapterValidation {

    @Autowired
    private ValidationUtils validationUtils;
    public void checkForChapterMandatoryData(ChapterInfo chapterInfo) throws InputNotLogicallyValidException {
        // bookId
//        validationUtils.checkForNull("bookId",chapterInfo.getChapterId());
//
//        );
//        // title
    }
}
