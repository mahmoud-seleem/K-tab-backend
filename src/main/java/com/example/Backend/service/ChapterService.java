package com.example.Backend.service;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.s3Connection.S3PreSignedURL;
import com.example.Backend.s3Connection.S3fileSystem;
import com.example.Backend.schema.*;
import com.example.Backend.utils.ImageConverter;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import com.example.Backend.validation.helpers.ChapterValidation;
import org.aspectj.weaver.ast.Literal;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;

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
    private ValidationUtils validationUtils;
    @Autowired
    private ChapterValidation chapterValidation;
    @Autowired
    private ReadingRepository readingRepository;

    public ChapterInfo saveNewChapter(ChapterInfo chapterInfo) throws Exception {
        Chapter chapter = createNewChapter(chapterInfo);
        return createChapterInfoResponse(
                chapterRepository.save(
                        updateLaseModifiedDate(chapter)));
    }

    public ChapterInfo updateChapterInfo(ChapterInfo chapterInfo) throws Exception {
        Chapter chapter = chapterValidation.validateChapterUpdate(chapterInfo);
        updateChapterData(chapter, chapterInfo);
        return createChapterInfoResponse(
                chapterRepository.save(updateLaseModifiedDate(chapter)));
    }

    public ChapterInfo getChapterInfo(UUID chapterId) throws InputNotLogicallyValidException {
        return createChapterInfoResponse(
                validationUtils.checkChapterIsExisted(chapterId)
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
        Book book = chapterValidation.validateChapterCreation(chapterInfo);
        Chapter chapter = new Chapter(
                chapterInfo.getTitle()
        );
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

    public InteractionInfo addInteraction(InteractionInfo interactionInfo) throws InputNotLogicallyValidException {
        Student student = validationUtils.checkStudentIsExisted(interactionInfo.getStudentId());
        Chapter chapter = validationUtils.checkChapterIsExisted(interactionInfo.getChapterId());
        validationUtils.checkPaymentIsExisted(student, chapter.getBook());
        validationUtils.checkForNull("interactionData", interactionInfo.getInteractionData());
        Reading reading = readingRepository.findByStudentAndChapter(student, chapter);
        JSONObject data = new JSONObject(
                interactionInfo.getInteractionData()
        );
        Interaction interaction = new Interaction(data);
        reading.addInteraction(interaction);
        return createInteractionInfo(
                interactionRepository.save(interaction));
    }

    public InteractionInfo updateInteraction(InteractionInfo interactionInfo) throws InputNotLogicallyValidException {
        JSONObject data;
        Interaction interaction = validationUtils.checkInteractionIsExisted(interactionInfo.getInteractionId());
        validationUtils.checkForNull("interactionData", interactionInfo.getInteractionData());
        data = new JSONObject(interactionInfo.getInteractionData());
        interaction.setData(data);
        return createInteractionInfo(
                interactionRepository.save(interaction));
    }

    public List<InteractionInfo> deleteInteraction(UUID studentId,UUID interactionId) throws InputNotLogicallyValidException {
        Student student = validationUtils.checkStudentIsExisted(studentId);
        Interaction interaction = validationUtils.checkInteractionIsExisted(interactionId);
        Reading reading = interaction.getReading();
        validationUtils.checkPaymentIsExisted(student,reading.getChapter().getBook());
        reading.removeInteraction(interaction);
        interactionRepository.delete(interaction);
        return createListOfInteractionInfo(reading.getInteractions());
    }

    public InteractionInfo getInteraction(UUID studentId,UUID interactionId) throws InputNotLogicallyValidException {
        Student student = validationUtils.checkStudentIsExisted(studentId);
        Interaction interaction = validationUtils.checkInteractionIsExisted(interactionId);
        Reading reading = interaction.getReading();
        validationUtils.checkPaymentIsExisted(student,reading.getChapter().getBook());
        return createInteractionInfo(interaction);
    }

    public List<InteractionInfo> getInteractions(UUID studentId, UUID chapterId) throws InputNotLogicallyValidException {
        Student student = validationUtils.checkStudentIsExisted(studentId);
        Chapter chapter = validationUtils.checkChapterIsExisted(chapterId);
        validationUtils.checkPaymentIsExisted(student, chapter.getBook());
        Reading reading = readingRepository.findByStudentAndChapter(student, chapter);
        return createListOfInteractionInfo(reading.getInteractions());
    }


    private InteractionInfo createInteractionInfo(Interaction interaction) {
        return new InteractionInfo(
                interaction.getReading().getStudent().getStudentId(),
                interaction.getReading().getChapter().getChapterId(),
                interaction.getReading().getReadingId(),
                interaction.getInteractionId(),
                interaction.getData().toMap());
    }

    private List<InteractionInfo> createListOfInteractionInfo(List<Interaction> interactions) {
        List<InteractionInfo> interactionInfoList = new ArrayList<>();
        for (Interaction interaction : interactions) {
            interactionInfoList.add(
                    createInteractionInfo(interaction));
        }
        return interactionInfoList;
    }
    public BookInfo updateChaptersOrder(UUID authorId,UUID bookId, List<ChapterOrder> chapterOrders) throws InputNotLogicallyValidException {
        Author author = checkChapterUpdateData(authorId,bookId,chapterOrders);
        Book book = validationUtils.checkBookIsExisted(bookId);
        List<Chapter> chapters = new ArrayList<>();
        for (int i = 0 ; i < chapterOrders.size();i++) {
            Chapter chapter = validationUtils.checkChapterIsExisted(chapterOrders.get(i).getChapterId());
            if (!(chapter.getBook().getBookId().equals(bookId))){
                throw new InputNotLogicallyValidException(
                        "chapterOrders["+i+"]",
                        "chapterOrders["+i+"]"+" is not in this book !"
                );
            }
            chapters.add(chapter);
        }
        for (int i = 0 ; i < chapters.size();i++) {
            chapters.get(i).setChapterOrder(chapterOrders.get(i).getOrder());
            chapterRepository.save(chapters.get(i));
        }
        return bookService.createBookInfoResponse(book);
    }
    private Author checkChapterUpdateData(UUID authorId,UUID bookId,List<ChapterOrder> chapterOrders) throws InputNotLogicallyValidException {
        Author author = validationUtils.checkAuthorIsExisted(authorId);
        Book book = validationUtils.checkBookIsExisted(bookId);
        validationUtils.checkForNull("chapterOrders",chapterOrders);
        validationUtils.checkForEmptyList("chapterOrders",chapterOrders);
        validationUtils.checkForDuplicationInList("chapterOrders",
                createSortedOrders(chapterOrders));
        validationUtils.checkForListSize("chapterOrders",chapterOrders,book.getChapters().size());
        validationUtils.checkForBookOwner(author,book);
        return author;
    }
    private List<Integer> createSortedOrders(List<ChapterOrder> chapterOrders) throws InputNotLogicallyValidException {
        List<Integer> orders = new ArrayList<>();
        for (ChapterOrder chapterOrder : chapterOrders){
            orders.add(chapterOrder.getOrder());
        }
        Collections.sort(orders);
        if (orders.get(0) != 0 || orders.get(orders.size()-1) !=  (orders.size() - 1)){
            throw new InputNotLogicallyValidException(
                    "order",
                    "orders is invalid !! "
            );
        }
        return orders;
    }
}
