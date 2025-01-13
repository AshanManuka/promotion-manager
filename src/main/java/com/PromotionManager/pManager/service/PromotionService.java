package com.PromotionManager.pManager.service;

import com.PromotionManager.pManager.dto.PromotionReqDto;
import org.springframework.http.ResponseEntity;

public interface PromotionService {
    ResponseEntity<?> createPromotion(PromotionReqDto reqDto);
}
