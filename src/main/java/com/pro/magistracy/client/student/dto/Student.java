package com.pro.magistracy.client.student.dto;

import com.pro.magistracy.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Student {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date createdAt;
    private String roleName;
    private Role role;
    private Document document;
    private ExamTest exam;
    private Double examGrade;
    private Interview interview;
    private Double interviewGrade;
    private String image;
    private Boolean approved;

    public Student(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.role = user.getRole();
        this.roleName = user.getRole().name();
        this.document = user.getDocument();
        this.exam = user.getExam();
        this.interview = user.getInterview();
        this.image = user.getImage();
        this.approved = user.getApproved();
        this.examGrade = user.getExam() != null ? user.getExam().getGrade() : null;
        this.interviewGrade = user.getInterview() != null ? user.getInterview().getGrade() : null;
    }

}
