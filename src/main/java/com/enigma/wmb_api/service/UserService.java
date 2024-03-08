package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.GetUserRequest;
import com.enigma.wmb_api.dto.request.UserRequest;
import com.enigma.wmb_api.dto.response.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService  {
    UserResponse create(UserRequest request);
    Page<UserResponse> getAll(GetUserRequest request);
    UserResponse getById(String id);
    UserResponse update(UserRequest request);
    UserResponse updateMemberById(String id, Boolean isMember);
    UserResponse delete(String id);
}
