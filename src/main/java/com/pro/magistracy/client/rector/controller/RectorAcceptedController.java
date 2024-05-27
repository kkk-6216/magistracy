package com.pro.magistracy.client.rector.controller;

import com.pro.magistracy.client.rector.dto.Rector;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.ApprovedStudentService;
import com.pro.magistracy.service.MailService;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/rector")
public class RectorAcceptedController {

    private final UserService userService;
    private final ApprovedStudentService approvedStudentService;
    private final MailService mailService;

    @Autowired
    public RectorAcceptedController(UserService userService, ApprovedStudentService approvedStudentService, MailService mailService) {
        this.userService = userService;
        this.approvedStudentService = approvedStudentService;
        this.mailService = mailService;
    }

    @GetMapping("/accepted")
    public String accepted(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Rector userInfo = new Rector(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("users", userService.findStudentsIsApproved());
        return "rector/accepted";
    }

    @PostMapping("/accept")
    public String accept() {
        return "redirect:/rector/accepted";
    }
}
