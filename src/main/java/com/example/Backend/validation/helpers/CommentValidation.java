package com.example.Backend.validation.helpers;

import com.example.Backend.model.Author;
import com.example.Backend.model.Chapter;
import com.example.Backend.model.Student;
import com.example.Backend.schema.CommentInfo;
import com.example.Backend.security.AppUser;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommentValidation {
    @Autowired
    private ValidationUtils validationUtils;

    public AppUser validateCommentCreationData(CommentInfo commentInfo) throws InputNotLogicallyValidException {
        AppUser appUser;
        Chapter chapter = validationUtils.checkChapterIsExisted(commentInfo.getChapterId());
        if (commentInfo.getCommenterType().equals("STUDENT")){
            appUser = validationUtils.checkStudentIsExisted(commentInfo.getStudentId());
            validationUtils.checkPaymentIsExisted((Student) appUser,chapter.getBook());
        }else {
            appUser =  validationUtils.checkAuthorIsExisted(commentInfo.getAuthorId());
            validationUtils.checkForBookOwnerOrContributor((Author) appUser,chapter.getBook());
        }
        validationUtils.checkForNullEmptyAndBlankString("content",commentInfo.getContent());
        validationUtils.checkForNull("hasMentions",commentInfo.getHasMentions());
            if (commentInfo.getHasMentions()){
                validationUtils.checkForNull("mentionedUsers",commentInfo.getMentionedUsers());
                validationUtils.checkForEmptyList("mentionedUsers",commentInfo.getMentionedUsers());
            } // else we will ignore the mentioned users
    return appUser;
    }
    public void validateCommentPageInputs(String next,
                                          String prev,
                                          int limit,
                                          String operation,
                                          UUID chapterId) throws InputNotLogicallyValidException {
        validationUtils.checkForValidOperationName(operation);
        validationUtils.checkForValidCommentsLimit(limit);
        validationUtils.checkChapterIsExisted(chapterId);
        if (next != null){
            validationUtils.checkForValidDateFormat(next);
        }
        if (prev != null){
            validationUtils.checkForValidDateFormat(prev);
        }
        if (next != null && prev != null){
            validationUtils.checkForValidDateFormat(next);
            validationUtils.checkForValidDateFormat(prev);
            validationUtils.checkForValidNextAndPrevDates(next,prev);
        }
    }
}
