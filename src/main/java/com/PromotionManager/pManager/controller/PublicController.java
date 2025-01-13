package com.PromotionManager.pManager.controller;

import com.PromotionManager.pManager.dto.userDto.UserReqDto;
import com.PromotionManager.pManager.entity.User;
import com.PromotionManager.pManager.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/app-status")
    public ResponseEntity<String> getAppStatus() {
        return ResponseEntity.ok("Application is running");
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserReqDto reqDto){
        log.info("saving admin profile name:{}",reqDto.getUserName());
        User savedUser = userService.saveUser(reqDto);
        return ResponseEntity.ok(savedUser.getUsername()+" Saved Successfully!");
    }
}
