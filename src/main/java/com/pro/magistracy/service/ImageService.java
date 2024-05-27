package com.pro.magistracy.service;

import com.pro.magistracy.model.User;
import com.pro.magistracy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageService {

    private final UserRepository userRepository;

    @Autowired
    public ImageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void imageSave(User user, String originalFilename) {
        user.setImage(originalFilename);
        userRepository.save(user);
    }
}
