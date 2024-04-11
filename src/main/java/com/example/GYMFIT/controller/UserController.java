package com.example.GYMFIT.controller;

import com.example.GYMFIT.dto.UserFormDto;
import com.example.GYMFIT.entity.User;
import com.example.GYMFIT.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/users")
@AllArgsConstructor
@RestController  //컨트롤러선언
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/new")  // 회원생성요청처리
//    public ResponseEntity<User> newUser(@RequestBody UserFormDto dto) {   //RequestBody는 Json데이터로 받아와야함
//        User created = userService.create(dto); //서비스로 생성
//        if (created != null) {
//            return ResponseEntity.status(HttpStatus.CREATED).body(created);  // 생성 성공시
//        } else {
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 생성 실패시
//        }
//    }
}


