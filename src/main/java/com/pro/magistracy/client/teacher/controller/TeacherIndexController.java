package com.pro.magistracy.client.teacher.controller;

import com.pro.magistracy.client.rector.dto.Rector;
import com.pro.magistracy.client.teacher.dto.Teacher;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/teachers")
public class TeacherIndexController {

    private final UserService userService;

    @Autowired
    public TeacherIndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Teacher userInfo = new Teacher(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("users", userService.findStudents());

        return "teacher/index";
    }

}
