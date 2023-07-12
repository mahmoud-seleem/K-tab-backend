package com.example.Backend.schema;

import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentInfo {

    private String commentId;



    private String authorId;

    private String studentId;

    private String commenterId;
    private String chapterId;

    private String content;
    private Boolean hasMentions;

    private List<String> mentionedUsers;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String date;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String commenterType;


    public CommentInfo(UUID commentId, String commenterType, UUID authorId, UUID studentId, UUID chapterId, String content, String date, Boolean hasMentions, List<String> mentionedUsers) {
        this.commentId = commentId.toString();
        this.commenterType = commenterType;
        this.authorId = (authorId == null) ? null : (authorId.toString());
        this.studentId = (studentId == null) ? null : (studentId.toString());
        this.chapterId = chapterId.toString();
        this.content = content;
        this.date = date;
        this.hasMentions = hasMentions;
        this.mentionedUsers = mentionedUsers;
    }

    public CommentInfo(UUID commentId, UUID commenterId, UUID chapterId, String content, Boolean hasMentions, List<String> mentionedUsers, String date, String commenterType) {
        this.commentId = commentId.toString();
        this.commenterId =(commenterId == null) ? null : (commenterId.toString());
        this.chapterId = chapterId.toString();
        this.content = content;
        this.hasMentions = hasMentions;
        this.mentionedUsers = mentionedUsers;
        this.date = date;
        this.commenterType = commenterType;
    }

    public CommentInfo() {
    }

    public UUID getCommentId() {
        return convertStringToUUID(commentId);
    }

    public void setCommentId(String commentId) throws InputNotLogicallyValidException {
        validateUUIDString("commentId",commentId);
        this.commentId = commentId;
    }

    public void setCommentId(UUID commentId){
        this.commentId = commentId.toString();
    }
    public String getCommenterType() {
        return commenterType;
    }

    public void setCommenterType(String commenterType) {
        this.commenterType = commenterType;
    }

    public UUID getAuthorId() {
        return convertStringToUUID(authorId);
    }

    public void setAuthorId(String authorId) throws InputNotLogicallyValidException {
        validateUUIDString("authorId",authorId);
        this.authorId = authorId;
    }
    public void setAuthorId(UUID authorId){
        this.authorId = authorId.toString();
    }
    public UUID getStudentId() {
        return convertStringToUUID(studentId);
    }

    public void setStudentId(String studentId) throws InputNotLogicallyValidException {
        validateUUIDString("studentId",studentId);
        this.studentId = studentId;
    }
    public void setStudentId(UUID studentId)  {
        this.studentId = studentId.toString();
    }

    public UUID getChapterId() {
        return convertStringToUUID(chapterId);
    }

    public void setChapterId(String chapterId) throws InputNotLogicallyValidException {
        validateUUIDString("chapterId",chapterId);
        this.chapterId = chapterId;
    }
    public void setChapterId(UUID chapterId){
        this.chapterId = chapterId.toString();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getHasMentions() {
        return hasMentions;
    }

    public void setHasMentions(Boolean hasMentions) {
        this.hasMentions = hasMentions;
    }

    public List<String> getMentionedUsers() {
        return mentionedUsers;
    }

    public void setMentionedUsers(List<String> mentionedUsers) throws InputNotLogicallyValidException {
        if(mentionedUsers != null){
            ValidationUtils validationUtils = new ValidationUtils();
            validationUtils.checkForEmptyList("mentionedUsers",mentionedUsers);
            for(int i = 0 ; i<mentionedUsers.size();i++){
                validateUUIDString("mentionedUsers["+i+"]" ,mentionedUsers.get(i));
            }
        }
        this.mentionedUsers = mentionedUsers;
    }

    private UUID convertStringToUUID(String s){
        if (s == null){
            return null;
        }
        return UUID.fromString(s);
    }
    private void validateUUIDString(String name,String value) throws InputNotLogicallyValidException {
        if(name != null){
            ValidationUtils validationUtils = new ValidationUtils();
            validationUtils.checkForValidUUIDString(name,value);
        }
    }

    public String getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(String commenterId) {
        this.commenterId = commenterId;
    }
}
