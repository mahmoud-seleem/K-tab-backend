package com.example.Backend.service;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.BookPage;
import com.example.Backend.schema.CommentInfo;
import com.example.Backend.schema.CommentPage;
import com.example.Backend.schema.enums.UserType;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
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


    public CommentInfo addComment(CommentInfo commentInfo) {
        Comment comment = createNewComment(commentInfo);
        Chapter chapter = chapterRepository.findById(
                commentInfo.getChapterId()).get();
        chapter.addComment(comment);
        if (commentInfo.getCommenterType().equals("STUDENT")) {
            addStudentComment(commentInfo, comment);
        } else {
            addAuthorComment(commentInfo, comment);
        }
        setMentionedUsers(commentInfo, comment);
        commentRepository.save(comment);
        return createCommentInfo(comment);
    }

    public CommentInfo getComment(UUID commentId) {
        return createCommentInfo(
                commentRepository.findById(commentId).get());
    }

    public void deleteComment(UUID userId, UUID commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        if (userIsTheCommenter(userId,comment)){
            comment.removeFromChapter(comment.getChapter());
            if (comment.getCommenterType().equals("STUDENT")) {
                comment.removeFromStudent(comment.getStudent());
            } else {
                comment.removeFromAuthor(comment.getAuthor());
            }
            commentRepository.delete(comment);
        }
    }

    public CommentInfo updateComment(UUID userId,CommentInfo commentInfo) {
        Comment comment = commentRepository.findById(
                commentInfo.getCommentId()).get();
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
        }else return null;
    }

    public List<CommentInfo> getAllChapterComments(UUID chapterId) {
        Chapter chapter = chapterRepository.findById(chapterId).get();
        List<Comment> comments = commentRepository.findAllByChapterOrderByDate(chapter);
        return createListOfCommentInfo(comments);
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
                page.setNext(comments.get(comments.size() - 2).getDate());
                comments.remove(comments.size() - 1);
            }
            return createCommentPage(page, comments);
        } else if (next == null) {
            page.setNext(null);
            page.setPrev(LocalDateTime.parse(prev));
            return page;
        } else {
            comments= commentRepository.getNextPage(chapterId,
                    LocalDateTime.parse(next),limit+1);
            page.setPrev(
                    comments.get(0).getDate());
            if (
                    comments.size() != limit + 1) { // the last page
                page.setNext(null);
            } else {
                page.setNext(
                        comments.get(
                                comments.size() - 2).getDate());

                comments.remove(
                        comments.size() - 1);
            }
            return createCommentPage(page, comments);
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
                page.setNext(comments.get(comments.size() - 2).getDate());
                comments.remove(comments.size() - 1);
            }
            return createCommentPage(page, comments);
        } else if (prev == null) { // back to the first page while scrolling down to the top
            page.setPrev(null);
            page.setNext(LocalDateTime.parse(next));
            return page;
        } else {
            comments = commentRepository.getPrevPageDesc(chapterId,
                    LocalDateTime.parse(prev),limit+1);
            Collections.reverse(comments);
            page.setNext(comments.get(comments.size() - 1).getDate());
            if (comments.size() != limit + 1) { // the first page
                page.setPrev(null);
            } else {
                comments.remove(0);
                page.setPrev(comments.get(0).getDate());
            }
            return createCommentPage(page, comments);
        }
    }


    private CommentPage createCommentPage(CommentPage page,List<Comment> comments){
        page.setCommentInfoList(
                createListOfCommentInfo(comments));
        return page;
    }
    private boolean userIsTheCommenter(UUID userId,Comment comment){
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
    public void addStudentComment(CommentInfo commentInfo, Comment comment) {
        comment.setAuthor(null);
        Student student = studentRepository.findById(
                commentInfo.getStudentId()).get();
        student.addStudentComment(comment);
    }

    public void addAuthorComment(CommentInfo commentInfo, Comment comment) {
        comment.setStudent(null);
        Author author = authorRepository.findById(
                commentInfo.getAuthorId()).get();
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

    private void setMentionedUsers(CommentInfo commentInfo, Comment comment) {
        if (commentInfo.getHasMentions()) {
            comment.setMentionedUsers(commentInfo.getMentionedUsers());
        }
    }

    private List<CommentInfo> createListOfCommentInfo(List<Comment> comments){
        List<CommentInfo> commentInfoList = new ArrayList<>();
        for (Comment comment : comments){
            commentInfoList.add(createCommentInfo(comment));
        }
        return commentInfoList;
    }
    private CommentInfo createCommentInfo(Comment comment) {
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
                comment.getMentionedUsers());
    }
}
