package com.example.Backend.Repository;

import com.example.Backend.model.Book;
import com.example.Backend.model.Payment;
import com.example.Backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findAllByStudentOrderByRecentOpenedDate(Student student);
    List<Payment> findAllByStudentOrderByRecentOpenedDateDesc(Student student);
    Payment findByStudentAndBook(Student student, Book book);
}
