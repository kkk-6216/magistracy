package com.pro.magistracy.client.rector.controller;

import com.pro.magistracy.admin.dto.Admin;
import com.pro.magistracy.client.rector.dto.Rector;
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
@RequestMapping("/rector")
public class RectorIndexController {

    private final UserService userService;

    @Autowired
    public RectorIndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Rector userInfo = new Rector(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("users", userService.findStudents());

        return "rector/index";
    }


}
