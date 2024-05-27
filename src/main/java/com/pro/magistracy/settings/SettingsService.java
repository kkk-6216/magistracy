package com.pro.magistracy.settings;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class SettingsService {

    private final ObjectMapper objectMapper;

    @Autowired
    public SettingsService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

    }

    public Settings getSettings() {
        Settings settings = null;
        try {
            settings = objectMapper.readValue(new URL("file:src\\main\\resources\\settings.json"), Settings.class);
        } catch (IOException ignored) {}

        return settings;
    }

    public void setDateDocumentsAcceptanceStart(LocalDateTime dateDocumentsAcceptanceStart) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("dateDocumentsAcceptanceStart", dateDocumentsAcceptanceStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }

    public void setDateDocumentsAcceptanceEnd(LocalDateTime dateDocumentsAcceptanceEnd) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("dateDocumentsAcceptanceEnd", dateDocumentsAcceptanceEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }
    

    public void setDateAdmissionExamStart(LocalDateTime dateAdmissionExamStart) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("dateAdmissionExamStart", dateAdmissionExamStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }

    public void setDateAdmissionExamEnd(LocalDateTime dateAdmissionExamEnd) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("dateAdmissionExamEnd", dateAdmissionExamEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }

    public void setDateExaminationVerificationStart(LocalDateTime dateExaminationVerificationStart) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("dateExaminationVerificationStart", dateExaminationVerificationStart.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }

    public void setDateExaminationVerificationEnd(LocalDateTime dateExaminationVerificationEnd) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("dateExaminationVerificationEnd", dateExaminationVerificationEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }

    public void setFormLogin(boolean formLogin) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("formLogin", formLogin);

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }

    public void setFormRegister(boolean formRegister) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("formRegister", formRegister);

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }

    public void setDocumentsAcceptance(boolean documentsAcceptance) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("documentsAcceptance", documentsAcceptance);

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }

    public void setAdmissionExam(boolean admissionExam) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("admissionExam", admissionExam);

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }

    public void setExaminationVerification(boolean examinationVerification) {
        try {

            JsonNode rootNode = objectMapper.readTree(new URL("file:src\\main\\resources\\settings.json"));

            ((ObjectNode) rootNode).put("examinationVerification", examinationVerification);

            objectMapper.writeValue(new File("src\\main\\resources\\settings.json"), rootNode);

        } catch (IOException ignored) {}
    }

}
