package com.PromotionManager.pManager.service;

import com.PromotionManager.pManager.dto.promotion.PromotionReqDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface PromotionService {
    ResponseEntity<?> createPromotion(PromotionReqDto reqDto, String userName) throws IOException;

    ResponseEntity<?> getAllPromotionsByUser(String userName);
}
