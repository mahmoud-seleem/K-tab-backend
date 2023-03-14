package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "student")
<<<<<<< HEAD
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","studentCommentList","disabilityList","studentNotificationList", "ratings"})
=======
@JsonIgnoreProperties({"hibernateLazyInitializer","handler",
        "studentCommentList","disabilityList",
        "studentNotificationList","interactions","ratings"})
>>>>>>> origin
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "student_id", nullable = false)
    private UUID studentId;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_email", nullable = false)
    private String studentEmail;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_photo", nullable = false)
    private String profilePhoto;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "education_level" , nullable = false)
    private String educationLevel;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentComment> studentCommentList;

    @OneToMany(mappedBy = "student")
    Set<Rating> ratings = new HashSet<>();

    @OneToOne(mappedBy = "student")
    private StudentSettings studentSettings;

    @ManyToMany()
    @JoinTable(
            name = "student_disabilities",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "disability_id"))
    private List<Disability> disabilityList;

    @OneToMany(mappedBy = "destinationStudent")
    private List<StudentNotification> studentNotificationList;

    @OneToMany(mappedBy = "student")
    private List<Interaction> interactions;

    public Student() {
    }

    public Student(String studentName, String studentEmail, String password, String profilePhoto, String contact) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.contact = contact;
    }

    public Student(String studentName, String studentEmail, String password, String profilePhoto, String contact, String educationLevel, List<StudentComment> studentCommentList) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.contact = contact;
        this.educationLevel = educationLevel;
        this.studentCommentList = studentCommentList;
    }

    public Student(String studentName) {
        this.studentName = studentName;
        this.studentEmail = "mahmoudsaleem522@gmail.com";
        this.contact = "01061424231";
        this.password = "12345678";
        this.profilePhoto = "profile_photo_link";
        this.educationLevel = "3rd secondary";
        this.studentCommentList = new ArrayList<>();
        this.disabilityList = new ArrayList<>();
        this.studentNotificationList = new ArrayList<>();
        this.interactions = new ArrayList<>();
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

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }

    public void addStudentComment(StudentComment studentComment) {
        this.getStudentCommentList().add(studentComment);
        studentComment.setStudent(this);
    }

    public void removeStudentComment(StudentComment studentComment) {
        this.getStudentCommentList().remove(studentComment);
        studentComment.setStudent(null);
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public StudentSettings getStudentSettings() {
        return studentSettings;
    }



    public void setStudentSettings(StudentSettings studentSettings) {
        this.studentSettings = studentSettings;
    }

    public List<StudentComment> getStudentCommentList() {
        return studentCommentList;
    }

    public void setStudentCommentList(List<StudentComment> studentCommentList) {
        this.studentCommentList = studentCommentList;
    }
    public void addDisability(Disability disability){
        this.disabilityList.add(disability);
        //disability.getStudentList().add(this);
    }
    public void removeDisability(Disability disability){
        this.disabilityList.remove(disability);
        //disability.getStudentList().remove(this);
    }
    public List<Disability> getDisabilityList() {
        return disabilityList;
    }
    public void setDisabilityList(List<Disability> disabilityList) {
        this.disabilityList = disabilityList;
    }

    public List<StudentNotification> getStudentNotificationList() {
        return studentNotificationList;
    }

    public void setStudentNotificationList(List<StudentNotification> studentNotificationList) {
        this.studentNotificationList = studentNotificationList;
    }

    public void addStudentNotification(StudentNotification studentNotification){
        this.getStudentNotificationList().add(studentNotification);
        studentNotification.setDestinationStudent(this);
    }
    public void removeStudentNotification(StudentNotification studentNotification){
        this.getStudentNotificationList().remove(studentNotification);
        studentNotification.setDestinationStudent(null);
    }
    public void addRating(Rating rating){
        this.getRatings().add(rating);
        rating.setStudent(this);
    }
    public void removeRating(Rating rating){
        this.getRatings().remove(rating);
        rating.setStudent(null);
    }
    public void addInteraction(Interaction interaction){
        this.getInteractions().add(interaction);
        interaction.setStudent(this);
    }
    public void removeInteraction(Interaction interaction){
        this.getInteractions().remove(interaction);
        interaction.setStudent(null);
    }
}