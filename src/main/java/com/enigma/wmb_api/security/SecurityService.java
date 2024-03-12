package com.enigma.wmb_api.security;

import com.enigma.wmb_api.dto.request.UserRequest;
import com.enigma.wmb_api.entity.MUser;
import com.enigma.wmb_api.entity.MUserAccount;
import com.enigma.wmb_api.service.UserAccountService;
import com.enigma.wmb_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service("securityService")
@RequiredArgsConstructor
public class SecurityService {
    private final UserService userService;
    private final UserAccountService userAccountService;
    public Boolean checkUserLoggedInByDTO(UserRequest request) {
        MUser currentCustomer = userService.getByIdEntity(String.valueOf(request.getId()));
        MUserAccount userAccount = userAccountService.getByContext();

        if (!userAccount.getId().equals(currentCustomer.getUserAccount().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "cannot access this resource");
        }

        return true;
    }

    public Boolean checkUserLoggedInById(String id) {
        MUser currentCustomer = userService.getByIdEntity(id);
        MUserAccount userAccount = userAccountService.getByContext();

        if (!userAccount.getId().equals(currentCustomer.getUserAccount().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "cannot access this resource");
        }

        return true;
    }
}
