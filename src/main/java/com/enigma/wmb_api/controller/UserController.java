package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.SearchUserRequest;
import com.enigma.wmb_api.dto.request.newUserRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.entity.MUser;
import com.enigma.wmb_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.USER)
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<CommonResponse<MUser>> createNewUser(
            @RequestBody newUserRequest request
    ) {
        MUser user = userService.create(request);

        // coba dibuat util
        CommonResponse<MUser> response = CommonResponse.<MUser>builder()
                .statuscode(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(user)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MUser>>> getAllUser(
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

        Page<MUser> users = userService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .page(users.getPageable().getPageNumber() + 1)
                .size(users.getPageable().getPageSize())
                .hasNext(users.hasNext())
                .hasPrevious(users.hasPrevious())
                .build();

        // coba dibuat validationUtil
        if (page > users.getTotalPages()) {
            CommonResponse<List<MUser>> errorResponse = CommonResponse.<List<MUser>>builder()
                    .statuscode(HttpStatus.BAD_REQUEST.value())
                    .message("Page number exceeds total pages available")
                    .paging(pagingResponse)
                    .build();

            return ResponseEntity.badRequest().body(errorResponse);
        }

        CommonResponse<List<MUser>> commonResponse = CommonResponse.<List<MUser>>builder()
                .statuscode(HttpStatus.OK.value())
                .message("User fetched successfully")
                .data(users.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<MUser>> getUserById(
            @PathVariable("id") String id
    ) {
        MUser user = userService.getById(id);

        CommonResponse<MUser> commonResponse = CommonResponse.<MUser>builder()
                .statuscode(HttpStatus.OK.value())
                .message("User fetched successfully")
                .data(user)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MUser>> updateUser(
            @RequestBody MUser payload
    ) {
        MUser user = userService.update(payload);

        CommonResponse<MUser> commonResponse = CommonResponse.<MUser>builder()
                .statuscode(HttpStatus.OK.value())
                .message("User updated successfully")
                .data(user)
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> updateMemberUser(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "is_member") Boolean isMember
    ) {
        userService.updateMemberById(id, isMember);

        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .statuscode(HttpStatus.OK.value())
                .message("User member updated successfully")
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteUser(
            @PathVariable(name = "id") String id
    ) {
        userService.delete(id);

        CommonResponse<String> commonResponse = CommonResponse.<String>builder()
                .statuscode(HttpStatus.OK.value())
                .message("User deleted successfully")
                .build();

        return ResponseEntity
                .ok(commonResponse);
    }
}
