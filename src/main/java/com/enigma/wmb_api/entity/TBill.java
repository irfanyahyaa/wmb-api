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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "trans_date", updatable = false)
    private Date transDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MUser user;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private MTable table;

    @ManyToOne
    @JoinColumn(name = "trans_type_id")
    private MTransType transType;

    @OneToMany(mappedBy = "bill")
    @JsonManagedReference
    private List<TBillDetail> billDetails;
}