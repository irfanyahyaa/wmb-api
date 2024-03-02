package com.enigma.wmb_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_user")
public class MUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "mobile_phone_no", length = 20)
    private String mobilePhoneNumber;

    @Column(name = "is_member")
    private Boolean isMember;
}