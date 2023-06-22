//package com.example.Backend.controller;
//
//import com.example.Backend.Repository.BookRepository;
//import com.example.Backend.Repository.ChapterRepository;
//import com.example.Backend.Repository.TagRepository;
//import com.example.Backend.model.Book;
//import com.example.Backend.model.Chapter;
//import com.example.Backend.model.Tag;
//import com.example.Backend.service.TagServices;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//public class TagController{
//
//    @Autowired
//    private TagServices tagServices;
//
//    @Autowired
//    private BookRepository bookRepository;
//    @Autowired
//    private ChapterRepository chapterRepository;
//    @Autowired
//    private TagRepository tagRepository;
//    @GetMapping("/newtag")
//    public Tag createNewTag(){
//        Tag tag = new Tag("science");
//        return  tagRepository.save(tag);
//    }
//    @GetMapping("/newchapter")
//    public Chapter creatNewChapter(){
//        Book book = bookRepository.findByTitle("JAVA");
//        Chapter chapter = new Chapter("chapter one");
//        book.addChapter(chapter);
//        return  chapterRepository.save(chapter);
//    }
//    @GetMapping("/newbook")
//    public Book creatNewBook(){
//        //Chapter chapter = new Chapter("chapter one");
//        Book book = new Book("JAVA");
//        return  bookRepository.save(book);
//    }
//
//    @GetMapping("/addtagtochapter/{id}")
//    public Tag assignTagToChapter(@PathVariable UUID id){
//        Tag tag = new Tag("math");
//        chapterRepository.findById(id).get().addTag(tag);
//        return tagRepository.save(tag);
//    }
//    @GetMapping("/addtagtobook/{id}")
//    public Tag assignTagToBook(@PathVariable UUID id){
//        Tag tag = new Tag("fun");
//        bookRepository.findById(id).get().addTag(tag);
//        return tagRepository.save(tag);
//    }
//    @GetMapping("/getallchapters")
//    public List<Chapter> getAllChapters() {return chapterRepository.findAll();}
//
//    @GetMapping("/getallbooks")
//    public List<Book> getAllBooks(){return bookRepository.findAll();}
//
//    @GetMapping("/getbooktags/{id}")
//    public List<Tag> getBookTags(@PathVariable UUID id ){
//        return bookRepository.findById(id).get().getTags();
//    }
//    @GetMapping("/getchaptertags/{id}")
//    public List<Tag> getChapterTags(@PathVariable UUID id ){
//        return chapterRepository.findById(id).get().getTags();
//    }
//    @GetMapping("/gettagbooks/{id}")
//    public List<Book> getTagBooks(@PathVariable UUID id ){
//        return tagRepository.findById(id).get().getBookList();
//    }
//    @GetMapping("/gettagchapters/{id}")
//    public List<Chapter> getTagChapters(@PathVariable UUID id ){
//        return tagRepository.findById(id).get().getChapterList();
//    }
//
//}
