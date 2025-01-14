package com.PromotionManager.pManager.repository;

import com.PromotionManager.pManager.dto.userDto.FullUserAccountResDto;
import com.PromotionManager.pManager.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("SELECT ua FROM UserAccount ua JOIN ua.user u WHERE ua.email=?1 OR u.username = ?2")
    UserAccount findByEmailAndUserName(String email, String userName);

    @Query("SELECT new com.PromotionManager.pManager.dto.userDto.FullUserAccountResDto(ua.id, ua.name, ua.email, u.username, u.role) " +
            "FROM UserAccount ua JOIN ua.user u")
    List<FullUserAccountResDto> getAllAccounts();

    @Query("SELECT ua FROM UserAccount ua JOIN ua.user u WHERE u.username=?1")
    UserAccount findByUserName(String userName);

    @Query("SELECT new com.PromotionManager.pManager.dto.userDto.FullUserAccountResDto(ua.id, ua.name, ua.email, u.username, u.role) " +
            "FROM UserAccount ua JOIN ua.user u WHERE ua.id=?1")
    FullUserAccountResDto getSingleUser(Long userId);
}
