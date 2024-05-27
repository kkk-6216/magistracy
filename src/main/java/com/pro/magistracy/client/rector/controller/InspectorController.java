package com.pro.magistracy.client.rector.controller;

import com.pro.magistracy.model.User;
import com.pro.magistracy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InspectorController {

    private final UserService userService;

    @Autowired
    public InspectorController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/rector/teachers/{id}")
    public void inspector(@PathVariable Long id, @RequestBody Object o) {
        userService.inspector(id);
    }
}
