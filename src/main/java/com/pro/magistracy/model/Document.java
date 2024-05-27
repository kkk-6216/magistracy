package com.pro.magistracy.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "application_path")
    private String applicationPath;

    @Column(name = "bachelor_diploma_path")
    private String BachelorDiplomaPath;

    @Column(name = "transcript_path")
    private String transcriptPath;

    @Column(name = "recommendation_letter_path")
    private String recommendationLetterPath;

    @Column(name = "cvresume_path")
    private String cVResumePath;

    @Column(name = "portfolio_path")
    private String portfolioPath;

    @Column(name = "status")
    private Boolean status;

    @OneToOne(mappedBy = "document")
    private User user;
}



