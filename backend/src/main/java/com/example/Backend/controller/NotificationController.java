//package com.example.Backend.controller;
//
//import com.example.Backend.Repository.AuthorNotificationRepository;
//import com.example.Backend.Repository.AuthorRepository;
//import com.example.Backend.Repository.StudentNotificationRepository;
//import com.example.Backend.Repository.StudentRepository;
//import com.example.Backend.model.Author;
//import com.example.Backend.model.AuthorNotification;
//import com.example.Backend.model.Student;
//import com.example.Backend.model.StudentNotification;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/notify")
//public class NotificationController {
//
//    @Autowired
//    private StudentRepository studentRepository;
//    @Autowired
//    private AuthorRepository authorRepository;
//
//    @Autowired
//    private AuthorNotificationRepository authorNotificationRepository;
//
//    @Autowired
//    private StudentNotificationRepository studentNotificationRepository;
//    @GetMapping("/newstudent")
//    public Student creatNewStudent(){
//        Student student = new Student("mahmoud");
//        return (studentRepository.save(student));
//    }
//    @GetMapping("/newauthor")
//    public Author creatNewAuthor(){
//        Author author = new Author("mohamed");
//        return (authorRepository.save(author));
//    }
//    @GetMapping("/getallstudents")
//    public List<Student> getAllStudents(){
//        return studentRepository.findAll();
//    }
//
//    @GetMapping("/getallauthors")
//    public List<Author> getAllAuthors(){
//        return authorRepository.findAll();
//    }
//    @GetMapping("/getallauthornotify")
//    public List<AuthorNotification> getAllAuthorNotifications(){
//        return authorNotificationRepository.findAll();
//    }
//
//    @GetMapping("/getallstudentnotify")
//    public List<StudentNotification> getAllStudentNotifications(){
//        return studentNotificationRepository.findAll();
//    }
//
//    @GetMapping("/getauthornotify/{id}")
//    public List<AuthorNotification> getAuthorNotify(@PathVariable UUID id){
//        return authorRepository.findById(id).get().getAuthorNotificationList();
//    }
//    @GetMapping("/getnotifyauthor/{id}")
//    public Author getNotifyAuthor(@PathVariable UUID id){
//        return authorNotificationRepository.findById(id).get().getDestinationAuthor();
//    }
//
//    @GetMapping("/getstudentnotify/{id}")
//    public List<StudentNotification> getStudentNotify(@PathVariable UUID id){
//        return studentRepository.findById(id).get().getStudentNotificationList();
//    }
//    @GetMapping("/getnotifystudent/{id}")
//    public Student getNotifyStudent(@PathVariable UUID id){
//        return studentNotificationRepository.findById(id).get().getDestinationStudent();
//    }
//
//    @GetMapping("/newauthornotify/{studentId}/{content}")
//    public AuthorNotification createNewAuthorNotify(@PathVariable UUID studentId,@PathVariable String content){
//        AuthorNotification notification = new AuthorNotification(content ,studentId);
//        Author author = authorRepository.findByName("mohamed");
//        author.addAuthorNotification(notification);
//        return  authorNotificationRepository.save(notification);
//    }
//
//    @GetMapping("/newstudentnotify/{authorId}/{content}")
//    public StudentNotification createNewStudentNotify(@PathVariable UUID authorId,@PathVariable String content){
//        StudentNotification notification = new StudentNotification(content ,authorId);
//        Student author = studentRepository.findByName("mahmoud");
//        author.addStudentNotification(notification);
//        return  studentNotificationRepository.save(notification);
//    }
//
//}
