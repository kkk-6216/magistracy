package com.pro.magistracy.videocall;

import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/videocall")
public class VideoCallController {

    private final UserService userService;

    @Autowired
    public VideoCallController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}")
    public String interview(Model model, @PathVariable Long id) {

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("teacher", userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "videocall/videocall";
    }
}
