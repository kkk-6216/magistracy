package com.pro.magistracy.settings;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Settings {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateDocumentsAcceptanceStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateDocumentsAcceptanceEnd;

    private boolean documentsAcceptance;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateAdmissionExamStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateAdmissionExamEnd;

    private boolean admissionExam;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateExaminationVerificationStart;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateExaminationVerificationEnd;

    private boolean examinationVerification;

    private boolean formLogin;

    private boolean formRegister;

}
