package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.UserRole;
import com.enigma.wmb_api.entity.MRole;
import com.enigma.wmb_api.repository.RoleRepository;
import com.enigma.wmb_api.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public MRole getOrSave(UserRole role) {
        return roleRepository.findByRole(role)
                .orElseGet(() -> roleRepository.saveAndFlush(MRole.builder().role(role).build()));
    }
}
