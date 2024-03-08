package com.enigma.wmb_api.dto.response;

import com.enigma.wmb_api.constant.TransType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableResponse {
    private String id;

    private String name;
}
