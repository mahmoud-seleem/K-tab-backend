package com.example.Backend.service;

import com.example.Backend.Repository.*;
import com.example.Backend.model.*;
import com.example.Backend.schema.BookHeader;
import com.example.Backend.schema.FavouriteOrder;
import com.example.Backend.schema.PaymentInfo;
import com.example.Backend.security.JwtService;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private ValidationUtils validationUtils;

    public PaymentInfo buyBook(UUID studentId, UUID bookId) throws InputNotLogicallyValidException {
        Student student = validationUtils.checkStudentIsExisted(studentId);
        Book book = validationUtils.checkBookIsExisted(bookId);
        validationUtils.checkPaymentIsNotExisted(student, book);
        Payment payment = new Payment(
                "this book is bought in " +
                        LocalDateTime.now().format(Utils.formatter)
                        + "by student " + student.getStudentName());
        payment.setRecentOpenedDate(LocalDateTime.now());
        student.addPayment(payment);
        book.addPayment(payment);
        List<Chapter> chapters = book.getChapters();
        for (Chapter chapter : chapters) {
            Reading reading = new Reading(
                    chapter, student, 0);
            student.addReading(reading);
            chapter.addReading(reading);
            readingRepository.save(reading);
        }
        return createPaymentInfo(
                paymentRepository.save(payment));
    }

    public List<PaymentInfo> getAllStudentPayments(UUID studentId) {
        Student student = studentRepository.findById(studentId).get();
        return createPaymentInfoList(
                student.getPaymentList());
    }

    public List<PaymentInfo> getAllStudentPayments1(UUID studentId) {
        Student student = studentRepository.findById(studentId).get();
        return createPaymentInfoList(
                paymentRepository.
                        findAllByStudentOrderByRecentOpenedDate(student));
    }

    public List<BookHeader> getStudentLibrary(UUID studentId) {
        Student student = studentRepository.findById(studentId).get();
        return createBookHeaderList(
                paymentRepository.
                        findAllByStudentOrderByRecentOpenedDateDesc(
                                student));
    }

    public List<PaymentInfo> getAllBookPayments(UUID bookId) {
        Book book = bookRepository.findById(bookId).get();
        return createPaymentInfoList(book.getPaymentList());
    }

    private PaymentInfo createPaymentInfo(Payment payment) {
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

    private List<PaymentInfo> createPaymentInfoList(List<Payment> payments) {
        List<PaymentInfo> paymentInfoList = new ArrayList<>();
        for (Payment payment : payments) {
            paymentInfoList.add(
                    createPaymentInfo(payment));
        }
        return paymentInfoList;
    }

    private BookHeader createBookHeader(Book book) {
        return new BookHeader(
                book.getBookId(),
                book.getAuthor().getAuthorId(),
                book.getAuthor().getAuthorName(),
                book.getBookCover(),
                book.getTitle(),
                book.getTagsNames(),
                book.getBookAbstract()
        );
    }

    private List<BookHeader> createBookHeaderList(List<Payment> payments) {
        List<BookHeader> bookHeaders = new ArrayList<>();
        for (Payment payment : payments) {
            bookHeaders.add(
                    createBookHeader(payment.getBook()));
        }
        return bookHeaders;
    }

    public List<BookHeader> addBookToFavourite(UUID studentId, UUID bookId) throws InputNotLogicallyValidException {
        Student student = validationUtils.checkStudentIsExisted(studentId);
        Book book = validationUtils.checkBookIsExisted(bookId);
        validationUtils.checkPaymentIsExisted(student, book);
        validationUtils.checkFavIsNotExisted(student, book);
        Favourite favourite = new Favourite();
        student.addToFavourites(favourite);
        book.addToFavourites(favourite);
        favourite.setOrder(student.getFavouriteList().size() - 1);
        favouriteRepository.save(favourite);
        return getFavourites(studentId);
    }


    public List<BookHeader> removeBookFromFavourite(UUID studentId, UUID bookId) throws InputNotLogicallyValidException {
        Student student = validationUtils.checkStudentIsExisted(studentId);
        Book book = validationUtils.checkBookIsExisted(bookId);
        validationUtils.checkPaymentIsExisted(student, book);
        Favourite favourite = validationUtils.checkFavIsExisted(student,book);
            for (Favourite fav :
                    favouriteRepository
                            .findAllByStudentAndOrderGreaterThan(
                                    student, favourite.getOrder())) {
                fav.setOrder(fav.getOrder() - 1);
            }
            student.removeFromFavourites(favourite);
            book.removeFromFavourites(favourite);
            favouriteRepository.delete(favourite);
            return getFavourites(studentId);
    }

    public List<BookHeader> updateFavouritesOrder(UUID studentId, List<FavouriteOrder> favouriteOrders) throws InputNotLogicallyValidException {
        Student student = checkFavouriteUpdateData(studentId,favouriteOrders);
        List<Favourite> favouriteList = new ArrayList<>();
        for (FavouriteOrder favouriteOrder : favouriteOrders) {
            Book book = validationUtils.checkBookIsExisted(favouriteOrder.getBookId());
            validationUtils.checkPaymentIsExisted(student,book);
            Favourite favourite = validationUtils.checkFavIsExisted(student,book);
            favouriteList.add(favourite);
        }
        for(int i = 0; i< favouriteList.size();i++){
            favouriteList.get(i).setOrder(favouriteOrders.get(i).getOrder());
            favouriteRepository.save(favouriteList.get(i));
        }

        return getFavourites(studentId);
    }

    private BookHeader createBookHeader(Favourite favourite) {
        return new BookHeader(
                favourite.getBook().getBookId(),
                favourite.getBook().getBookCover(),
                favourite.getBook().getTitle());
    }

    public List<BookHeader> getFavourites(UUID studentId) {
        Student student = studentRepository.findById(studentId).get();
        List<BookHeader> bookHeaders = new ArrayList<>();
        for (Favourite favourite : favouriteRepository
                .findAllByStudentOrderByOrder(student)) {
            bookHeaders.add(
                    createBookHeader(favourite.getBook()));
        }
        return bookHeaders;
    }
    private Student checkFavouriteUpdateData(UUID studentId,List<FavouriteOrder> favouriteOrders) throws InputNotLogicallyValidException {
        Student student = validationUtils.checkStudentIsExisted(studentId);
        validationUtils.checkForNull("favouriteOrders",favouriteOrders);
        validationUtils.checkForEmptyList("favouriteOrders",favouriteOrders);
        validationUtils.checkForDuplicationInList("favouriteOrders",
                createSortedOrders(favouriteOrders));
        validationUtils.checkForListSize("favouriteOrders",favouriteOrders,student.getFavouriteList().size());
        return student;
    }
    private List<Integer> createSortedOrders(List<FavouriteOrder> favouriteOrders) throws InputNotLogicallyValidException {
        List<Integer> orders = new ArrayList<>();
        for (FavouriteOrder favouriteOrder : favouriteOrders){
            orders.add(favouriteOrder.getOrder());
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
