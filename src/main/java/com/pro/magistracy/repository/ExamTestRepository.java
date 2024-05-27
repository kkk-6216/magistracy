package com.pro.magistracy.repository;

import com.pro.magistracy.model.ExamTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamTestRepository extends JpaRepository<ExamTest, Long> {
}
