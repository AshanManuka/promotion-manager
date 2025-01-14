package com.PromotionManager.pManager.service;

import com.PromotionManager.pManager.dto.promotion.PromotionReqDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface PromotionService {
    ResponseEntity<?> createPromotion(PromotionReqDto reqDto, String userName) throws IOException;

    ResponseEntity<?> getAllPromotionsByUser(String userName);

    ResponseEntity<?> updatePromotion(PromotionReqDto reqDto, Long promotionId, String userName) throws IOException;

    ResponseEntity<?> deletePromotion(Long promotionId, String userName);

    ResponseEntity<?> getSinglePromotionsByUser(String userName, Long promotionId);
}
