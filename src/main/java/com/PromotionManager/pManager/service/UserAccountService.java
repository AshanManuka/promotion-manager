package com.PromotionManager.pManager.service;


import com.PromotionManager.pManager.dto.userDto.UserAccountReqDto;
import com.PromotionManager.pManager.dto.userDto.UserAccountResDto;
import org.springframework.http.ResponseEntity;

public interface UserAccountService {

    ResponseEntity<?> createUserAccount(UserAccountReqDto reqDto);
}
