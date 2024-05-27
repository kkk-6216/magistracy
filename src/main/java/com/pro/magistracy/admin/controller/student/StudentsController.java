package com.pro.magistracy.admin.controller.student;

import com.pro.magistracy.admin.dto.Admin;
import com.pro.magistracy.client.student.dto.Student;
import com.pro.magistracy.model.Role;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.ApprovedStudentService;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller()
@RequestMapping("/admin/students")
public class StudentsController {

    private final UserService userService;
    private final ApprovedStudentService approvedStudentService;

    @Autowired
    public StudentsController(UserService userService, ApprovedStudentService approvedStudentService) {
        this.userService = userService;
        this.approvedStudentService = approvedStudentService;
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

        model.addAttribute("users", userService.findStudents());
        return "admin/student/index";
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
        if(user1.getRole() != Role.ROLE_STUDENT) {
            return "redirect:/admin/students";
        }
        model.addAttribute("user", new Student(user1));
        return "admin/student/user-edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Student student) {
        User user  = userService.findById(id);
        if(user.getRole() != Role.ROLE_STUDENT) {
            return "redirect:/admin/students";
        }

        user.setFirstName(student.getFirstName());
        user.setLastName(student.getLastName());
        user.setEmail(student.getEmail());
        user.setRole(Role.valueOf(student.getRoleName()));

        if(student.getRole() == Role.ROLE_TEACHER) {
            user.setInspector(false);
            user.setNonLocked(false);
        }

        userService.update(user, student.getExamGrade(), student.getInterviewGrade());

        if (user.getRole() == Role.ROLE_TEACHER) {
            return "redirect:/admin/teachers";
        } else {
            return "redirect:/admin/students";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        User user = userService.findById(id);
        if(user.getRole() != Role.ROLE_STUDENT) {
            return "redirect:/admin/students";
        }
        userService.delete(id);

        return "redirect:/admin/students";
    }

    @GetMapping("/approved")
    public String getApprovedStudents(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(authentication.getName());

        Admin userInfo = new Admin(user);
        if(user.getImage() != null) {
            userInfo.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }
        model.addAttribute("user", userInfo);

        model.addAttribute("users", userService.findStudentsIsApproved());

        return "admin/student/approved";
    }
}
