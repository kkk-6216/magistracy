package com.pro.magistracy.client.teacher.controller.exams;

import com.pro.magistracy.client.teacher.controller.exams.dto.Exam;
import com.pro.magistracy.admin.dto.Admin;
import com.pro.magistracy.client.teacher.dto.Teacher;
import com.pro.magistracy.model.ExamTest;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.ExamTestService;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachers/exams")
public class ExamTestController {

    private final UserService userService;
    private final ExamTestService examTestService;

    @Autowired
    public ExamTestController(UserService userService, ExamTestService examTestService) {
        this.userService = userService;
        this.examTestService = examTestService;
    }

    @GetMapping
    @PreAuthorize("@dateTimeAspect.checkDateTimeExaminationVerification()")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Admin userInfo = new Admin(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("userInfo", userInfo);

        model.addAttribute("users", userService.findStudentsHaveExams());
        return "teacher/exams/exams";
    }

    @GetMapping("/{id}")
    @PreAuthorize("@dateTimeAspect.checkDateTimeExaminationVerification()")
    public String check(@PathVariable Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user1 = userService.findByEmail(authentication.getName());

        Teacher userInfo = new Teacher(user1);
        if(user1.getImage() != null) {
            userInfo.setImage("/" + user1.getEmail() + "/logo/" + user1.getImage());
        }
        model.addAttribute("userInfo", userInfo);

        User user = userService.findById(id);
        ExamTest examTest = user.getExam();
        System.out.println(examTest);
        Exam exam = new Exam();
        exam.setGrade(examTest.getGrade());
        model.addAttribute("user", user);
        model.addAttribute("exam", exam);

        return "teacher/exams/exam-file-check";
    }

    @PatchMapping("/{id}")
    @PreAuthorize("@dateTimeAspect.checkDateTimeExaminationVerification()")
    public String approveExam(@PathVariable Long id, @ModelAttribute("exam") Exam exam) {
        User user = userService.findById(id);
        ExamTest userExam = user.getExam();
        userExam.setGrade(exam.getGrade());
        userExam.setStatus(true);

        examTestService.approvedExam(userExam);

        return "redirect:/teachers/exams";
    }
}
