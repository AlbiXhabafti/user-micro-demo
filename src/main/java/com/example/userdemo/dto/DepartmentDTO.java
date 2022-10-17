package com.example.userdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {
    private Long id;
    private String departmentName;
    private String SEM;
    private String code;
}
