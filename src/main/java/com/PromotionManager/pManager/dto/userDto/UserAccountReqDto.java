package com.PromotionManager.pManager.dto.userDto;

import com.PromotionManager.pManager.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserAccountReqDto {
    private String name;
    private String email;
    private String userName;
    private String password;
    private Role role;
}
