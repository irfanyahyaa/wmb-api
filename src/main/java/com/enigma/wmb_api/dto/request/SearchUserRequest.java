package com.enigma.wmb_api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchUserRequest {
    private Integer page;

    private Integer size;

    @Pattern(regexp = "[a-zA-Z]+", message = "sortBy must be alphabetic")
    private String sortBy;

    @Pattern(regexp = "[a-zA-Z]+", message = "direction must be alphabetic")
    private String direction;

    @Pattern(regexp = "[a-zA-Z]+", message = "name must be alphabetic")
    private String name;

    @Pattern(regexp = "[0-9]+", message = "mobilePhoneNo must be numeric")
    private String mobilePhoneNo;

    private Boolean isMember;
}
