package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.UserRole;
import com.enigma.wmb_api.dto.request.AuthRequest;
import com.enigma.wmb_api.dto.response.LoginResponse;
import com.enigma.wmb_api.dto.response.RegisterResponse;
import com.enigma.wmb_api.entity.MRole;
import com.enigma.wmb_api.entity.MUser;
import com.enigma.wmb_api.entity.MUserAccount;
import com.enigma.wmb_api.repository.UserAccountRepository;
import com.enigma.wmb_api.service.AuthService;
import com.enigma.wmb_api.service.RoleService;
import com.enigma.wmb_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse register(AuthRequest request) throws DataIntegrityViolationException {
        MRole role = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        MUserAccount account = MUserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .roles(List.of(role))
                .isEnable(true)
                .build();
        userAccountRepository.saveAndFlush(account);

        MUser user = MUser.builder()
                .isActive(true)
                .userAccount(account)
                .build();
        userService.create(user);

        List<String> roles = account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(roles)
                .build();
    }

    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        MRole roleCustomer = roleService.getOrSave(UserRole.ROLE_CUSTOMER);
        MRole roleAdmin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        MUserAccount account = MUserAccount.builder()
                .username("admin_" + request.getUsername())
                .password(hashPassword)
                .roles(List.of(roleAdmin, roleCustomer))
                .isEnable(true)
                .build();
        userAccountRepository.saveAndFlush(account);

        MUser user = MUser.builder()
                .isActive(true)
                .userAccount(account)
                .build();
        userService.create(user);

        List<String> roles = account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(roles)
                .build();
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        return null;
    }
}
