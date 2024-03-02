package com.enigma.wmb_api.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_bill")
public class TBill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "trans_date")
    private Date transDate;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private MCustomer customerId;

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