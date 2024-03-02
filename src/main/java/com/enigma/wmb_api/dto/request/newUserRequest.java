package com.enigma.wmb_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class newUserRequest {
    @NotBlank(message = "name cannot be blank")
    @Pattern(regexp = "[a-zA-Z]+", message = "name must be alphabetic")
    private String name;
    @NotBlank(message = "mobilePhoneNo cannot be blank")
    @Pattern(regexp = "[0-9]+", message = "mobilePhoneNo must be numeric")
    private String mobilePhoneNo;
    private Boolean isMember;
}