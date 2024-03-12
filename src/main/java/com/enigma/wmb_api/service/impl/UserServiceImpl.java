package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.GetUserRequest;
import com.enigma.wmb_api.dto.request.UserRequest;
import com.enigma.wmb_api.dto.response.UserResponse;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MUser create(MUser user) {
        return userRepository.saveAndFlush(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserResponse> getAll(GetUserRequest request) {
        validationUtil.validate(request);
        validationUtil.validateSortFields(MUser.class, request.getSortBy());

        if (request.getPage() <= 0) request.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());

        Pageable pageable = PageRequest.of((request.getPage() - 1), request.getSize(), sort);
        Specification<MUser> specification = UserSpecification.getSpecification(request);

        Page<MUser> userPage = userRepository.findAll(specification, pageable);

        validationUtil.validateEmptyData(userPage, "users");

        return userPage.map(user -> UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .mobilePhoneNo(user.getMobilePhoneNo())
                .isActive(user.getIsActive())
                .build());
    }

    @Transactional(readOnly = true)
    @Override
    public MUser getByIdEntity(String id) {
        return findByIdOrNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getByIdDTO(String id) {
        MUser user = findByIdOrNotFound(id);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .mobilePhoneNo(user.getMobilePhoneNo())
                .isActive(user.getIsActive())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse update(UserRequest request) {
        validationUtil.validate(request);
        MUser userFound = findByIdOrNotFound(request.getId());

        MUser user = MUser.builder()
                .id(request.getId())
                .name(request.getName())
                .mobilePhoneNo(request.getMobilePhoneNo())
                .isActive(userFound.getIsActive())
                .userAccount(userFound.getUserAccount())
                .build();
        userRepository.saveAndFlush(user);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .mobilePhoneNo(user.getMobilePhoneNo())
                .isActive(user.getIsActive())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse updateMemberById(String id, Boolean isActive) {
        MUser user = findByIdOrNotFound(id);
        user.setIsActive(isActive);
        userRepository.updateMember(id, isActive);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .mobilePhoneNo(user.getMobilePhoneNo())
                .isActive(user.getIsActive())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserResponse delete(String id) {
        MUser user = findByIdOrNotFound(id);
        userRepository.deleteById(id);

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .mobilePhoneNo(user.getMobilePhoneNo())
                .isActive(user.getIsActive())
                .build();
    }

    private MUser findByIdOrNotFound(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
    }
}
