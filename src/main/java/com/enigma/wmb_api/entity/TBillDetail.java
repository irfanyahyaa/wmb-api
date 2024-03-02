package com.enigma.wmb_api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "t_bill_detail")
public class TBillDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "bill_id", nullable = false)
    @JsonBackReference
    private TBill billId;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private MMenu menuId;

    @Column(name = "menu_price", nullable = false, updatable = false)
    private Long menuPrice;

    @Column(name = "qty", nullable = false)
    private Integer qty;
}