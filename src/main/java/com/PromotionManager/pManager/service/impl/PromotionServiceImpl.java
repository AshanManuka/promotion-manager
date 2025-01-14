package com.PromotionManager.pManager.service.impl;

import com.PromotionManager.pManager.dto.promotion.PromotionReqDto;
import com.PromotionManager.pManager.dto.common.CommonResponse;
import com.PromotionManager.pManager.dto.promotion.PromotionResDto;
import com.PromotionManager.pManager.entity.Promotion;
import com.PromotionManager.pManager.entity.UserAccount;
import com.PromotionManager.pManager.repository.PromotionRepository;
import com.PromotionManager.pManager.repository.UserAccountRepository;
import com.PromotionManager.pManager.service.PromotionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final UserAccountRepository userAccountRepository;
    private final ModelMapper modelMapper;

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

    @Override
    public ResponseEntity<?> getAllPromotionsByUser(String userName) {
        UserAccount userAccount = findUserByUserName(userName);

        if(userAccount == null){
            log.info("user not found");
            return ResponseEntity.ok(new CommonResponse<>(false, "User not Found"));
        }else{
            List<Promotion> promotionList = promotionRepository.getPromotionByUserId(userAccount.getId());
            if(promotionList.isEmpty()){
                log.info("promotions not found");
                return ResponseEntity.ok(new CommonResponse<>(false, "promotions not Found"));
            }


            List<PromotionResDto> responseList = promotionList.stream()
                    .map(promotion -> modelMapper.map(promotion, PromotionResDto.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new CommonResponse<>(true, responseList));
        }

    }

    @Override
    public ResponseEntity<?> updatePromotion(PromotionReqDto reqDto, Long promotionId, String userName) throws IOException {
        UserAccount userAccount = findUserByUserName(userName);

        if (userAccount == null) {
            log.info("User not found");
            return ResponseEntity.ok(new CommonResponse<>(false, "User not Found"));
        }

        Promotion promotion = promotionRepository.getPromotionById(promotionId);
        if (promotion == null) {
            log.info("Promotion not found");
            return ResponseEntity.ok(new CommonResponse<>(false, "Promotion not Found"));
        }

        modelMapper.map(reqDto, promotion);

        promotion.setUser(userAccount);

        if (reqDto.getBanner() != null) {
            promotion.setBanner(reqDto.getBanner().getBytes());
        }
        promotion.setUpdatedDate(new Date());
        promotionRepository.save(promotion);

        return ResponseEntity.ok(new CommonResponse<>(true, "Promotion Updated Successfully"));
    }


    @Override
    public ResponseEntity<?> deletePromotion(Long promotionId, String userName) {
        UserAccount userAccount = findUserByUserName(userName);

        if(userAccount == null){
            log.info("user not found");
            return ResponseEntity.ok(new CommonResponse<>(false, "User not Found"));
        }

        Promotion promotion = promotionRepository.getPromotionById(promotionId);
        if(promotion == null){
            log.info("promotion not found");
            return ResponseEntity.ok(new CommonResponse<>(false, "promotion not Found"));
        }

        promotionRepository.delete(promotion);
        return ResponseEntity.ok(new CommonResponse<>(true, "Promotion Delete Successfully"));
    }

    @Override
    public ResponseEntity<?> getSinglePromotionsByUser(String userName, Long promotionId) {
        UserAccount userAccount = findUserByUserName(userName);

        if(userAccount == null){
            log.info("user not found");
            return ResponseEntity.ok(new CommonResponse<>(false, "User not Found"));
        }else{
            Promotion promotion = promotionRepository.getSinglePromotionById(promotionId);
            if(promotion == null){
                log.info("promotion not found");
                return ResponseEntity.ok(new CommonResponse<>(false, "promotions not Found"));
            }

            PromotionResDto resDto = modelMapper.map(promotion, PromotionResDto.class);
            return ResponseEntity.ok(new CommonResponse<>(true, resDto));
        }
    }


    private UserAccount findUserByUserName(String userName){
        return userAccountRepository.findByUserName(userName);
    }

}
