package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.response.PaymentResponse;
import com.enigma.wmb_api.entity.TBill;

public interface PaymentService {
    PaymentResponse createPayment(TBill bill);
}