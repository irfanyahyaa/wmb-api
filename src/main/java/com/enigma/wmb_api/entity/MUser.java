package com.enigma.wmb_api.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "m_user")
public class MUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "mobile_phone_no", length = 20)
    private String mobilePhoneNo;

    @Column(name = "is_member")
    private Boolean isMember;
}