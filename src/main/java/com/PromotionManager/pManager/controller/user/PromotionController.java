package com.PromotionManager.pManager.controller.user;

import com.PromotionManager.pManager.dto.promotion.PromotionReqDto;
import com.PromotionManager.pManager.service.PromotionService;
import com.PromotionManager.pManager.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/user")
public class PromotionController {

    private final PromotionService promotionService;
    private final JwtUtil jwtUtil;


    @PostMapping("/create-promotion")
    public ResponseEntity<?> createPromotion(@ModelAttribute PromotionReqDto reqDto, @RequestHeader("Authorization") String headerToken) throws IOException {
        String userName = getUserNameByToken(headerToken);
        log.info("POST req for save new Promotion title :{} by User: {}", reqDto.getName(), userName);
        return promotionService.createPromotion(reqDto, userName);
    }

    @GetMapping("/promotions-by-user")
    public ResponseEntity<?> getAllPromotionsByUser(@RequestHeader("Authorization") String headerToken){
        String userName = getUserNameByToken(headerToken);
        log.info("load all promotion by User: {}",userName);
        return promotionService.getAllPromotionsByUser(userName);
    }




    private String getUserNameByToken(String tokenHeader){
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            return jwtUtil.extractUsername(token);
        }else{
            return null;
        }

    }



}
