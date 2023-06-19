package com.example.Backend.Repository;

import com.example.Backend.model.Book;
import com.example.Backend.model.Favourite;
import com.example.Backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FavouriteRepository extends JpaRepository<Favourite, UUID> {
    Favourite findByBookAndStudent(Book book,Student student);
    List<Favourite> findAllByStudentOrderByOrder(Student student);
    List<Favourite> findAllByStudentAndOrderGreaterThan(
            Student student,int order);
}
