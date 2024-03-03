package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.SearchUserRequest;
import com.enigma.wmb_api.dto.request.UserRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.dto.response.UserResponse;
import com.enigma.wmb_api.entity.MUser;
import com.enigma.wmb_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.USER)
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonResponse<UserResponse>> createNewUser(
            @RequestBody UserRequest request
    ) {
        UserResponse user = userService.create(request);

        // coba dibuat util
        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statuscode(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(user)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<UserResponse>>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "mobilePhoneNo", required = false) String mobilePhoneNo,
            @RequestParam(name = "isMember", required = false) Boolean isMember
    ) {
        SearchUserRequest request = SearchUserRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .name(name)
                .mobilePhoneNo(mobilePhoneNo)
                .isMember(isMember)
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
                .statuscode(HttpStatus.OK.value())
                .message("User fetched successfully")
                .data(users.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(
            @PathVariable("id") String id
    ) {
        UserResponse user = userService.getById(id);

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .statuscode(HttpStatus.OK.value())
                .message("User fetched successfully")
                .data(user)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<UserResponse>> updateUser(
            @RequestBody UserRequest request
    ) {
        UserResponse user = userService.update(request);

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .statuscode(HttpStatus.OK.value())
                .message("User updated successfully")
                .data(user)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> updateMemberUser(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "is_member") Boolean isMember
    ) {
        UserResponse user = userService.updateMemberById(id, isMember);

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .statuscode(HttpStatus.OK.value())
                .message("User member updated successfully")
                .data(user)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> deleteUser(
            @PathVariable(name = "id") String id
    ) {
        UserResponse user = userService.delete(id);

        CommonResponse<UserResponse> commonResponse = CommonResponse.<UserResponse>builder()
                .statuscode(HttpStatus.OK.value())
                .message("User deleted successfully")
                .data(user)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }
}
