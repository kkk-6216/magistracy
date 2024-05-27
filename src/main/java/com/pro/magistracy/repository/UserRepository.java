package com.pro.magistracy.repository;

import com.pro.magistracy.model.Role;
import com.pro.magistracy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRole(Role role);
    List<User> findUsersByDocumentIdIsNotNull();
    List<User> findUsersByExamIdIsNotNull();
    Optional<User> findUserByRole(Role role);
    List<User> findUsersByApprovedIsTrue();

    @Modifying
    @Query("UPDATE User u SET u.approved = false")
    void setStudentsApproved(boolean approved);

    @Modifying
    @Query("UPDATE User u SET u.isEnabled = :isEnabled WHERE u.role <> 'ROLE_ADMIN'")
    void setIsEnabledForNonAdminUsers(boolean isEnabled);

    @Modifying
    @Query("UPDATE User u SET u.isNonLocked = false, u.inspector = false WHERE u.role = 'ROLE_TEACHER'")
    void setTeacherNonInspector();

    List<User> findUsersByInterviewIdIsNotNull();
}
