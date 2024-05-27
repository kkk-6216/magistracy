package com.pro.magistracy.admin.controller;

import com.pro.magistracy.service.ApprovedStudentService;
import com.pro.magistracy.service.InterviewService;
import com.pro.magistracy.service.UserService;
import com.pro.magistracy.settings.SettingsService;
import com.pro.magistracy.settings.tool.FormDate;
import com.pro.magistracy.settings.tool.FormLR;
import com.pro.magistracy.settings.tool.SettingsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Controller
@RequestMapping("/admin/settings")
public class ConfigController {

    private final UserService userService;
    private final SettingsService settingsService;
    private final InterviewService interviewService;
    private final ApprovedStudentService approvedStudentService;

    @Autowired
    public ConfigController(UserService userService, SettingsService settingsService, InterviewService interviewService, ApprovedStudentService approvedStudentService) {
        this.userService = userService;
        this.settingsService = settingsService;
        this.interviewService = interviewService;
        this.approvedStudentService = approvedStudentService;
    }

    @GetMapping
    public String settings(Model model) {
        model.addAttribute("settings", new SettingsDTO(settingsService.getSettings()));

        LocalDateTime now = LocalDateTime.now();
        boolean interview = now.isAfter(settingsService.getSettings().getDateExaminationVerificationEnd());

        model.addAttribute("interview", interview);
        return "admin/settings";
    }

    @PostMapping("/form")
    @ResponseBody
    public void form(@RequestBody FormLR formLR) {
        switch (formLR.getKey()) {
            case "formLogin" -> {
                settingsService.setFormLogin(formLR.isValue());
                userService.setIsEnabledForNonAdminUsers(formLR.isValue());
            }
            case "formRegister" -> settingsService.setFormRegister(formLR.isValue());
            case "documentsAcceptance" -> settingsService.setDocumentsAcceptance(formLR.isValue());
            case "admissionExam" -> settingsService.setAdmissionExam(formLR.isValue());
            case "examinationVerification" -> settingsService.setExaminationVerification(formLR.isValue());
            case "interview" -> {
                if(formLR.isValue()) {interviewService.generateInterview();}
            }
            case "approve" -> {
                if(formLR.isValue()) {approvedStudentService.approvedStudents(10);}
            }
        }
    }

    @PostMapping("/dates")
    @ResponseBody
    public void dates(@RequestBody FormDate formDate) {
        LocalDateTime startDateTime = LocalDateTime.parse(formDate.getStartDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endDateTime = LocalDateTime.parse(formDate.getEndDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        switch (formDate.getKey()) {
            case "documentsAcceptance" -> {
                settingsService.setDateDocumentsAcceptanceStart(startDateTime);
                settingsService.setDateDocumentsAcceptanceEnd(endDateTime);
            }
            case "admissionExam" -> {
                settingsService.setDateAdmissionExamStart(startDateTime);
                settingsService.setDateAdmissionExamEnd(endDateTime);
            }
            case "examinationVerification" -> {
                settingsService.setDateExaminationVerificationStart(startDateTime);
                settingsService.setDateExaminationVerificationEnd(endDateTime);
            }
        }
    }
}
