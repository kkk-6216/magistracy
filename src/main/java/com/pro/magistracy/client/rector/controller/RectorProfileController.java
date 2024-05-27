package com.pro.magistracy.client.rector.controller;

import com.pro.magistracy.client.rector.dto.Rector;
import com.pro.magistracy.client.student.dto.Student;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/rector/profile")
public class RectorProfileController {

    private final UserService userService;

    @Autowired
    public RectorProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Rector userInfo = new Rector(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }

        model.addAttribute("user", userInfo);

        return "rector/profile";
    }

    @PatchMapping
    public String update(@ModelAttribute Rector userInfo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        user.setEmail(userInfo.getEmail());
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());

        userService.update(user);

        return "redirect:/rector/profile";

    }
}
