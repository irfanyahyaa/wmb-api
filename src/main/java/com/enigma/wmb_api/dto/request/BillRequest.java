package com.enigma.wmb_api.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillRequest {
    private String userId;
    private String tableId;
    private String transTypeId;
    private List<BillDetailRequest> billDetails;
}