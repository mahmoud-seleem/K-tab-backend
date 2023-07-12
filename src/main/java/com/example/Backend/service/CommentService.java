package com.example.Backend.service;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.CommentInfo;
import com.example.Backend.schema.CommentPage;
import com.example.Backend.security.AppUser;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.example.Backend.validation.helpers.CommentValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@Service
public class CommentService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private S3fileSystem s3fileSystem;
    @Autowired
    private Utils utils;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ValidationUtils validationUtils;
    @Autowired
    private CommentValidation commentValidation;


    public CommentInfo addComment(CommentInfo commentInfo) throws InputNotLogicallyValidException {
        AppUser appUser = commentValidation.validateCommentCreationData(commentInfo);
        Chapter chapter = validationUtils.checkChapterIsExisted(commentInfo.getChapterId());
        Comment comment = createNewComment(commentInfo);
        chapter.addComment(comment);
        if (commentInfo.getCommenterType().equals("STUDENT")) {
            addStudentComment((Student) appUser,commentInfo, comment);
        } else {
            addAuthorComment((Author) appUser,commentInfo, comment);
        }
        setMentionedUsers(commentInfo, comment);
        commentRepository.save(comment);
        return createCommentInfo(comment);
    }

    public CommentInfo getComment(UUID commentId) throws InputNotLogicallyValidException {
        return createCommentInfo(
                validationUtils.checkCommentIsExisted(commentId));
    }

    public void deleteComment(UUID userId, UUID commentId) throws InputNotLogicallyValidException {
        Comment comment = validationUtils.checkCommentIsExisted(commentId);
        if (userIsTheCommenter(userId,comment)){
            comment.removeFromChapter(comment.getChapter());
            if (comment.getCommenterType().equals("STUDENT")) {
                comment.removeFromStudent(comment.getStudent());
            } else {
                comment.removeFromAuthor(comment.getAuthor());
            }
            commentRepository.delete(comment);
        }else throw new InputNotLogicallyValidException(
                "user/comment",
                "user is not the owner of this comment"
        );
    }

    public CommentInfo updateComment(UUID userId,CommentInfo commentInfo) throws InputNotLogicallyValidException {
        Comment comment = validationUtils.checkCommentIsExisted(commentInfo.getCommentId());
        if(userIsTheCommenter(userId,comment)){
            if (commentInfo.getContent() != null){
                comment.setContent(commentInfo.getContent());
            }
            if (commentInfo.getHasMentions() !=null){
                comment.setHasMentions(commentInfo.getHasMentions());
                setMentionedUsers(commentInfo,comment);
            }
            commentRepository.save(comment);
            return createCommentInfo(comment);
        }else throw new InputNotLogicallyValidException(
                "user/comment",
                "user is not the owner of this comment"
        );
    }

    public List<CommentInfo> getAllChapterComments(UUID chapterId) throws InputNotLogicallyValidException {
        Chapter chapter = validationUtils.checkChapterIsExisted(chapterId);
        List<Comment> comments = commentRepository.findAllByChapterOrderByDate(chapter);
        return createListOfCommentInfo(comments);
    }

    public CommentPage getCommentPage(
            String operation,
            String next,
            String prev,
            UUID chapterId,
            int limit ) throws InputNotLogicallyValidException { // Default limit will be 2
        commentValidation.validateCommentPageInputs(
                next,prev,limit,operation,chapterId);
        try{
            if (operation.equals("next")){
                return getNextPage(chapterId,next,prev,limit);
            }else {
                return getPrevPage(chapterId,next,prev,limit);
            }
        }catch (Exception e){
            throw new InputNotLogicallyValidException(
                    "prev/next",
                    "bad pointers please remove them to get the first page !!!"
            );
        }
    }
    public CommentPage getNextPage(UUID chapterId,String next, String prev, int limit) {
        List<Comment> comments = null;
        CommentPage page  = new CommentPage();
        if (next == null && prev == null) {
            comments = commentRepository.getNextPage(chapterId,
                    LocalDateTime.of(2000, Month.APRIL,1,4,0),limit+1);
            page.setPrev(null);
            if (comments.size() == 0 || comments.size() != limit + 1) { // the last page or the database is empty
                page.setNext(null);
            } else {
                page.setNext(comments.get(comments.size() - 2).getDate().format(Utils.formatter));       comments.remove(comments.size() - 1);
            }
            return createCommentPage(page, comments,limit);
        } else if (next == null) {
            page.setNext(null);
            page.setPrev(LocalDateTime.parse(prev).format(Utils.formatter));
            return page;
        } else {
            comments= commentRepository.getNextPage(chapterId,
                    LocalDateTime.parse(next,Utils.formatter),limit+1);
            page.setPrev(
                    comments.get(0).getDate().format(Utils.formatter));
            if (
                    comments.size() != limit + 1) { // the last page
                page.setNext(null);
            } else {
                page.setNext(
                        comments.get(
                                comments.size() - 2).getDate().format(Utils.formatter));
                comments.remove(
                        comments.size() - 1);
            }
            return createCommentPage(page, comments,limit);
        }
    }
    public CommentPage getPrevPage(UUID chapterId,String next, String prev, int limit) {
        List<Comment> comments  = null;
        CommentPage page = new CommentPage();
        if (next == null && prev == null) { // always get the first page
            comments = commentRepository.getPrevPage(chapterId,
                    LocalDateTime.of(2100,Month.APRIL,1,4,0),limit+1);
            page.setPrev(null);
            if (comments.size() == 0 || comments.size() != limit + 1) { // the last page or the database is empty
                page.setNext(null);
            } else {
                page.setNext(comments.get(comments.size() - 2).getDate().format(Utils.formatter));
                comments.remove(comments.size() - 1);
            }
            return createCommentPage(page, comments,limit);
        } else if (prev == null) { // back to the first page while scrolling down to the top
            page.setPrev(null);
            page.setNext(LocalDateTime.parse(next).format(Utils.formatter));
            return page;
        } else {
            comments = commentRepository.getPrevPageDesc(chapterId,
                    LocalDateTime.parse(prev,Utils.formatter),limit+1);
            Collections.reverse(comments);
            page.setNext(comments.get(comments.size() - 1).getDate().format(Utils.formatter));
            if (comments.size() != limit + 1) { // the first page
                page.setPrev(null);
            } else {
                comments.remove(0);
                page.setPrev(comments.get(0).getDate().format(Utils.formatter));
            }
            return createCommentPage(page, comments,limit);
        }
    }


    private CommentPage createCommentPage(CommentPage page,List<Comment> comments,int limit){
        page.setCommentInfoList(
                createListOfCommentInfo(comments));
        if (comments.size() != 0){
            page.setNumOfPages(getNumberOfCommentPages(limit,comments.get(0).getChapter()));
        }else {
            page.setNumOfPages(0);
        }

        return page;
    }
    private boolean userIsTheCommenter(UUID userId,Comment comment) throws InputNotLogicallyValidException {
        validationUtils.checkUserIsExisted("user",userId);
        UUID commenterId = getCommenterId(comment);
        return commenterId.equals(userId);
    }
    private UUID getCommenterId(Comment comment){
        if (comment.getCommenterType().equals("STUDENT")){
            return (comment.getStudent().getStudentId());
        } else {
            return (comment.getAuthor().getAuthorId());
        }
    }
    public void addStudentComment(Student student,CommentInfo commentInfo, Comment comment) {
        comment.setAuthor(null);
        student.addStudentComment(comment);
    }

    public void addAuthorComment(Author author ,CommentInfo commentInfo, Comment comment) {
        comment.setStudent(null);
        author.addAuthorComment(comment);
    }

    private Comment createNewComment(CommentInfo commentInfo) {
        return new Comment(
                commentInfo.getCommenterType(),
                commentInfo.getContent(),
                LocalDateTime.now(),
                commentInfo.getHasMentions()
        );
    }

    private void setMentionedUsers(CommentInfo commentInfo, Comment comment) throws InputNotLogicallyValidException {
        if (commentInfo.getHasMentions() != null){
            if (commentInfo.getHasMentions()) {
                validationUtils.checkForNull("mentionedUsers",commentInfo.getMentionedUsers());
                validationUtils.checkForEmptyList("mentionedUsers",commentInfo.getMentionedUsers());
                for(int i = 0 ; i<commentInfo.getMentionedUsers().size();i++){
                    validationUtils.checkUserIsExisted("mentionedUsers["+i+"]",
                            UUID.fromString(commentInfo.getMentionedUsers().get(i)));
                }
                comment.setMentionedUsers(getMentionedUsersAsUuids(commentInfo.getMentionedUsers()));
            }else {
                comment.setMentionedUsers(null);
            }
        }
    }

    private List<UUID> getMentionedUsersAsUuids(List<String> mentionedUsers){
        List<UUID> res = new ArrayList<>();
        if(mentionedUsers != null){
            for(String s : mentionedUsers){
                res.add(UUID.fromString(s));
            }
        }
        return res;
    }
    private List<CommentInfo> createListOfCommentInfo(List<Comment> comments){
        List<CommentInfo> commentInfoList = new ArrayList<>();
        for (Comment comment : comments){
            commentInfoList.add(createCommentInfo(comment));
        }
        return commentInfoList;
    }
    private CommentInfo createCommentInfo1(Comment comment) {
        return new CommentInfo(
                comment.getCommentId(),
                comment.getCommenterType(),
                ((comment.getAuthor() != null) ?
                        comment.getAuthor().getAuthorId() : null),
                ((comment.getStudent() != null) ?
                        comment.getStudent().getStudentId() : null),
                comment.getChapter().getChapterId(),
                comment.getContent(),
                comment.getDate().format(Utils.formatter),
                comment.getHasMentions(),
                comment.getMentionedUsersAsStrings());
    }
    private CommentInfo createCommentInfo(Comment comment) {
        return new CommentInfo(
                comment.getCommentId(),
                ((comment.getStudent() != null) ?
                        comment.getStudent().getStudentId() : comment.getAuthor().getAuthorId()),
                comment.getChapter().getChapterId(),
                comment.getContent(),
                comment.getHasMentions(),
                comment.getMentionedUsersAsStrings(),
                comment.getDate().format(Utils.formatter),
                comment.getCommenterType());
    }
    private int getNumberOfCommentPages(int limit,Chapter chapter){
        long comments = chapter.getCommentList().size();
        if (comments == 0){
            return 0;
        }else {
            return ((int)Math.ceil(
                    ((double) comments) / limit));
        }
    }
}
