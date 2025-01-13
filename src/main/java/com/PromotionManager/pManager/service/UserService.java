package com.PromotionManager.pManager.service;

import com.PromotionManager.pManager.dto.userDto.UserReqDto;
import com.PromotionManager.pManager.entity.User;
import com.PromotionManager.pManager.enums.Role;
import com.PromotionManager.pManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserReqDto reqDto) {
        User user = User.builder()
                .username(reqDto.getUserName())
                .password(passwordEncoder.encode(reqDto.getPassword()))
                .role(Role.ADMIN)
                .build();
        return userRepository.save(user);
    }

    public User findByUserName(String username){
        return userRepository.findByUsername(username);
    }
}
