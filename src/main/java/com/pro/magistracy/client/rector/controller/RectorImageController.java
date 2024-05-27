package com.pro.magistracy.client.rector.controller;

import com.pro.magistracy.model.User;
import com.pro.magistracy.service.ImageService;
import com.pro.magistracy.service.UserService;
import com.pro.magistracy.tool.UploadFiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Controller
@RequestMapping("/rector/images")
public class RectorImageController {

    private final UserService userService;
    private final ImageService imageService;

    @Autowired
    public RectorImageController(UserService userService, ImageService imageService) {
        this.userService = userService;
        this.imageService = imageService;
    }

    @PostMapping
    public String addImage(@RequestParam("file") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByEmail(username);

        UploadFiles.uploadFile(username + "/logo", file, file.getOriginalFilename());

        imageService.imageSave(user, Objects.requireNonNull(file.getOriginalFilename()).isEmpty() ? null : file.getOriginalFilename());
        return "redirect:/rector";
    }
}
