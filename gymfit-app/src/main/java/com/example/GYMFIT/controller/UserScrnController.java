package com.example.GYMFIT.controller;

import com.example.GYMFIT.dto.UserFormDto;
import com.example.GYMFIT.entity.User;
import com.example.GYMFIT.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/WelcomeGymFit")
@Controller  //컨트롤러선언
public class UserScrnController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserScrnController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")  // 페이지 보여주기
    public String login() {
        log.info(passwordEncoder.encode("123"));
        return "signin/userSignForm";  //회원가입 페이지를 보여줌
    }

    @GetMapping("/new-user")  // 페이지 보여주기
    public String newUser(Model model) {
        model.addAttribute("userFormDto", new UserFormDto());
        return "user/userForm";  //회원가입 페이지를 보여줌
    }

    @PostMapping("/new-page")   // 사용자 등록 페이지에서 사용자 정보 제출
    public String createUserAndShowPage(@ModelAttribute("userFormDto") UserFormDto userFormDto,
                                        Model model){//form에서 넘어온 데이터 처리

        String plaintext = userFormDto.getUserPassword();
        userFormDto.setUserPassword(passwordEncoder.encode(plaintext));
        User createdUser = userService.create(userFormDto);
        if(createdUser != null){   // null이 아니라면 같은 이메일이 아니라는 것
            return "signin/userSignForm";   // 경로를 변경하면 된다.    // 회원가입이 성공하면 여기로 온다.
        }else{
            model.addAttribute("errorMessage", "실패, 다시 ㄱㄱㄱ");
            log.info("GRRRRRRRRRRRRRRRRRRRRRR");
            return "redirect:/WelcomeGymFit/new-user?errorMessage=EmailAlreadyExists";   // 같은 이메일이면 여기로 돌아옴(가입안됨) 404뜸
        }
    }
}