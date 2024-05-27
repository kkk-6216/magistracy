package com.pro.magistracy.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exam_tests")
@Getter
@Setter
@NoArgsConstructor
public class ExamTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "grade")
    private Double grade;

    @Column(name = "exam_file_path")
    private String examFilePath;

    @OneToOne(mappedBy = "exam")
    private User user;
}
