package com.example.userdemo.service;

import com.example.userdemo.dto.DepartmentDTO;
import com.example.userdemo.dto.ResponseDTO;
import com.example.userdemo.dto.UserDTO;
import com.example.userdemo.model.User;
import com.example.userdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;


    private UserDTO mapToUser(User user){
        UserDTO dto = new UserDTO();
        dto.setName(user.getName());
        dto.setDepartmentId(user.getDepartmentId());
        return dto;
    }
    public ResponseDTO getUser(Long userId){
        User user = userRepository.findById(userId).get();
        UserDTO userDTO = mapToUser(user);

        ResponseEntity<DepartmentDTO>responseEntity = restTemplate.
                                                getForEntity("http://localhost:8888/api/departments/" + user.getDepartmentId(), DepartmentDTO.class);
        DepartmentDTO departmentDTO = responseEntity.getBody();
        System.out.println(responseEntity.getStatusCode());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setUserDTO(userDTO);
        responseDTO.setDepartmentDTO(departmentDTO);
        return responseDTO;

    }
    public User save(User user){
        return userRepository.save(user);
    }
    public User getUserByName(String name){
        return userRepository.findByName(name);
    }
}
