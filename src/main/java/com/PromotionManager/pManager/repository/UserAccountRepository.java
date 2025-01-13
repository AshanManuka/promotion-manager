package com.PromotionManager.pManager.repository;

import com.PromotionManager.pManager.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("SELECT ua FROM UserAccount ua JOIN ua.user u WHERE ua.email=?1 AND u.username = ?2")
    UserAccount findByEmailAndUserName(String email, String userName);
}
