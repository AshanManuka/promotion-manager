package com.PromotionManager.pManager.controller;

import com.PromotionManager.pManager.dto.common.CommonResponse;
import com.PromotionManager.pManager.entity.User;
import com.PromotionManager.pManager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/get-user")
    public ResponseEntity<?> getUser(@RequestParam String userName){
        User uName = userService.findByUserName(userName);
        return ResponseEntity.ok(new CommonResponse<>(true, userName));
    }
}
