package com.pro.magistracy.tool.checker.accept;

import com.pro.magistracy.settings.Settings;
import com.pro.magistracy.settings.SettingsService;
import com.pro.magistracy.tool.checker.DateTimeChecker;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DateTimeAspect {

    private final DateTimeChecker dateTimeChecker;
    private final SettingsService settingsService;

    @Autowired
    public DateTimeAspect(DateTimeChecker dateTimeChecker, SettingsService settingsService) {
        this.dateTimeChecker = dateTimeChecker;
        this.settingsService = settingsService;
    }


    @Before("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public boolean checkDateTimeDocumentAcceptance () {
        Settings settings = settingsService.getSettings();
        return dateTimeChecker.checkDateTime(settings.getDateDocumentsAcceptanceStart(), settings.getDateDocumentsAcceptanceEnd());
    }


    @Before("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public boolean checkDateTimeAdmissionExam () {
        Settings settings = settingsService.getSettings();
        return dateTimeChecker.checkDateTime(settings.getDateAdmissionExamStart(), settings.getDateAdmissionExamEnd());
    }


    @Before("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public boolean checkDateTimeExaminationVerification () {
        Settings settings = settingsService.getSettings();
        return dateTimeChecker.checkDateTime(settings.getDateExaminationVerificationStart(), settings.getDateExaminationVerificationEnd());
    }

}
