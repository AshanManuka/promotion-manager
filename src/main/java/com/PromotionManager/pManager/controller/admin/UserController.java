package com.PromotionManager.pManager.controller.admin;

import com.PromotionManager.pManager.dto.userDto.UserAccountReqDto;
import com.PromotionManager.pManager.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/admin")
public class UserController {

    private final UserAccountService userAccountService;

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserAccountReqDto reqDto){
        log.info("POST req for save user account name :{} , role :{}", reqDto.getName(), reqDto.getRole());
        return userAccountService.createUserAccount(reqDto);
    }

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        log.info("Get all user");
        return userAccountService.getAllUsers();
    }

    @GetMapping("/single-user")
    public ResponseEntity<?> getSingleUser(@RequestParam Long userId){
        log.info("Get single user userId : {}", userId);
        return userAccountService.getSingleUser(userId);
    }



    @PostMapping("/update-user")
    public ResponseEntity<?> updateUserAccount(@RequestBody UserAccountReqDto reqDto, @RequestParam Long userId){
        log.info("Update req for user Account, userId: {}", userId);
        return userAccountService.updateSingleUser(userId, reqDto);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<?> DeleteUserAccount(@RequestParam Long userId){
        log.info("Delete req for user Account, userId: {}", userId);
        return userAccountService.deleteUser(userId);
    }


}
