package com.pro.magistracy.admin.controller.documents;

import com.pro.magistracy.admin.controller.documents.dto.Doc;
import com.pro.magistracy.model.Document;
import com.pro.magistracy.model.User;
import com.pro.magistracy.service.DocumentService;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApproveController {

    private final UserService userService;
    private final DocumentService documentService;

    @Autowired
    public ApproveController(UserService userService, DocumentService documentService) {
        this.userService = userService;
        this.documentService = documentService;
    }

    @PostMapping("/admin/documents")
    public void approve(@RequestBody Doc doc) {
        User user = userService.findById(doc.getId());
        Document document = user.getDocument();
        documentService.update(document, doc.isClicked());
    }
}
