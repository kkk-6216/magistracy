package com.pro.magistracy.client.student.controller;

import com.pro.magistracy.client.student.dto.Student;
import com.pro.magistracy.client.student.tool.Notification;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notifications")
public class NotificationController {

    private final UserService userService;

    @Autowired
    public NotificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String notifications(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        if(user.getImage() != null) {
            user.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }

        Notification notification = new Notification(user);

        model.addAttribute("user", new Student(user));
        model.addAttribute("notification", notification);

        return "student/notification";
    }
}
