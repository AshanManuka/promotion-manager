package com.PromotionManager.pManager.dto.userDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserReqDto {
    private String userName;
    private String password;
}
