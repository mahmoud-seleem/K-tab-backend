package com.example.Backend.service;

import com.example.Backend.Repository.AuthorRepository;
import com.example.Backend.Repository.BookRepository;
import com.example.Backend.Repository.PaymentRepository;
import com.example.Backend.model.Author;
import com.example.Backend.model.Book;
import com.example.Backend.model.Payment;
import com.example.Backend.model.Student;
import com.example.Backend.schema.DisabilityCount;
import com.example.Backend.schema.PaymentCount;
import com.example.Backend.utils.Utils;
import com.example.Backend.validation.InputNotLogicallyValidException;
import com.example.Backend.validation.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.GetBucketRequestPaymentRequest;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

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

    @Autowired
    private PaymentRepository paymentRepository;

    private static final DecimalFormat FORMAT = new DecimalFormat("0.00");

    public List<PaymentCount> getTop3BookWithMostPayments(UUID authorId) throws InputNotLogicallyValidException {
        Author author = validationUtils.checkAuthorIsExisted(authorId);
        List<Book> books = author.getAuthorBooksList();
        List<PaymentCount> result = new ArrayList<>();
        List<PaymentCount> finalResult = new ArrayList<>();
        for (Book book : books) {
            result.add(new PaymentCount(
                    book.getBookId().toString(),
                    book.getTitle(),
                    book.getPaymentList().size()));
        }
        Collections.sort(result);
        Collections.reverse(result);
        int counter = 0;
        for (PaymentCount paymentCount : result) {
            if (counter < 3) {
                finalResult.add(paymentCount);
                counter++;
            } else {
                break;
            }
        }
        return finalResult;
    }

    public List<DisabilityCount> getDisabilityStatistics(UUID authorId) throws InputNotLogicallyValidException {
        Author author = validationUtils.checkAuthorIsExisted(authorId);
        List<Student> students = author.getAuthorStudents();
        DisabilityCount blindDis = new DisabilityCount("Blind");
        DisabilityCount visuallyImpairedDis = new DisabilityCount("Visually_Impaired");
        DisabilityCount dyslexiaDis = new DisabilityCount("Dyslexia");
        DisabilityCount normal = new DisabilityCount("Normal_People");
        int total = 0;
        for(Student student : students){
            List<String> disabilitiesNames = student.getDisabilitiesNames();
            if (disabilitiesNames.size() == 0) {// normal student
                normal.setStudentsCount(normal.getStudentsCount() + 1);
                total += 1;
                break;
            } else {
                total = traversDisabilities(
                        disabilitiesNames,
                        blindDis, visuallyImpairedDis, dyslexiaDis, total
                );
            }
}
    List<DisabilityCount> result = new ArrayList<>(Arrays.asList(
            blindDis, visuallyImpairedDis, dyslexiaDis, normal
    ));

    calculateDisabilitiesPercentage(total, result);
        return result;
                }

private int traversDisabilities(List<String> disabilitiesNames,
        DisabilityCount blindDis,
        DisabilityCount visuallyImpairedDis,
        DisabilityCount dyslexiaDis,
        int total){

        if(disabilitiesNames.contains("Blind")){
        blindDis.setStudentsCount(blindDis.getStudentsCount()+1);
        total+=1;
        disabilitiesNames.remove("Blind");

        }
        if(disabilitiesNames.contains("Visually_Impaired")){
        visuallyImpairedDis.setStudentsCount(visuallyImpairedDis.getStudentsCount()+1);
        total+=1;
        disabilitiesNames.remove("Visually_Impaired");
        }
        if(disabilitiesNames.contains("Dyslexia")){
        dyslexiaDis.setStudentsCount(dyslexiaDis.getStudentsCount()+1);
        total+=1;
        disabilitiesNames.remove("Dyslexia");
        }
        return total;
        }

private void calculateDisabilitiesPercentage(int total,List<DisabilityCount> list){
        for(DisabilityCount disabilityCount:list){
        if(total!=0){
        double percentage=((((double)disabilityCount.getStudentsCount())/total)*100);
        disabilityCount.setStudentsPercentage(
        String.format("%.2f",percentage));
        }else{
        disabilityCount.setStudentsPercentage("0.00");
        }
        }
        }

    public PaymentCount getBookViews(UUID userId, UUID bookId) throws InputNotLogicallyValidException {
        Book book = validationUtils.checkBookIsExisted(bookId);
        Author author = validationUtils.checkAuthorIsExisted(userId);
        validationUtils.checkForBookOwner(author,book);
        List<Payment> payments = paymentRepository.findAllByBookAndRecentOpenedDateGreaterThan(
                book, LocalDateTime.now().minusMinutes(1));
        PaymentCount paymentCount = new PaymentCount();
        if (payments != null){
            paymentCount.setNumberOfStudents(payments.size());
        }
        paymentCount.setBookTitle(book.getTitle());
        paymentCount.setBookId(bookId);
        return paymentCount;
    }
}

