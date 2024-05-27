package com.pro.magistracy.client.student.controller;

import com.pro.magistracy.client.student.dto.Student;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Student userInfo = new Student(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }

        model.addAttribute("user", userInfo);

        return "student/profile";
    }

    @PatchMapping("/students/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Student userInfo) {

        User user  = userService.findById(id);
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());

        userService.update(user);

        return "redirect:/";

    }
}
