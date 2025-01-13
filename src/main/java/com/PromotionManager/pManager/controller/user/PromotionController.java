package com.PromotionManager.pManager.controller.user;

import com.PromotionManager.pManager.dto.PromotionReqDto;
import com.PromotionManager.pManager.dto.userDto.UserAccountReqDto;
import com.PromotionManager.pManager.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/user")
public class PromotionController {

    private final PromotionService promotionService;


    @PostMapping("/create-promotion")
    public ResponseEntity<?> createPromotion(@ModelAttribute PromotionReqDto reqDto){
        log.info("POST req for save new Promotion title :{}", reqDto.getName());
        return promotionService.createPromotion(reqDto);
    }

    @PostMapping("/promotion")
    public ResponseEntity<?> getAllPromotions(){
        log.info("POST req for save new Promotion title ");
        return ResponseEntity.ok("reqDto");
    }



}
