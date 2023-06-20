package com.example.Backend.service;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.schema.BookHeader;
import com.example.Backend.schema.FavouriteOrder;
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
    private FavouriteRepository favouriteRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReadingRepository readingRepository;

    public PaymentInfo buyBook(UUID studentId,UUID bookId){
        Student student = studentRepository.findById(studentId).get();
        Book book = bookRepository.findById(bookId).get();
        Payment payment = new Payment(
                "this book is bought in "+
                        LocalDateTime.now().format(Utils.formatter)
                        + "by student "+ student.getStudentName());
        payment.setRecentOpenedDate(LocalDateTime.now());
        student.addPayment(payment);
        book.addPayment(payment);
        List<Chapter> chapters =  book.getChapters();
        for (Chapter chapter : chapters){
            Reading reading = new Reading(
                    chapter,student,0);
            student.addReading(reading);
            chapter.addReading(reading);
            readingRepository.save(reading);
        }
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
    public Favourite addBookToFavourite(UUID studentId,UUID bookId){
        Student student = studentRepository.findById(studentId).get();
        Book book = bookRepository.findById(bookId).get();
        Favourite favourite = favouriteRepository.findByBookAndStudent(book,student);
        if(favourite != null){
            // the book is already in fav
            return null;
        }else {
            Favourite newFavourite = new Favourite();
            student.addToFavourites(newFavourite);
            book.addToFavourites(newFavourite);
            newFavourite.setOrder(student.getFavouriteList().size()-1);
            return favouriteRepository.save(newFavourite);
        }
    }
    public String removeBookFromFavourite(UUID studentId,UUID bookId){
        Student student = studentRepository.findById(studentId).get();
        Book book = bookRepository.findById(bookId).get();
        Favourite favourite = favouriteRepository.findByBookAndStudent(book,student);
        if(favourite != null){
            for (Favourite fav:
                    favouriteRepository
                            .findAllByStudentAndOrderGreaterThan(
                                    student,favourite.getOrder())){
                fav.setOrder(fav.getOrder() - 1);
            }
            student.removeFromFavourites(favourite);
            book.removeFromFavourites(favourite);
            favouriteRepository.delete(favourite);
            return "DONE";
        }else {
            return "the book is not in fav";
        }
    }
    public void updateFavouritesOrder(UUID studentId, List<FavouriteOrder> favouriteOrders){
        Student student = studentRepository.findById(studentId).get();
        for (FavouriteOrder favouriteOrder : favouriteOrders){
            Book book = bookRepository.findById(favouriteOrder.getBookId()).get();
            Favourite favourite = favouriteRepository.findByBookAndStudent(
                    book,student);
            favourite.setOrder(favouriteOrder.getOrder());
            favouriteRepository.save(favourite);
        }
    }

    private BookHeader createBookHeader(Favourite favourite){
        return new BookHeader(
                favourite.getBook().getBookId(),
                favourite.getBook().getBookCover(),
                favourite.getBook().getTitle());
    }
    public List<BookHeader> getFavourites(UUID studentId){
        Student student = studentRepository.findById(studentId).get();
        List<BookHeader> bookHeaders = new ArrayList<>();
        for (Favourite favourite: favouriteRepository
                .findAllByStudentOrderByOrder(student)){
            bookHeaders.add(
                    createBookHeader(favourite));
        }
        return bookHeaders;
    }
}
