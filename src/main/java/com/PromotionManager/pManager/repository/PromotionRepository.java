package com.PromotionManager.pManager.repository;

import com.PromotionManager.pManager.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
