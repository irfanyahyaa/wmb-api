package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.BillRequest;
import com.enigma.wmb_api.dto.request.GetBillRequest;
import com.enigma.wmb_api.dto.response.BillResponse;
import com.enigma.wmb_api.entity.TBill;
import org.springframework.data.domain.Page;

public interface BillService {
    BillResponse create(BillRequest request);

    Page<TBill> getAll(GetBillRequest request);

    BillResponse getById(String id);
}