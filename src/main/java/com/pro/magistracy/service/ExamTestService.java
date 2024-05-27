package com.pro.magistracy.service;

import com.pro.magistracy.model.ExamTest;
import com.pro.magistracy.model.User;
import com.pro.magistracy.repository.ExamTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExamTestService {

    private final ExamTestRepository examTestRepository;

    @Autowired
    public ExamTestService(ExamTestRepository examTestRepository) {
        this.examTestRepository = examTestRepository;
    }

    @Transactional
    public void save(String file, User user) {
        ExamTest examTest = new ExamTest();
        examTest.setExamFilePath(file);
        examTest.setUser(user);
        user.setExam(examTest);
        examTest.setStatus(false);

        examTestRepository.save(examTest);
    }

    @Transactional
    public void approvedExam(ExamTest userExam) {
        examTestRepository.save(userExam);
    }
}
