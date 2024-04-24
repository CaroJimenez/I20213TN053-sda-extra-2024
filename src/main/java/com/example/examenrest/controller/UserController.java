package com.example.examenrest.controller;

import com.example.examenrest.model.User;
import com.example.examenrest.model.UserDto;
import com.example.examenrest.service.UserService;
import com.example.examenrest.utils.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> create (@Valid @RequestBody UserDto userDto){
        try {
            ApiResponse<User> response = userService.create(userDto);
            HttpStatus statusCode = response.isError() ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse<>(null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<User>>> getAll(){
        try {
            ApiResponse<List<User>> response = userService.getAll();
            HttpStatus statusCode = response.isError() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse<>(null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getById(@PathVariable String id){
        try {
            ApiResponse<User> response = userService.getById(id);
            HttpStatus statusCode = response.isError() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
            return new ResponseEntity<>(response, statusCode);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse<>(null, true, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
