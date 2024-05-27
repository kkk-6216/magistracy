package com.pro.magistracy.service;

import com.pro.magistracy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly=true)
public class ApprovedStudentService {

    private final UserService userService;

    @Autowired
    public ApprovedStudentService(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    public void approvedStudents(int q) {
        userService.studentsIsApprovedDisable();
        List<User> users = new ArrayList<>(userService.findStudents().stream().filter(s -> s.getInterview() != null && s.getInterview().getGrade() != null).toList());

        users.sort(Comparator.comparingDouble(ApprovedStudentService::compareStudents).reversed());

        List<User> approvedStudents = new ArrayList<>();

        for (int i = 0; i < Math.min(q, users.size()); i++) {
            User student = users.get(i);
            student.setApproved(true);
            approvedStudents.add(student);
            userService.update(student);
        }

    }

    public static double compareStudents(User student) {
        return student.getExam().getGrade() + student.getInterview().getGrade();
    }

}
