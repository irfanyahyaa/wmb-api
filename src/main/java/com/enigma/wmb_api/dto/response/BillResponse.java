package com.enigma.wmb_api.dto.response;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillResponse {
    private String id;
    private Date transDate;
    private String userId;
    private String tableId;
    private String transTypeId;
    private List<BillDetailResponse> billDetails;
}