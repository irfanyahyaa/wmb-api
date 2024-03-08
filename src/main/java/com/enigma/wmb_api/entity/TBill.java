package com.enigma.wmb_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "t_bill")
public class TBill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "trans_date")
    private Date transDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MUser userId;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private MTable tableId;

    @ManyToOne
    @JoinColumn(name = "trans_type_id")
    private MTransType transTypeId;

    @OneToMany(mappedBy = "billId")
    @JsonManagedReference
    private List<TBillDetail> billDetail;
}