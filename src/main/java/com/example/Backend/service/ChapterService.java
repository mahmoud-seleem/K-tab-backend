package com.example.Backend.service;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.s3Connection.S3PreSignedURL;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.schema.ChapterInfo;
import com.example.Backend.schema.InteractionInfo;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
import org.aspectj.weaver.ast.Literal;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ChapterService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ImageConverter imageConverter;

    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private S3fileSystem s3fileSystem;
    @Autowired
    private Utils utils;

    @Autowired
    private BookService bookService;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private InteractionRepository interactionRepository;
    @Autowired
    private S3PreSignedURL s3PreSignedURL;

    @Autowired
    private ReadingRepository readingRepository;
    public ChapterInfo saveNewChapter(ChapterInfo chapterInfo) throws Exception {
        Chapter chapter = createNewChapter(chapterInfo);
        return createChapterInfoResponse(
                chapterRepository.save(
                        updateLaseModifiedDate(chapter)));
    }

    public ChapterInfo updateChapterInfo(ChapterInfo chapterInfo) throws Exception {
        Chapter chapter = chapterRepository.findById(chapterInfo.getChapterId()).get();
        updateChapterData(chapter, chapterInfo);
        return createChapterInfoResponse(
                chapterRepository.save(updateLaseModifiedDate(chapter)));
    }

    public ChapterInfo getChapterInfo(UUID chapterId) {
        return createChapterInfoResponse(
                chapterRepository.findById(chapterId).get()
        );
    }

    private ChapterInfo createChapterInfoResponse(Chapter chapter) {
        return new ChapterInfo(
                chapter.getBook().getAuthor().getAuthorId(),
                chapter.getChapterId(),
                chapter.getBook().getBookId(),
                chapter.getTitle(),
                chapter.getContent(),
                chapter.getAudio(),
                chapter.getReadingDuration(),
                chapter.getChapterOrder(),
                chapter.getCreationDateAsString(),
                chapter.getLastModifiedDateAsString(),
                chapter.getTagsNames()
        );
    }

    private Chapter createNewChapter(ChapterInfo chapterInfo) throws Exception {
        Chapter chapter = new Chapter(
                chapterInfo.getTitle()
        );
        Book book = bookRepository.
                findById(chapterInfo.getBookId()).get();
        book.addChapter(chapter);
        bookRepository.save(book);
        updateChapterData(
                chapterRepository.
                        save(chapter), chapterInfo);
        updateCreationDate(chapter);
        return chapter;
    }

    private Chapter updateCreationDate(Chapter chapter) {
        chapter.setCreationDate(LocalDateTime.parse(LocalDateTime.now().
                format(Utils.formatter), Utils.formatter));
        return chapter;
    }

    private Chapter updateLaseModifiedDate(Chapter chapter) {
        chapter.setLastModified(LocalDateTime.parse(LocalDateTime.now().
                format(Utils.formatter), Utils.formatter));
        bookService.updateLastEditDate(chapter.getBook());
        return chapter;
    }

    private void updateChapterData(
            Chapter chapter, ChapterInfo chapterInfo) throws Exception {
        for (Field field : chapterInfo.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.get(chapterInfo) != null) {
                System.out.println(field.getName());
                switch (field.getName()) {
                    case "tags": {
                        setupChapterTags(chapter, chapterInfo.getTags());
                        break;
                    }
                    case "ownerId":
                    case "chapterId":
                    case "bookId": {
                        break;
                    }
                    default: {
                        utils.getMethodBySignature("set",
                                        field, chapter, field.getType())
                                .invoke(chapter,
                                        field.get(chapterInfo));
                    }
                }
            }
        }
        setupChapterContent(chapter, chapterInfo);
        setupChapterAudio(chapter, chapterInfo);
        setupChapterOrder(chapter, chapterInfo);
    }

    private void setupChapterOrder(Chapter chapter, ChapterInfo chapterInfo) {
        if (chapterInfo.getChapterOrder() == null) {
            if (chapter.getChapterOrder() == null) {
                chapter.setChapterOrder(
                        chapter.getBook().getChapters().size() - 1
                );
            }
        } else {
            chapter.setChapterOrder(
                    chapterInfo.getChapterOrder());
        }
    }

    private String setupChapterContent(Chapter chapter, ChapterInfo chapterInfo) {
        String contentPath = ("Books/"
                + chapter.getBook().getBookId().toString()
                + "/"
                + "Chapters/"
                + chapter.getChapterId().toString()
                + "/content.json");
        s3fileSystem.reserveEmptyPlace(contentPath);
        chapter.setContent(contentPath);
        return contentPath;
    }

    private String setupChapterAudio(Chapter chapter, ChapterInfo chapterInfo) {
        String audioPath = ("Books/"
                + chapter.getBook().getBookId().toString()
                + "/"
                + "Chapters/"
                + chapter.getChapterId().toString()
                + "/audio.mp3");
        s3fileSystem.reserveEmptyPlace(audioPath);
        chapter.setAudio(audioPath);
        return audioPath;
    }

    private Chapter setupChapterTags(Chapter chapter, List<String> tags) {
        chapter.clearTags();
        Tag tag = null;
        for (String tagName : tags) {
            if (tagRepository.findByName(tagName).isEmpty()) {
                tag = tagRepository.save(new Tag(tagName));
            } else {
                tag = tagRepository.findByName(tagName).get();
                tag.removeChapter(chapter);
            }
            tag.addChapter(chapter);
        }
        tagRepository.save(tag);
        return chapterRepository.save(chapter);
    }

    public InteractionInfo addInteraction(InteractionInfo interactionInfo) {

        Student student = studentRepository
                .findById(interactionInfo.getStudentId()).get();
        Chapter chapter = chapterRepository
                .findById(interactionInfo.getChapterId()).get();
        JSONObject data = new JSONObject(
                interactionInfo.getInteractionData()
        );
        Interaction interaction = new Interaction(data);
        Reading reading = readingRepository.findByStudentAndChapter(student,chapter);
        reading.addInteraction(interaction);
        return createInteractionInfo(
                interactionRepository.save(interaction));
    }

    public InteractionInfo updateInteraction(InteractionInfo interactionInfo) {
        JSONObject data ;
        Interaction interaction = interactionRepository
                .findById(interactionInfo.getInteractionId()).get();
        if(interactionInfo.getInteractionData() != null ){
            data = new JSONObject(interactionInfo.getInteractionData());
            interaction.setData(data);
        }
        return createInteractionInfo(
                interactionRepository.save(interaction));
    }
    public void deleteInteraction(UUID interactionId) {
        Interaction interaction = interactionRepository
                .findById(interactionId).get();
        Reading reading = interaction.getReading();
        reading.removeInteraction(interaction);
        interactionRepository.delete(interaction);
    }

    public InteractionInfo getInteraction(UUID interactionId){
        Interaction interaction = interactionRepository
                .findById(interactionId).get();
        return createInteractionInfo(interaction);
    }
    public List<InteractionInfo> getInteractions(UUID studentId,UUID chapterId){
        Student student = studentRepository
                .findById(studentId).get();
        Chapter chapter = chapterRepository
                .findById(chapterId).get();
        Reading reading = readingRepository.findByStudentAndChapter(student,chapter);
        return createListOfInteractionInfo(reading.getInteractions());
    }


    private InteractionInfo createInteractionInfo(Interaction interaction){
        return new InteractionInfo(
                interaction.getReading().getStudent().getStudentId(),
                interaction.getReading().getChapter().getChapterId(),
                interaction.getReading().getReadingId(),
                interaction.getInteractionId(),
                interaction.getData().toMap());
    }

    private List<InteractionInfo> createListOfInteractionInfo(List<Interaction> interactions){
        List<InteractionInfo> interactionInfoList = new ArrayList<>();
        for (Interaction interaction: interactions){
            interactionInfoList.add(
                    createInteractionInfo(interaction));
        }
        return interactionInfoList;
    }
}
