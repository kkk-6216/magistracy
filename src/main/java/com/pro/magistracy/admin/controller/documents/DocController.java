package com.pro.magistracy.admin.controller.documents;

import com.pro.magistracy.admin.dto.Admin;
import com.pro.magistracy.client.student.dto.Student;
import com.pro.magistracy.model.Document;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/documents")
public class DocController {

    private final UserService userService;

    @Autowired
    public DocController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String index(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Admin userInfo = new Admin(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("userInfo", userInfo);

        model.addAttribute("users", userService.findStudentsHaveDocuments());
        return "admin/documents/documents";
    }

    @GetMapping("/users/{id}")
    public String showDocuments(@PathVariable Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user1 = userService.findByEmail(authentication.getName());

        Admin userInfo = new Admin(user1);
        if(user1.getImage() != null) {
            userInfo.setImage("/" + user1.getEmail() + "/logo/" + user1.getImage());
        }
        model.addAttribute("userInfo", userInfo);

        User user = userService.findById(id);
        Document document = user.getDocument();

        model.addAttribute("document", document);
        model.addAttribute("user", new Student(user));
        return "admin/documents/document";
    }
}
