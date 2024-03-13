package com.enigma.wmb_api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDetailRequest {
    // berfungi untuk menyamakan format yang disediakan oleh midtrans
    @JsonProperty("order_id")
    private String orderId;

    @JsonProperty("gross_amount")
    private Long amount;
}