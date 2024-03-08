package com.enigma.wmb_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableRequest {
    private String id;

    @NotBlank(message = "name cannot be blank")
    private String name;
}