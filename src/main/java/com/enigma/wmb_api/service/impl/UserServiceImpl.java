package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.SearchUserRequest;
import com.enigma.wmb_api.dto.request.newUserRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.entity.MUser;
import com.enigma.wmb_api.repository.UserRepository;
import com.enigma.wmb_api.service.UserService;
import com.enigma.wmb_api.specification.UserSpecification;
import com.enigma.wmb_api.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    @Override
    public MUser create(newUserRequest request) {
        validationUtil.validate(request);

        MUser user = MUser
                .builder()
                .name(request.getName())
                .mobilePhoneNo(request.getMobilePhoneNo())
                .isMember(request.getIsMember())
                .build();

        return userRepository.saveAndFlush(user);
    }

    @Override
    public Page<MUser> getAll(SearchUserRequest request) {
        // validate DTO
        validationUtil.validate(request);
        // validate sortBy
        validationUtil.validateSortFields(MUser.class, request.getSortBy());

        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());

        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        Specification<MUser> specification = UserSpecification.getSpecification(request);

        Page<MUser> userPage = userRepository.findAll(specification, pageable);
        // validate empty data
        validationUtil.validateEmptyData(userPage, "Users");

        return userPage;
    }

    @Override
    public MUser getById(String id) {
        return findByIdOrNotFound(id);
    }

    @Override
    public MUser update(MUser user) {
        findByIdOrNotFound(user.getId());

        return userRepository.saveAndFlush(user);
    }

    @Override
    public void updateMemberById(String id, Boolean isMember) {
        findByIdOrNotFound(id);

        userRepository.updateMember(id, isMember);
    }

    @Override
    public void delete(String id) {
        findByIdOrNotFound(id);

        userRepository.deleteById(id);
    }

    // coba dibuat util
    private MUser findByIdOrNotFound(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
}
