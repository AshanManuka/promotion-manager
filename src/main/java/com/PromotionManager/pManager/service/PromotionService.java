package com.PromotionManager.pManager.service;

import com.PromotionManager.pManager.dto.PromotionReqDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface PromotionService {
    ResponseEntity<?> createPromotion(PromotionReqDto reqDto, String userName) throws IOException;
}
