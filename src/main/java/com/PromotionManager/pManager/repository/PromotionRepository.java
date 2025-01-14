package com.PromotionManager.pManager.repository;

import com.PromotionManager.pManager.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    @Query("SELECT p FROM Promotion p WHERE p.user.id=?1")
    List<Promotion> getPromotionByUserId(Long id);

    @Query("SELECT p FROM Promotion p WHERE p.id=?1")
    Promotion getPromotionById(Long promotionId);

    @Query("SELECT p FROM Promotion p WHERE p.id=?1")
    Promotion getSinglePromotionById(Long promotionId);
}
