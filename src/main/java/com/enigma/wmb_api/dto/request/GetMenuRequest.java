package com.enigma.wmb_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMenuRequest {
    private Integer page;

    private Integer size;

    @Pattern(regexp = "[a-zA-Z]+", message = "sortBy must be alphabetic")
    private String sortBy;

    @Pattern(regexp = "[a-zA-Z]+", message = "direction must be alphabetic")
    private String direction;

    private String query;

    private String menu;

    @Min(value = 0, message = "price must be greater than or equal to 0")
    private Long minPrice;

    private Long maxPrice;
}