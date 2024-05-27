package com.pro.magistracy.client.student.tool;

import com.pro.magistracy.model.Document;
import com.pro.magistracy.model.ExamTest;
import com.pro.magistracy.model.Interview;
import com.pro.magistracy.model.User;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Notification {
    private Boolean document;
    private Boolean exam;
    private Boolean interview;
    private Double gradeExam;
    private Double gradeInterview;

    public Notification(User user) {
        Document d = user.getDocument();
        ExamTest e = user.getExam();
        Interview i = user.getInterview();

        document = (user.getDocument() == null) ? null : user.getDocument().getStatus();
        exam = (user.getExam() == null) ? null : user.getExam().getStatus();
        if(Boolean.TRUE.equals(exam)) {
            gradeExam = user.getExam().getGrade();
        }
        interview = (user.getInterview() == null) ? null : user.getInterview().getStatus();
        if(Boolean.TRUE.equals(interview)) {
            gradeInterview = user.getInterview().getGrade();
        }
    }
}
