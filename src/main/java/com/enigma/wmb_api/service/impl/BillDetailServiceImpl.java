package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.entity.TBillDetail;
import com.enigma.wmb_api.repository.BillDetailRepository;
import com.enigma.wmb_api.service.BillDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillDetailServiceImpl implements BillDetailService {
    private final BillDetailRepository billDetailRepository;

    @Override
    public List<TBillDetail> createBulk(List<TBillDetail> billDetails) {
        return billDetailRepository.saveAllAndFlush(billDetails);
    }
}