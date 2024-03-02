package com.enigma.wmb_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class newUserRequest {
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotBlank(message = "mobile_phone_no cannot be blank")
    private String mobilePhoneNumber;
    private Boolean isMember;
}