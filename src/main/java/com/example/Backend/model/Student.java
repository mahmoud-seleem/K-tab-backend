package com.example.Backend.model;

import com.example.Backend.security.AppUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.*;

@Entity
@Table(name = "student")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler",
        "studentCommentList",
        "studentNotificationList",
        "interactions",
        "ratings",
        "studentSettings",
        "paymentList",
        "readingList",
        "favouriteList",
        "studentDisabilityList"} , ignoreUnknown = true)
public class Student extends AppUser {

    @Id
    @GeneratedValue
    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_email", nullable = false, unique = true)
    private String studentEmail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "contact")
    private String contact;

    @Column(name = "education_level")
    private String educationLevel;
    @OneToMany(mappedBy = "student")
    private List<Comment> studentCommentList = new ArrayList<>();

    @OneToOne(mappedBy = "student")
    private StudentSettings studentSettings;

    @OneToMany(mappedBy = "student")
    private List<StudentDisability> studentDisabilityList = new ArrayList<>();

    @OneToMany(mappedBy = "destinationStudent")
    private List<StudentNotification> studentNotificationList;


    @OneToMany(mappedBy = "student")
    private List<Reading> readingList = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<Payment> paymentList = new ArrayList<>() ;

    @OneToMany(mappedBy = "student")
    private List<Favourite> favouriteList = new ArrayList<>() ;
    public Student() {
    }
    public Student( String studentName,
                    String studentEmail,
                    String password) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.password = password;
    }
    public Student(String studentName, String studentEmail) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
    }

    public Student(String studentName, String studentEmail, String password, String profilePhoto, String contact) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.contact = contact;
    }

    public Student(String studentName, String studentEmail, String password, String profilePhoto, String contact, String educationLevel) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.contact = contact;
        this.educationLevel = educationLevel;
    }


    public Student(String studentName) {
        this.studentName = studentName;
        this.studentEmail = "mahmoudsaleem522@gmail.com";
        this.contact = "01061424231";
        this.password = "12345678";
        this.profilePhoto = "profile_photo_link";
        this.educationLevel = "3rd secondary";
        this.studentCommentList = new ArrayList<>();
        this.studentDisabilityList = new ArrayList<>();
        this.studentNotificationList = new ArrayList<>();
        this.paymentList = new ArrayList<>();
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<Favourite> getFavouriteList() {
        return favouriteList;
    }

    public void setFavouriteList(List<Favourite> favouriteList) {
        this.favouriteList = favouriteList;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }


    public void addStudentComment(Comment studentComment) {
        this.getStudentCommentList().add(studentComment);
        studentComment.setStudent(this);
    }

    public void removeStudentComment(Comment studentComment) {
        this.getStudentCommentList().remove(studentComment);
        studentComment.setStudent(null);
    }

    public List<Reading> getReadingList() {
        return readingList;
    }

    public void setReadingList(List<Reading> readingList) {
        this.readingList = readingList;
    }

    //    public List<Rating> getRatings() {
//        return ratings;
//    }

//    public void setRatings(List<Rating> ratings) {
//        this.ratings = ratings;
//    }

    public StudentSettings getStudentSettings() {
        return studentSettings;
    }



    public void setStudentSettings(StudentSettings studentSettings) {
        this.studentSettings = studentSettings;
        studentSettings.setStudent(this);
    }

    public List<Comment> getStudentCommentList() {
        return studentCommentList;
    }

    public void setStudentCommentList(List<Comment> studentCommentList) {
        this.studentCommentList = studentCommentList;
    }

    public List<StudentDisability> getStudentDisabilityList() {
        return studentDisabilityList;
    }

    public void setStudentDisabilityList(List<StudentDisability> studentDisabilityList) {
        this.studentDisabilityList = studentDisabilityList;
    }

    public void addToFavourites(Favourite favourite){
        this.getFavouriteList().add(favourite);
        favourite.setStudent(this);
    }
    public void removeFromFavourites(Favourite favourite){
        this.getFavouriteList().remove(favourite);
        favourite.setStudent(null);
    }
    public void addPayment(Payment payment){
        this.getPaymentList().add(payment);
        payment.setStudent(this);
    }
    public void removePayment(Payment payment){
        this.getPaymentList().remove(payment);
        payment.setStudent(null);
    }
    public List<StudentNotification> getStudentNotificationList() {
        return studentNotificationList;
    }

    public void setStudentNotificationList(List<StudentNotification> studentNotificationList) {
        this.studentNotificationList = studentNotificationList;
    }

    public void removeStudentDisability(StudentDisability studentDisability){
        this.getStudentDisabilityList().remove(studentDisability);
        studentDisability.setStudent(null);
    }
    public void addStudentDisability(StudentDisability studentDisability){
        this.getStudentDisabilityList().add(studentDisability);
        studentDisability.setStudent(this);
    }
    public void addStudentNotification(StudentNotification studentNotification){
        this.getStudentNotificationList().add(studentNotification);
        studentNotification.setDestinationStudent(this);
    }
    public void removeStudentNotification(StudentNotification studentNotification){
        this.getStudentNotificationList().remove(studentNotification);
        studentNotification.setDestinationStudent(null);
    }
//    public void addRating(Rating rating){
//        this.getRatings().add(rating);
//        rating.setStudent(this);
//    }
//    public void removeRating(Rating rating){
//        this.getRatings().remove(rating);
//        rating.setStudent(null);
//    }

    public void addReading(Reading reading){
        this.getReadingList().add(reading);
        reading.setStudent(this);
    }
    public void removeReading(Reading reading){
        this.getReadingList().remove(reading);
        reading.setStudent(null);
    }


    public List<Map<String,Object>> getDisabilitiesInfo(){
        List<Map<String,Object>> disabilitiesInfo = new ArrayList<>();
        for (StudentDisability studentDisability
                : this.getStudentDisabilityList()){
            Map<String,Object> disabilityInfo = new HashMap<>();
            disabilityInfo.put("name",
                    studentDisability.getDisability().getDisabilityName());
            disabilityInfo.put("details",
                    studentDisability.getDisabilityDetails());
            disabilitiesInfo.add(disabilityInfo);
        }
        return disabilitiesInfo;
    }
    public List<String> getDisabilitiesNames(){
        List<String> disabilitiesNames = new ArrayList<>();
        for (StudentDisability studentDisability : this.getStudentDisabilityList()){
            disabilitiesNames.add(
                    studentDisability.getDisability().getDisabilityName());
        }
        return disabilitiesNames;
    }
}