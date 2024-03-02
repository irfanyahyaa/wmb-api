package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.TBillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetail extends JpaRepository<TBillDetail, String> {
}
