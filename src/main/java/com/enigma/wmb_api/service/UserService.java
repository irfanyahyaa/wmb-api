package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.SearchUserRequest;
import com.enigma.wmb_api.dto.request.UserRequest;
import com.enigma.wmb_api.dto.response.UserResponse;
import com.enigma.wmb_api.entity.MUser;
import org.springframework.data.domain.Page;

public interface UserService  {
    UserResponse create(UserRequest request);
    Page<UserResponse> getAll(SearchUserRequest request);
    UserResponse getById(String id);
    UserResponse update(UserRequest request);
    UserResponse updateMemberById(String id, Boolean isMember);
    UserResponse delete(String id);
}
