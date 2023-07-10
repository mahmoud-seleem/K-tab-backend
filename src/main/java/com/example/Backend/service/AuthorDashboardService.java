package com.example.Backend.service;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.BookRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.Book;
import com.example.Backend.schema.PaymentCount;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorDashboardService {

    @Autowired
    private ValidationUtils validationUtils;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private Utils utils;
    @Autowired
    private BookRepository bookRepository;

//    public List<PaymentCount> getTop3BookWithMostPayments(UUID authorId) throws InputNotLogicallyValidException {
//        Author author = validationUtils.checkAuthorIsExisted(authorId);
//        List<Book> books = author.getAuthorBooksList();
//        List<PaymentCount> result = new ArrayList<>();
//            for (Book book: books){
//                result.add( new PaymentCount(
//                        book.getBookId().toString(),
//                        book.getPaymentList().size()));
//            }
//        Collections.sort(result);
//        }
//    }
}
