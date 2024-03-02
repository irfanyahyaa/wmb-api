package com.enigma.wmb_api.entity;

import com.enigma.wmb_api.constant.TransType;
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
@Table(name = "m_trans_type")
public class MTransType {
    @Id
    @Enumerated(EnumType.STRING)
    private TransType id;

    @Column(name = "description", length = 10)
    private String description;
}