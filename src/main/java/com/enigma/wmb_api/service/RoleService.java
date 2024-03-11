package com.enigma.wmb_api.service;

import com.enigma.wmb_api.constant.UserRole;
import com.enigma.wmb_api.entity.MRole;

public interface RoleService {
    MRole getOrSave(UserRole role);
}
