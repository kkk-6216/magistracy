package com.pro.magistracy.service;

import com.pro.magistracy.model.Document;
import com.pro.magistracy.model.User;
import com.pro.magistracy.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Transactional
    public Document saveDocument(String file1,
                                 String file2,
                                 String file3,
                                 String file4,
                                 String file5,
                                 String file6,
                                 User user) {
        Document document = new Document();

        document.setApplicationPath(file1);
        document.setBachelorDiplomaPath(file2);
        document.setTranscriptPath(file3);
        document.setRecommendationLetterPath(file4);
        document.setCVResumePath(file5);
        document.setPortfolioPath(file6);

        document.setUser(user);
        user.setDocument(document);

        document.setStatus(false);

        return documentRepository.save(document);
    }

    @Transactional
    public void update(Document document, boolean clicked) {
        document.setStatus(clicked);
        documentRepository.save(document);
    }
}
