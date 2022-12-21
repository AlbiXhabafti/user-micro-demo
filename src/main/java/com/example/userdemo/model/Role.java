package com.example.userdemo.model;

import com.example.userdemo.enums.RoleEnum;

import javax.persistence.*;

@Entity
@Table
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private RoleEnum roleEnum;

    public Role() {
    }

    public Role(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }

    public Long getIg() {
        return id;
    }

    public void setIg(Long ig) {
        this.id = ig;
    }

    public RoleEnum getRoleEnum() {
        return roleEnum;
    }

    public void setRoleEnum(RoleEnum roleEnum) {
        this.roleEnum = roleEnum;
    }
}
