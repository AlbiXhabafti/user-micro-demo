package com.example.userdemo.service;

import com.example.userdemo.dto.*;
import com.example.userdemo.model.User;
import com.example.userdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private static final long EXPIRE_TOKEN_AFTER_MINUTES = 30;
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

        ResponseEntity<CityDTO>cityDTOResponseEntity = restTemplate.getForEntity("http://localhost:7777/api/city/"+ user.getCityId(), CityDTO.class);
        CityDTO cityDTO = cityDTOResponseEntity.getBody();
        System.out.println(cityDTOResponseEntity.getStatusCode());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setUserDTO(userDTO);
        responseDTO.setDepartmentDTO(departmentDTO);
        responseDTO.setCity(cityDTO);
        return responseDTO;

    }
    public User save(User user){
        return userRepository.save(user);
    }
    public User getUserByName(String name){
        return userRepository.findByName(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);

        return UserDetailsImpl.build(user);
    }
    //kMetoda generaeToken perdoret tk forgotPasword
    private String generateToken() {
        StringBuilder token = new StringBuilder();

        return token.append(UUID.randomUUID().toString())
                .append(UUID.randomUUID().toString()).toString();
    }

    public String forgotPassword(String email) {

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByEmail(email));

        if (!userOptional.isPresent()) {
            return "Invalid email id.";
        }

        User user = userOptional.get();
        user.setToken(generateToken());
        user.setTokenCreationDate(LocalDateTime.now());

        user = userRepository.save(user);

        return user.getToken();
    }

    //isTokenExpired perdoret tek resetPassword
    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
    }
    public String resetPassword(String token, String password) {

        Optional<User> userOptional = Optional
                .ofNullable(userRepository.findByToken(token));

        if (!userOptional.isPresent()) {
            return "Invalid token.";
        }

        LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

        if (isTokenExpired(tokenCreationDate)) {
            return "Token expired.";

        }

        User user = userOptional.get();

        //String hashedPassword = passwordEncoder.encode(password);

        user.setPassword( password);
        user.setToken(null);
        user.setTokenCreationDate(null);

        userRepository.save(user);

        return "Your password successfully updated.";
    }
    public void deleteUser(Long id){

        userRepository.findById(id);

        userRepository.deleteById(id);
    }

}
