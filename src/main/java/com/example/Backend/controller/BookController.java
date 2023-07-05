package com.example.Backend.controller;


import com.example.Backend.Repository.BookRepository;
import com.example.Backend.schema.BookInfo;
import com.example.Backend.schema.BookPage;
import com.example.Backend.security.JwtService;
import com.example.Backend.service.BookService;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.json.ValidJson;
import com.example.Backend.validation.json.ValidParam;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin()
@RequestMapping("/book/")
public class BookController {

    @Autowired
    private BookService bookService;


    @Autowired
    private JwtService jwtService;
    @Autowired
    private BookRepository bookdRepository;
//    @GetMapping("/{id}")
//    public Book findBookById(@PathVariable UUID id){
//        return bookService.findBookById(id);
//    }

    @PostMapping()
    public BookInfo saveNewBook(HttpServletRequest request,@ValidJson("BookInfo") BookInfo bookInfo) throws Exception {
        bookInfo.setAuthorId(jwtService.getUserId(request));
        return bookService.saveNewBook(bookInfo);
    }

    @PutMapping()
    public BookInfo updateBookInfo(HttpServletRequest request, @ValidJson("BookInfo") BookInfo bookInfo) throws Exception {
        bookInfo.setAuthorId(jwtService.getUserId(request));
            return bookService.updateBookInfo(bookInfo);
    }

    @GetMapping()
    public BookInfo getBookInfo(HttpServletRequest request,@ValidParam UUID bookId) throws InputNotLogicallyValidException {
        if (jwtService.getUserType(request).equals("ADMIN")){
            return bookService.getBookInfo(bookId);
        }else {
            return bookService.getStudentBookInfo(jwtService.getUserId(request),bookId);
        }

    }

    @GetMapping("/page/")
    public BookPage getPage(@RequestBody Map<String, Object> body) {
        if (((String) body.get("op")).equals("next")) {
            return bookService.getNextPage(
                    (String) body.get("next"),
                    (String) body.get("prev"),
                    (int) body.get("limit"));
        } else {
            return bookService.getPrevPage(
                    (String) body.get("next"),
                    (String) body.get("prev"),
                    (int) body.get("limit"));
        }
    }
    @PostMapping("/search/")
    public BookPage search(@RequestBody Map<String, Object> body) {
        if (((String) body.get("op")).equals("next")) {
            return bookService.getNextPageWithSearch(
                    (String) body.get("next"),
                    (String) body.get("prev"),
                    (int) body.get("limit"),
                    (String) body.get("title"),
                    (String) body.get("tagName"),
                    (String) body.get("operation"));
        } else {
            return bookService.getPrevPageWithSearch(
                    (String) body.get("next"),
                    (String) body.get("prev"),
                    (int) body.get("limit"),
                    (String) body.get("title"),
                    (String) body.get("tag"),
                    (String) body.get("operation"));
        }
    }
    @GetMapping("/all/")
    public List<UUID> getAllBookIds() {
        return bookService.getAllBookIds();
    }

    @GetMapping("/all-with-tagName/")
    public List<Map<String, Object>> getAllBooksWithTagName(@RequestParam String tagName) {
        return bookService.getAllBookWithTagName(tagName);
    }

    @GetMapping("/all-with-title/")
    public List<Map<String, Object>> getAllBooksWithTitle(@RequestParam String title) {
        return bookService.getAllBookWithTitle(title);
    }

    @GetMapping("/all-with-tagAndTitle/")
    public List<Map<String, Object>> getAllBooksWithTagNameAndTitle(@RequestParam String tagName,
                                                                    @RequestParam String title) {
        return bookService.getAllBookWithTitleAndTag(title, tagName);
    }

    @GetMapping("/all-with-tagOrTitle/")
    public List<Map<String, Object>> getAllBooksWithTagNameOrTitle(@RequestParam String tagName,
                                                                   @RequestParam String title) {
        return bookService.getAllBookWithTitleOrTag(title, tagName);
    }
}