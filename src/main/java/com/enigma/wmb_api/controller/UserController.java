package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.GetUserRequest;
import com.enigma.wmb_api.dto.request.UserRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.dto.response.UserResponse;
import com.enigma.wmb_api.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.USER_API)
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CommonResponse<List<UserResponse>>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "mobilePhoneNo", required = false) String mobilePhoneNo,
            @RequestParam(name = "isActive", required = false) Boolean isActive
    ) {
        GetUserRequest request = GetUserRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .name(name)
                .mobilePhoneNo(mobilePhoneNo)
                .isActive(isActive)
                .build();

        Page<UserResponse> users = userService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .page(users.getPageable().getPageNumber() + 1)
                .size(users.getPageable().getPageSize())
                .hasNext(users.hasNext())
                .hasPrevious(users.hasPrevious())
                .build();

        CommonResponse<List<UserResponse>> commonResponse = CommonResponse.<List<UserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("users fetched successfully")
                .data(users.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN') OR @securityService.checkUserLoggedInById(#id)")
    @GetMapping("/{id}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(
            @PathVariable("id") String id
    ) {
        UserResponse user = userService.getByIdDTO(id);

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("user fetched successfully")
                .data(user)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN') OR @securityService.checkUserLoggedInByDTO(#request)")
    @PutMapping
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CommonResponse<UserResponse>> updateUser(
            @RequestBody UserRequest request
    ) {
        UserResponse user = userService.update(request);

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("user updated successfully")
                .data(user)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping("/{id}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CommonResponse<UserResponse>> updateMemberUser(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "is_active") Boolean isActive
    ) {
        UserResponse user = userService.updateMemberById(id, isActive);

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("user member updated successfully")
                .data(user)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CommonResponse<UserResponse>> deleteUser(
            @PathVariable(name = "id") String id
    ) {
        UserResponse user = userService.delete(id);

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("user deleted successfully")
                .data(user)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }
}