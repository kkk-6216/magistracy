package com.pro.magistracy.admin.controller.rector;

import com.pro.magistracy.admin.dto.Admin;
import com.pro.magistracy.model.Role;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/rector")
public class RectorController {

    private final UserService userService;

    @Autowired
    public RectorController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String rector(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Admin userInfo = new Admin(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("userInfo", userInfo);

        model.addAttribute("user", userService.findRector());
        return "admin/rector/index";
    }

    @PatchMapping
    public String update(@ModelAttribute User user) {
        User user1 = userService.findRector();
        if(user1.getRole() != Role.ROLE_RECTOR) {
            return "redirect:/admin";
        }

        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());

        userService.update(user1);

        return "redirect:/admin/rector";
    }

}
