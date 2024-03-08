package com.enigma.wmb_api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuRequest {
    private String id;

    @NotBlank(message = "name cannot be blank")
    private String menu;

    @NotNull(message = "price cannot be null")
    @Min(value = 0, message = "price must be greater than or equal to 0")
    private Long price;
}