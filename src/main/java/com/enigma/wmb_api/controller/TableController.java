package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.TableRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.TableResponse;
import com.enigma.wmb_api.service.TableService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.TABLE_API)
public class TableController {
    public final TableService tableService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CommonResponse<TableResponse>> createNewTable(
            @RequestBody TableRequest request
    ) {
        TableResponse table = tableService.create(request);

        CommonResponse<TableResponse> response = CommonResponse.<TableResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("table created successfully")
                .data(table)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CommonResponse<List<TableResponse>>> getAllTables() {
        List<TableResponse> tableResponse = tableService.getAll();

        CommonResponse<List<TableResponse>> response = CommonResponse.<List<TableResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("tables fetched successfully")
                .data(tableResponse)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CommonResponse<TableResponse>> getTableById(
            @PathVariable(name = "id") String id
    ) {
        TableResponse tableResponse = tableService.getByIdDTO(id);

        CommonResponse<TableResponse> response = CommonResponse.<TableResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("table fetched successfully")
                .data(tableResponse)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CommonResponse<TableResponse>> updateTable(
            @RequestBody TableRequest request
    ) {
        TableResponse tableResponse = tableService.update(request);

        CommonResponse<TableResponse> response = CommonResponse.<TableResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("table updated successfully")
                .data(tableResponse)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping("/{id}")
    @SecurityRequirement(name = "Authorization")
    public ResponseEntity<CommonResponse<TableResponse>> deleteTable(
            @PathVariable(name = "id") String id
    ) {
        TableResponse tableResponse = tableService.delete(id);

        CommonResponse<TableResponse> response = CommonResponse.<TableResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("table deleted successfully")
                .data(tableResponse)
                .build();

        return ResponseEntity
                .ok(response);
    }
}