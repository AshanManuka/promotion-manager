package com.PromotionManager.pManager.service.impl;

import com.PromotionManager.pManager.dto.PromotionReqDto;
import com.PromotionManager.pManager.repository.PromotionRepository;
import com.PromotionManager.pManager.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;

    @Override
    public ResponseEntity<?> createPromotion(PromotionReqDto reqDto) {


        return null;
    }
}
