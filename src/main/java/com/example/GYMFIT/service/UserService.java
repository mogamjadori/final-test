package com.example.GYMFIT.service;

import com.example.GYMFIT.dto.UserFormDto;
import com.example.GYMFIT.entity.User;
import com.example.GYMFIT.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional

public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(UserFormDto dto) {
         //사용자 이메일이 이미 존재하는지 확인
        if (!userRepository.existsByUserEmail(dto.getUserEmail())) {
            User user = dto.toEntity() ;//dto->엔티티로 변환한 후 User에 저장
            return userRepository.save(user);  // 사용자 저장 후 반환
        }
        return null;  // 이미 존재하는 사용자 이메일이라면 null반환
    }


    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        Optional<User> currentUser = Optional.ofNullable(userRepository.findByUserEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userEmail: " + userEmail)));

        UserFormDto userDetail = new UserFormDto();
        userDetail.setUserEmail(currentUser.get().getUserEmail());
        userDetail.setUserPassword(currentUser.get().getUserPassword());
        userDetail.setUserCno(currentUser.get().getUserCno());
        userDetail.setUserAdr(currentUser.get().getUserAdr());
        userDetail.setUserNm(currentUser.get().getUserNm());
        userDetail.setUserBirthDt(currentUser.get().getUserBirthDt());

        return userDetail;
    }
}