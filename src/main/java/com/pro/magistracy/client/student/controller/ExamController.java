package com.pro.magistracy.client.student.controller;

import com.pro.magistracy.client.student.dto.Student;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.ExamTestService;
import com.pro.magistracy.service.UserService;
import com.pro.magistracy.tool.UploadFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/exams")
public class ExamController {

    private final UserService userService;
    private final ExamTestService examTestService;

    @Autowired
    public ExamController(UserService userService, ExamTestService examTestService) {
        this.userService = userService;
        this.examTestService = examTestService;
    }

    @GetMapping
    @PreAuthorize("@dateTimeAspect.checkDateTimeAdmissionExam()")
    public String getExams(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if(user.getExam() != null) return "redirect:/";

        if(user.getImage() != null) {
            user.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("user", new Student(user));
        return "student/exam_page";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@dateTimeAspect.checkDateTimeAdmissionExam()")
    public String postExams(@RequestParam("file") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByEmail(username);

        if(user.getExam() != null) return "redirect:/";

        UploadFiles.uploadFile(username + "\\" + "exam", file, file.getOriginalFilename());

        examTestService.save(file.getOriginalFilename(), user);

        return "redirect:/";
    }
}
