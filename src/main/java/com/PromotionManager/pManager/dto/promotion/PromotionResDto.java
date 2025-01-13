package com.PromotionManager.pManager.dto.promotion;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PromotionResDto {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private byte[] banner;
}
