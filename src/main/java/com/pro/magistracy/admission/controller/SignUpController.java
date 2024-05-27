package com.pro.magistracy.admission.controller;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.UserService;
import com.pro.magistracy.service.MailService;
import com.pro.magistracy.settings.SettingsService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.Month;

@Controller
@RequestMapping("/sign-up")
public class SignUpController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final SettingsService settingsService;

    @Autowired
    public SignUpController(UserService userService, PasswordEncoder passwordEncoder, MailService mailService, SettingsService settingsService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
        this.settingsService = settingsService;
    }

    @GetMapping
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "/admission/sign_up";
    }

    @PostMapping
    public String signUp(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) throws MessagingException {
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "Passwords do not match");
            return "/admission/sign_up";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);

        String path = "C:\\origin\\magistracy\\src\\main\\resources\\templates\\mail\\success-sign-up.html";

        mailService.sendEmail(
            user.getEmail(),
            "Уведомление о успешной регистрации...",
            path,
            false
        );

        return "redirect:/sign-in";
    }
}
