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

    @PostMapping("/update-promotion")
    public ResponseEntity<?> updatePromotion(@ModelAttribute PromotionReqDto reqDto, @RequestParam Long promotionId, @RequestHeader("Authorization") String headerToken) throws IOException {
        String userName = getUserNameByToken(headerToken);
        log.info("update Promotion id :{} by User: {}", promotionId, userName);
        return promotionService.updatePromotion(reqDto, promotionId, userName);
    }

    @DeleteMapping("/delete-promotion")
    public ResponseEntity<?> deletePromotion(@RequestParam Long promotionId, @RequestHeader("Authorization") String headerToken) throws IOException {
        String userName = getUserNameByToken(headerToken);
        log.info("delete Promotion id :{} by User: {}", promotionId, userName);
        return promotionService.deletePromotion(promotionId, userName);
    }

    @GetMapping("/promotions-by-user")
    public ResponseEntity<?> getAllPromotionsByUser(@RequestHeader("Authorization") String headerToken){
        String userName = getUserNameByToken(headerToken);
        log.info("load all promotion by User: {}",userName);
        return promotionService.getAllPromotionsByUser(userName);
    }

    @GetMapping("/single-promotion-by-user")
    public ResponseEntity<?> getSinglePromotionsByUser(@RequestHeader("Authorization") String headerToken, Long promotionId){
        String userName = getUserNameByToken(headerToken);
        log.info("load single promotion by User: {}, promoId: {}",userName, promotionId);
        return promotionService.getSinglePromotionsByUser(userName,promotionId);
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
