package com.enigma.wmb_api.service;

import com.enigma.wmb_api.entity.TBillDetail;

import java.util.List;

public interface BillDetailService {
    List<TBillDetail> createBulk(List<TBillDetail> billDetails);
}