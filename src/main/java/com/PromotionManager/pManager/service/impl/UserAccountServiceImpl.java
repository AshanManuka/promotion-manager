package com.PromotionManager.pManager.service.impl;

import com.PromotionManager.pManager.dto.common.CommonResponse;
import com.PromotionManager.pManager.dto.userDto.FullUserAccountResDto;
import com.PromotionManager.pManager.dto.userDto.UserAccountReqDto;
import com.PromotionManager.pManager.dto.userDto.UserAccountResDto;
import com.PromotionManager.pManager.entity.User;
import com.PromotionManager.pManager.entity.UserAccount;
import com.PromotionManager.pManager.enums.Role;
import com.PromotionManager.pManager.repository.UserAccountRepository;
import com.PromotionManager.pManager.repository.UserRepository;
import com.PromotionManager.pManager.service.UserAccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> createUserAccount(UserAccountReqDto reqDto) {
       if(reqDto == null || reqDto.getName().isEmpty() || reqDto.getEmail().isEmpty() || reqDto.getUserName().isEmpty() || reqDto.getPassword().isEmpty()){
           log.info("Identified null Inputs");
           return ResponseEntity.ok(new CommonResponse<>(false, "Empty Inputs"));
       }else{

           UserAccount userAccount = userAccountRepository.findByEmailAndUserName(reqDto.getEmail(), reqDto.getUserName());
           if(userAccount != null){
               log.info("Email or username already exists");
               return ResponseEntity.ok(new CommonResponse<>(false, "Email or username already exists"));
           }else{
               UserAccount tempUserAccount = UserAccount.builder()
                       .name(reqDto.getName())
                       .email(reqDto.getEmail())
                       .createdDate(new Date())
                       .updatedDate(new Date())
                       .build();
               UserAccount savedAccount = userAccountRepository.save(tempUserAccount);
               log.info("saved userAccount details");

               User user = User.builder()
                       .username(reqDto.getUserName())
                       .password(passwordEncoder.encode(reqDto.getPassword()))
                       .role(String.valueOf(reqDto.getRole()))
                       .userAccount(savedAccount)
                       .build();

               User savedUser = userRepository.save(user);
               log.info("Saved user credentials");
               return ResponseEntity.ok(new CommonResponse<>(true,new UserAccountResDto(savedUser.getUsername(), reqDto.getPassword(), savedUser.getRole())));

           }


       }

    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        log.info("Getting all account list");
        List<FullUserAccountResDto> responseList = userAccountRepository.getAllAccounts();
        return ResponseEntity.ok(new CommonResponse<>(true, responseList));
    }


}
