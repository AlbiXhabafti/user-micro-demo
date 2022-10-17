package com.example.userdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
    private UserDTO userDTO;
    private DepartmentDTO departmentDTO;
}
