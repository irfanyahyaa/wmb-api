package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.AuthRequest;
import com.enigma.wmb_api.dto.response.JwtClaims;
import com.enigma.wmb_api.entity.MUserAccount;

public interface JwtService {
    String generateToken(MUserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}