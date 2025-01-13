package com.PromotionManager.pManager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;
    private Date createdDate;
    private Date updatedDate;

    @ToString.Exclude
    @OneToOne(mappedBy = "userAccount")
    private User user;

}
