package com.enigma.wmb_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Service
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtClaims {
    // claim adalah data yang akan didapatkan dari payload
    private String userAccountId;
    private List<String> roles;
}
