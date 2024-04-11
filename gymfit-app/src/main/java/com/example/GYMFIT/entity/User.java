package com.example.GYMFIT.entity;

import com.example.GYMFIT.constant.Role;
import com.example.GYMFIT.dto.UserFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Entity(name="user")
@Table(name = "user")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String userEmail;  //사용자 이메일

    @Column
    private String userPassword;   // 사용자 패스워드

    @Column
    private String userAdr;         // 사용자 주소

    @Column
    private String userCno;     // 사용자 연락처

    @Column
    private String userNm;      // 사용자 이름

    @Column
    private String userBirthDt;     // 사용자 생년월일

//    @Enumerated(EnumType.STRING)
//    private Role role;




    //User객체의 생성 및 초기화를 위해 생성자 추가 (1)  -> controller가 (2)번인데 service로 받기에 현재는 없음
//    public User(Long id, String userEmail, String userPassword, String userAdr, String userCno, String userNm, String userBirthDt) {
//        this.id = id;
//        this.userEmail = userEmail;
//        this.userPassword = userPassword;
//        this.userAdr = userAdr;
//        this.userCno = userCno;
//        this.userNm = userNm;
//        this.userBirthDt = userBirthDt;
//    }
}

