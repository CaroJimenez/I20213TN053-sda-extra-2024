package com.example.examenrest.service;

import com.example.examenrest.model.User;
import com.example.examenrest.model.UserDto;
import com.example.examenrest.model.UserRepository;
import com.example.examenrest.utils.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Transactional(rollbackFor = SQLException.class)
    public ApiResponse<User> create (UserDto user) {

        User email = userRepository.findByEmail(user.getEmail());
        if (email != null){
            return new ApiResponse<>
                    (null, true, HttpStatus.BAD_REQUEST, "Email already exists");
        }
        String id = UUID.randomUUID().toString();
        String newId = id.substring(0, 36);
        user.setId(newId);

        User newUser = new User();

        BeanUtils.copyProperties(user, newUser);
        userRepository.save(newUser);

        return new ApiResponse<>
                (newUser, false, HttpStatus.OK, "User created successfully");
    }
    @Transactional(readOnly = true)
    public ApiResponse<List<User>> getAll(){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            return new ApiResponse<>(
                    null, true, HttpStatus.NOT_FOUND, "Not users found"
            );
        }
        return new ApiResponse<>(
                users, false, HttpStatus.OK, "Users found"
        );
    }

    @Transactional(readOnly = true)
    public ApiResponse<User> getById(String id){
        User user = userRepository.findById(id).orElse(null);

        if (user == null){
            return new ApiResponse<>(
                    null, true, HttpStatus.NOT_FOUND, "User not found"
            );
        }

        LocalDate birthDate = user.getDob();
        LocalDate currentDate = LocalDate.now();
        int age = Period.between(birthDate, currentDate).getYears();
        String adult;
        if (age >= 18){
            adult ="mayor de edad";
        }else{
            adult = "menor de edad";
        }

        return new ApiResponse<>(
                user, false, HttpStatus.OK, "El usuario es "+adult
        );
    }
}
