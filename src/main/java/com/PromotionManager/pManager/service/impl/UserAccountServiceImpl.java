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
import java.util.Optional;
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

    @Override
    public ResponseEntity<?> updateSingleUser(Long userId, UserAccountReqDto reqDto) {
        if(userId == null || reqDto == null){
            log.info("Empty inputs");
            return ResponseEntity.ok(new CommonResponse<>(false, "Empty Inputs"));

        }else{
            Optional<UserAccount> tempUserAccount = userAccountRepository.findById(userId);

            if(tempUserAccount.isEmpty()){
                log.info("User not found");
                return ResponseEntity.ok(new CommonResponse<>(false, "User not Found"));
            }else{
                UserAccount userAccount = tempUserAccount.get();
                User user = userAccount.getUser();

                userAccount.setName(reqDto.getName());
                userAccount.setEmail(reqDto.getEmail());
                userAccount.setUpdatedDate(new Date());

                    if (reqDto.getUserName() != null) {
                        user.setUsername(reqDto.getUserName());
                    }
                    if (reqDto.getPassword() != null) {
                        user.setPassword(passwordEncoder.encode(reqDto.getPassword()));
                    }

                userAccountRepository.save(userAccount);

                log.info("User and UserAccount updated successfully");
                return ResponseEntity.ok(new CommonResponse<>(true, "User updated successfully"));
            }
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        if (userId == null) {
            log.info("Empty userId input");
            return ResponseEntity.ok(new CommonResponse<>(false, "Empty Input"));
        }

        Optional<UserAccount> tempUserAccount = userAccountRepository.findById(userId);

        if (tempUserAccount.isEmpty()) {
            log.info("User not found");
            return ResponseEntity.ok(new CommonResponse<>(false, "User not Found"));
        }

        UserAccount userAccount = tempUserAccount.get();

        User user = userAccount.getUser();
        if (user != null) {
            userRepository.delete(user);
        }

        userAccountRepository.delete(userAccount);

        log.info("User and userAccount deleted successfully");
        return ResponseEntity.ok(new CommonResponse<>(true, "User deleted successfully"));
    }



}
