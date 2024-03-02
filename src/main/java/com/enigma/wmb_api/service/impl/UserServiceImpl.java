package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.SearchUserRequest;
import com.enigma.wmb_api.dto.request.newUserRequest;
import com.enigma.wmb_api.entity.MUser;
import com.enigma.wmb_api.repository.UserRepository;
import com.enigma.wmb_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository customerRepository;

    @Override
    public MUser create(newUserRequest request) {
        MUser user = MUser
                .builder()
                .name(request.getName())
                .mobilePhoneNumber(request.getMobilePhoneNumber())
                .isMember(request.getIsMember())
                .build();

        return customerRepository.saveAndFlush(user);
    }

    @Override
    public MUser getById(String id) {
        return null;
    }

    @Override
    public Page<MUser> getAll(SearchUserRequest request) {
        return null;
    }

    @Override
    public MUser update(MUser user) {
        return null;
    }

    @Override
    public void updateMemberById(String id, Boolean member) {

    }

    @Override
    public String delete(String id) {
        return null;
    }
}
