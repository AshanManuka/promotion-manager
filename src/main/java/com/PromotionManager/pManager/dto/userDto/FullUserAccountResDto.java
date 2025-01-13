package com.PromotionManager.pManager.dto.userDto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FullUserAccountResDto {
    private long id;
    private String name;
    private String email;
    private String userName;
    private String role;

}
