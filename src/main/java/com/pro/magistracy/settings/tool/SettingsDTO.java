package com.pro.magistracy.settings.tool;

import com.pro.magistracy.settings.Settings;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SettingsDTO {

    private LocalDate dateDocumentsAcceptanceStart;
    private LocalTime timeDocumentsAcceptanceStart;

    private LocalDate dateDocumentsAcceptanceEnd;
    private LocalTime timeDocumentsAcceptanceEnd;

    private boolean documentsAcceptance;

    private LocalDate dateAdmissionExamStart;
    private LocalTime timeAdmissionExamStart;

    private LocalDate dateAdmissionExamEnd;
    private LocalTime timeAdmissionExamEnd;

    private boolean admissionExam;

    private LocalDate dateExaminationVerificationStart;
    private LocalTime timeExaminationVerificationStart;

    private LocalDate dateExaminationVerificationEnd;
    private LocalTime timeExaminationVerificationEnd;

    private boolean examinationVerification;

    private boolean formLogin;

    private boolean formRegister;

    public SettingsDTO(Settings settings) {
        dateDocumentsAcceptanceStart = settings.getDateDocumentsAcceptanceStart().toLocalDate();
        timeDocumentsAcceptanceStart = settings.getDateDocumentsAcceptanceStart().toLocalTime();

        dateDocumentsAcceptanceEnd = settings.getDateDocumentsAcceptanceEnd().toLocalDate();
        timeDocumentsAcceptanceEnd = settings.getDateDocumentsAcceptanceEnd().toLocalTime();

        documentsAcceptance = settings.isDocumentsAcceptance();

        dateAdmissionExamStart = settings.getDateAdmissionExamStart().toLocalDate();
        timeAdmissionExamStart = settings.getDateAdmissionExamStart().toLocalTime();

        dateAdmissionExamEnd = settings.getDateAdmissionExamEnd().toLocalDate();
        timeAdmissionExamEnd = settings.getDateAdmissionExamEnd().toLocalTime();

        admissionExam = settings.isAdmissionExam();

        dateExaminationVerificationStart = settings.getDateExaminationVerificationStart().toLocalDate();
        timeExaminationVerificationStart = settings.getDateExaminationVerificationStart().toLocalTime();

        dateExaminationVerificationEnd = settings.getDateExaminationVerificationEnd().toLocalDate();
        timeExaminationVerificationEnd = settings.getDateExaminationVerificationEnd().toLocalTime();

        examinationVerification = settings.isExaminationVerification();

        formLogin = settings.isFormLogin();
        formRegister = settings.isFormRegister();
    }
}
