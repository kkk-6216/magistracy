package com.pro.magistracy.client.student.controller;

import com.pro.magistracy.client.student.dto.Student;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    private final UserService userService;


    public StudentController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if(user.getImage() != null) {
            user.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }

        if(user.getInterview() != null) {
            model.addAttribute("interview", user.getInterview());
        } else {
            model.addAttribute("interview", null);
        }

        model.addAttribute("user", new Student(user));

        return "student/index";
    }



}
