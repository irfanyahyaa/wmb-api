package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.newUserRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.entity.MUser;
import com.enigma.wmb_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.USER)
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonResponse<MUser>> create(
            @RequestBody newUserRequest request
    ) {
        MUser user = userService.create(request);

        CommonResponse<MUser> response = CommonResponse.<MUser>builder()
                .statuscode(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(user)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
