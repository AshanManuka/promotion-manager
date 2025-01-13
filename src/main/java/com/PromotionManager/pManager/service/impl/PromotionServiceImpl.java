package com.PromotionManager.pManager.service.impl;

import com.PromotionManager.pManager.dto.PromotionReqDto;
import com.PromotionManager.pManager.dto.common.CommonResponse;
import com.PromotionManager.pManager.entity.Promotion;
import com.PromotionManager.pManager.entity.UserAccount;
import com.PromotionManager.pManager.repository.PromotionRepository;
import com.PromotionManager.pManager.repository.UserAccountRepository;
import com.PromotionManager.pManager.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Log4j2
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public ResponseEntity<?> createPromotion(PromotionReqDto reqDto, String userName) throws IOException {
        UserAccount userAccount = findUserByUserName(userName);

        if(userAccount == null){
            log.info("user not found");
            return ResponseEntity.ok(new CommonResponse<>(false, "User not Found"));
        }else{
            if(reqDto.getName().isEmpty() || reqDto.getStartDate() == null || reqDto.getEndDate() == null || reqDto.getBanner().isEmpty()){
                log.info("Empty userId input");
                return ResponseEntity.ok(new CommonResponse<>(false, "Empty Input"));
            }else{
                Promotion promotion = Promotion.builder()
                        .name(reqDto.getName())
                        .startDate(reqDto.getStartDate())
                        .endDate(reqDto.getEndDate())
                        .createdDate(new Date())
                        .updatedDate(new Date())
                        .banner(reqDto.getBanner().getBytes())
                        .user(userAccount)
                        .build();

                Promotion savedPromotion = promotionRepository.save(promotion);
                return ResponseEntity.ok(new CommonResponse<>(true, "Promotion Saved Successfully!"));
            }
        }
    }


    private UserAccount findUserByUserName(String userName){
        return userAccountRepository.findByUserName(userName);
    }

}
