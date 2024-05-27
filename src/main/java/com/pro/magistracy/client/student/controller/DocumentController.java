package com.pro.magistracy.client.student.controller;

import com.pro.magistracy.client.student.dto.Student;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.DocumentService;
import com.pro.magistracy.service.UserService;
import com.pro.magistracy.tool.UploadFiles;
import com.pro.magistracy.tool.checker.accept.DateTimeAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;
    private final UserService userService;

    @Autowired
    public DocumentController(DocumentService documentService, UserService userService) {
        this.documentService = documentService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("@dateTimeAspect.checkDateTimeDocumentAcceptance()")
    public String getDocument(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        if(user.getDocument() != null) return "redirect:/";

        if(user.getImage() != null) {
            user.setImage("/" + user.getEmail() + "/logo/" + user.getImage());
        }

        model.addAttribute("user", new Student(user));
        return "student/document";
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("@dateTimeAspect.checkDateTimeDocumentAcceptance()")
    public String addDocument(@RequestParam("file1") MultipartFile file1,
                              @RequestParam("file2") MultipartFile file2,
                              @RequestParam("file3") MultipartFile file3,
                              @RequestParam("file4") MultipartFile file4,
                              @RequestParam("file5") MultipartFile file5,
                              @RequestParam("file6") MultipartFile file6
    ) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByEmail(username);

        if(user.getDocument() != null) return "redirect:/";

        UploadFiles.uploadFile(username, file1, file1.getOriginalFilename());
        UploadFiles.uploadFile(username, file2, file2.getOriginalFilename());
        UploadFiles.uploadFile(username, file3, file3.getOriginalFilename());
        UploadFiles.uploadFile(username, file4, file4.getOriginalFilename());
        UploadFiles.uploadFile(username, file5, file5.getOriginalFilename());
        UploadFiles.uploadFile(username, file6, file6.getOriginalFilename());

        documentService.saveDocument(
                file1.getOriginalFilename(),
                file2.getOriginalFilename(),
                file3.getOriginalFilename(),
                file4.getOriginalFilename(),
                file5.getOriginalFilename(),
                file6.getOriginalFilename(),
                user
        );

        return "redirect:/";
    }

}
