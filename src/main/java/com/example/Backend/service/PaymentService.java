package com.example.Backend.service;

import com.example.Backend.Repository.BookRepository;
import com.example.Backend.Repository.ChapterRepository;
import com.example.Backend.Repository.PaymentRepository;
import com.example.Backend.Repository.StudentRepository;
import com.example.Backend.model.Book;
import com.example.Backend.model.Payment;
import com.example.Backend.model.Student;
import com.example.Backend.schema.BookHeader;
import com.example.Backend.schema.PaymentInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private BookRepository bookRepository;

    public PaymentInfo buyBook(UUID studentId,UUID bookId){
        Student student = studentRepository.findById(studentId).get();
        Book book = bookRepository.findById(bookId).get();
        Payment payment = new Payment("19/6");
        payment.setRecentOpenedDate(LocalDateTime.now());
        student.addPayment(payment);
        book.addPayment(payment);
        return createPaymentInfo(
                paymentRepository.save(payment));
    }

    public List<PaymentInfo> getAllStudentPayments(UUID studentId){
        Student student = studentRepository.findById(studentId).get();
        return createPaymentInfoList(
                student.getPaymentList());
    }
    public List<PaymentInfo> getAllStudentPayments1(UUID studentId){
        Student student = studentRepository.findById(studentId).get();
        return createPaymentInfoList(
                paymentRepository.
                        findAllByStudentOrderByRecentOpenedDate(student));
    }
    public List<BookHeader> getStudentLibrary(UUID studentId){
        Student student = studentRepository.findById(studentId).get();
        return createBookHeaderList(
                paymentRepository.
                        findAllByStudentOrderByRecentOpenedDateDesc(
                                student));
    }
    public List<PaymentInfo> getAllBookPayments(UUID bookId){
        Book book = bookRepository.findById(bookId).get();
        return createPaymentInfoList(book.getPaymentList());
    }
    private PaymentInfo createPaymentInfo(Payment payment){
        return new PaymentInfo(
                payment.getPaymentId(),
                payment.getBook().getTitle(),
                payment.getBook().getBookId(),
                payment.getStudent().getStudentEmail(),
                payment.getStudent().getStudentId(),
                payment.getPaymentInfo(),
                payment.getRecentOpenedDate().format(Utils.formatter),
                payment.getRatingValue()
        );
    }
    private List<PaymentInfo> createPaymentInfoList(List<Payment> payments){
        List<PaymentInfo> paymentInfoList = new ArrayList<>();
        for (Payment payment:payments){
            paymentInfoList.add(
                    createPaymentInfo(payment));
        }
        return paymentInfoList;
    }
    private BookHeader createBookHeader(Payment payment){
        return new BookHeader(
                payment.getBook().getBookId(),
                payment.getBook().getBookCover(),
                payment.getBook().getTitle());
    }
    private List<BookHeader> createBookHeaderList(List<Payment> payments){
        List<BookHeader> bookHeaders = new ArrayList<>();
        for (Payment payment:payments){
            bookHeaders.add(
                    createBookHeader(payment));
        }
        return bookHeaders;
    }

}
