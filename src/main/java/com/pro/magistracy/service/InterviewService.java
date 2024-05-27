package com.pro.magistracy.service;

import com.pro.magistracy.model.Interview;
import com.pro.magistracy.model.User;
import com.pro.magistracy.repository.InterviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final UserService userService;

    @Autowired
    public InterviewService(InterviewRepository interviewRepository, UserService userService) {
        this.interviewRepository = interviewRepository;
        this.userService = userService;
    }

    @Transactional
    public void generateInterview() {
        List<User> students = userService.findStudents();
        for (User user : students) {
            if(user.getExam() == null || user.getExam().getGrade() == null || user.getInterview() != null) continue;
            Interview interview = new Interview();
            interview.setUser(user);
            interview.setStatus(false);
            interview.setMeetId(user.getId());
            user.setInterview(interview);

            interviewRepository.save(interview);
        }
    }


}
