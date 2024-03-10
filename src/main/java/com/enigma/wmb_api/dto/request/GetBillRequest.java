package com.enigma.wmb_api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetBillRequest {
    private Integer page;

    private Integer size;

    @Pattern(regexp = "[a-zA-Z]+", message = "sortBy must be alphabetic")
    private String sortBy;

    @Pattern(regexp = "[a-zA-Z]+", message = "direction must be alphabetic")
    private String direction;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String transDate;

    private String userId;

    private String tableId;

    private String transTypeId;
}