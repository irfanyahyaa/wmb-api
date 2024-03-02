package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.SearchUserRequest;
import com.enigma.wmb_api.dto.request.newUserRequest;
import com.enigma.wmb_api.entity.MUser;
import org.springframework.data.domain.Page;

public interface UserService  {
    MUser create(newUserRequest request);
    MUser getById(String id);
    Page<MUser> getAll(SearchUserRequest request);
    MUser update(MUser user);
    void updateMemberById(String id, Boolean member);
    String delete(String id);
}
