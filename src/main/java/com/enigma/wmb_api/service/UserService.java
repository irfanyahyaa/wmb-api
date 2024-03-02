package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.SearchUserRequest;
import com.enigma.wmb_api.dto.request.newUserRequest;
import com.enigma.wmb_api.entity.MUser;
import org.springframework.data.domain.Page;

public interface UserService  {
    MUser create(newUserRequest request);
    Page<MUser> getAll(SearchUserRequest request);
    MUser getById(String id);
    MUser update(MUser user);
    void updateMemberById(String id, Boolean member);
    String delete(String id);
}
