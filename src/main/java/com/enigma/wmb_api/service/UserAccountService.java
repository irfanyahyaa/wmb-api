package com.enigma.wmb_api.service;

import com.enigma.wmb_api.entity.MUserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {
    MUserAccount getByUserId(String id);
    MUserAccount getByContext();
}