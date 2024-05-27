package com.pro.magistracy.service;

import com.pro.magistracy.model.Role;
import com.pro.magistracy.model.User;
import com.pro.magistracy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return user.get();
    }

    @Transactional
    public User save(User user) {
        user.setRole(Role.ROLE_STUDENT);
        user.setCreatedAt(new Date());
        user.setInspector(false);
        user.setNonLocked(true);
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Transactional
    public User saveTeacher(User user) {
        user.setCreatedAt(new Date());
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<User> findStudents() {
        return userRepository.findAllByRole(Role.ROLE_STUDENT);
    }

    public List<User> findTeachers() {
        return userRepository.findAllByRole(Role.ROLE_TEACHER);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findStudentsHaveDocuments() {
        return userRepository.findUsersByDocumentIdIsNotNull();
    }

    public List<User> findStudentsHaveExams() {
        return userRepository.findUsersByExamIdIsNotNull();
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User update(User user, Double examGrade, Double interviewGrade) {

        if (examGrade != null) {
            user.getExam().setGrade(examGrade);
        }

        if (interviewGrade != null) {
            user.getInterview().setGrade(interviewGrade);
        }

        return userRepository.save(user);
    }

    public User findRector() {
        return userRepository.findUserByRole(Role.ROLE_RECTOR).orElse(null);
    }

    public List<User> findStudentsIsApproved() {
        return userRepository.findUsersByApprovedIsTrue();
    }

    @Transactional
    public void inspector(Long id) {
        User user = userRepository.findById(id).orElse(null);
        assert user != null;
        user.setInspector(!user.getInspector());
        user.setNonLocked(!user.isNonLocked());
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }

    @Transactional
    public void setIsEnabledForNonAdminUsers(boolean isEnabled) {
        userRepository.setIsEnabledForNonAdminUsers(isEnabled);
    }

    @Transactional
    public void setTeachersNonInspector() {
        userRepository.setTeacherNonInspector();
    }

    public List<User> findStudentsHaveInterview() {
        return userRepository.findUsersByInterviewIdIsNotNull();
    }

    public List<User> findStudentsApproved() {
        return userRepository.findUsersByApprovedIsTrue();
    }

    @Transactional
    public void studentsIsApprovedDisable() {
        userRepository.setStudentsApproved(false);
    }
}
