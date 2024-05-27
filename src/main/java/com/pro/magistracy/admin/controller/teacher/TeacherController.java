package com.pro.magistracy.admin.controller.teacher;

import com.pro.magistracy.admin.dto.Admin;
import com.pro.magistracy.client.student.dto.Student;
import com.pro.magistracy.client.teacher.dto.Teacher;
import com.pro.magistracy.model.Role;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.MailService;
import com.pro.magistracy.service.UserService;
import com.pro.magistracy.tool.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/teachers")
public class TeacherController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordGenerator passwordGenerator;
    private final MailService mailService;

    @Autowired
    public TeacherController(UserService userService, PasswordEncoder passwordEncoder, PasswordGenerator passwordGenerator, MailService mailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.passwordGenerator = passwordGenerator;
        this.mailService = mailService;
    }

    @GetMapping
    public String index(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Admin userInfo = new Admin(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("user", userInfo);

        model.addAttribute("users", userService.findTeachers());
        return "admin/teacher/index";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Admin userInfo = new Admin(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("userInfo", userInfo);

        User user1 = userService.findById(id);
        if(user1.getRole() != Role.ROLE_TEACHER) {
            return "redirect:/admin/teachers";
        }
        model.addAttribute("user", new Teacher(user1));
        return "admin/teacher/user-edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Teacher teacher) {
        User user  = userService.findById(id);
        if(user.getRole() != Role.ROLE_TEACHER) {
            return "redirect:/admin/teachers";
        }

        user.setFirstName(teacher.getFirstName());
        user.setLastName(teacher.getLastName());
        user.setEmail(teacher.getEmail());
        user.setRole(Role.valueOf(teacher.getRoleName()));

        if(teacher.getRole() != Role.ROLE_TEACHER) {
            user.setInspector(null);
        }

        userService.update(user);

        if (user.getRole() == Role.ROLE_TEACHER) {
            return "redirect:/admin/teachers";
        } else {
            return "redirect:/admin/students";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        User user = userService.findById(id);
        if(user.getRole() != Role.ROLE_TEACHER) {
            return "redirect:/admin/students";
        }
        userService.delete(id);

        return "redirect:/admin/teachers";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Admin userInfo = new Admin(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("userInfo", userInfo);

        model.addAttribute("user", new Teacher(new User()));
        return "admin/teacher/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Teacher teacher) {
        User user = new User();
        user.setFirstName(teacher.getFirstName());
        user.setLastName(teacher.getLastName());
        user.setEmail(teacher.getEmail());
        user.setRole(Role.ROLE_TEACHER);
        user.setInspector(false);
        user.setNonLocked(false);
        user.setEnabled(true);

        String password = passwordGenerator.generatePassword(8);
        user.setPassword(passwordEncoder.encode(password));

        userService.saveTeacher(user);

        mailService.sendEmail(user.getEmail(),
                "Университет Кыргыз-Турк Манас",
                "<!DOCTYPE html>\n" +
                        "<html lang=\"ru\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                        "    <title>Вход в университет КТМУ</title>\n" +
                        "\n" +
                        "    <link href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                        "    <style>\n" +
                        "       \n" +
                        "        body {\n" +
                        "            background-color: #f7f7f7;\n" +
                        "        }\n" +
                        "        .login-container {\n" +
                        "            margin-top: 50px; \n" +
                        "            max-width: 400px; \n" +
                        "        }\n" +
                        "        .login-header {\n" +
                        "            text-align: center; \n" +
                        "            margin-bottom: 30px; \n" +
                        "        }\n" +
                        "        .card {\n" +
                        "            background-color: #d4edda; /* Светло-зеленый фон для карточки */\n" +
                        "            border-radius: 10px; \n" +
                        "            box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.1); \n" +
                        "        }\n" +
                        "        .card-body {\n" +
                        "            border-radius: 10px; \n" +
                        "        }\n" +
                        "        .card-title {\n" +
                        "            text-align: center; \n" +
                        "            margin-bottom: 20px;\n" +
                        "            padding-top: 20px; \n" +
                        "            font-size: 24px; \n" +
                        "            color: #155724; \n" +
                        "        }\n" +
                        "        .card-content {\n" +
                        "            padding: 20px; \n" +
                        "        }\n" +
                        "        .card-content span {\n" +
                        "            font-weight: bold; \n" +
                        "        }\n" +
                        "        .card-content p {\n" +
                        "            margin: 5px 0; \n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div class=\"container login-container\">\n" +
                        "    <div class=\"card\">\n" +
                        "        <div class=\"card-body\">\n" +
                        "            <h1 class=\"card-title\">Добро пожаловать в университет КТМУ</h1>\n" +
                        "            <div class=\"card-content\">\n" +
                        "                <span>Ваша почта:</span>\n" +
                        "                <p>" + user.getEmail() + "</p>\n" +
                        "                <span>Ваш пароль:</span>\n" +
                        "                <p>" + password + "</p>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "    </div>\n" +
                        "</div>\n" +
                        "</body>\n" +
                        "</html>\n",
                true
                );

        return "redirect:/admin/teachers";
    }
}
