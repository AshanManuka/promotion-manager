package com.PromotionManager.pManager.dto.userDto;

import com.PromotionManager.pManager.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserAccountResDto {
    private String userName;
    private String password;
    private String role;
}
