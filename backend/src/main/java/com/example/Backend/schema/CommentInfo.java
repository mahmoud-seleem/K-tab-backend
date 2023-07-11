package com.example.Backend.schema;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Data
@Component
public class CommentInfo {

    private UUID CommentId;

    private String commenterType;

    private UUID authorId;

    private UUID studentId;

    private UUID chapterId;

    private String content;

    private String date;

    private Boolean hasMentions;

    private List<UUID> mentionedUsers;

    public CommentInfo(UUID commentId, String commenterType, UUID authorId, UUID studentId, UUID chapterId, String content, String date, Boolean hasMentions, List<UUID> mentionedUsers) {
        CommentId = commentId;
        this.commenterType = commenterType;
        this.authorId = authorId;
        this.studentId = studentId;
        this.chapterId = chapterId;
        this.content = content;
        this.date = date;
        this.hasMentions = hasMentions;
        this.mentionedUsers = mentionedUsers;
    }

    public CommentInfo() {
    }

}
