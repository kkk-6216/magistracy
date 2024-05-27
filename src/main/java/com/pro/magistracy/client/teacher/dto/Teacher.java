package com.pro.magistracy.client.teacher.dto;

import com.pro.magistracy.model.Role;
import com.pro.magistracy.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Teacher {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private String roleName;
    private String image;
    private Date createdAt;
    private Boolean inspector;

    public Teacher(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.role = Role.ROLE_TEACHER;
        this.roleName = Role.ROLE_TEACHER.name();
        this.image = user.getImage();
        this.createdAt = user.getCreatedAt();
        this.inspector = user.getInspector();
    }
}

