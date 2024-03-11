package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.GetUserRequest;
import com.enigma.wmb_api.dto.request.UserRequest;
import com.enigma.wmb_api.dto.response.UserResponse;
import com.enigma.wmb_api.entity.MUser;
import org.springframework.data.domain.Page;

public interface UserService {
    MUser create(MUser user);

    Page<UserResponse> getAll(GetUserRequest request);

    MUser getByIdEntity(String id);

    UserResponse getByIdDTO(String id);

    UserResponse update(UserRequest request);

    UserResponse updateMemberById(String id, Boolean isActive);

    UserResponse delete(String id);
}