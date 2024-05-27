package com.pro.magistracy.client.teacher.controller.interviews;

import com.pro.magistracy.client.teacher.dto.Teacher;
import com.pro.magistracy.model.Interview;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/teachers/interviews")
public class InterviewsController {

    private final UserService userService;

    @Autowired
    public InterviewsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String interviews(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Teacher userInfo = new Teacher(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("userInfo", userInfo);

        model.addAttribute("users", userService.findStudentsHaveInterview());
        return "teacher/interviews/interviews";
    }

    @GetMapping("/{id}")
    public String interview(Model model, @PathVariable Long id) {

        model.addAttribute("user", userService.findById(id));

        model.addAttribute("interview", userService.findById(id).getInterview());
        model.addAttribute("teacher", userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "videocall/videocall";
    }

    @PatchMapping("/{id}")
    public String interviewPost(@PathVariable Long id, @ModelAttribute Interview interview) {
        User user = userService.findById(id);
        user.getInterview().setGrade(interview.getGrade());
        user.getInterview().setStatus(true);
        userService.save(user);
       return "redirect:/teachers/interviews";
    }
}
