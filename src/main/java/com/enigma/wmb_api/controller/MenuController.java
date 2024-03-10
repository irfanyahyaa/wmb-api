package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.request.GetMenuRequest;
import com.enigma.wmb_api.dto.request.MenuRequest;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.dto.response.PagingResponse;
import com.enigma.wmb_api.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.MENU_API)
public class MenuController {
    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<CommonResponse<MenuResponse>> createNewMenu(
            @RequestBody MenuRequest request
    ) {
        MenuResponse menu = menuService.create(request);

        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statuscode(HttpStatus.CREATED.value())
                .message("menu created successfully")
                .data(menu)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MenuResponse>>> getAllMenus(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "menu") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "menu", required = false) String menu,
            @RequestParam(name = "minPrice", required = false) Long minPrice,
            @RequestParam(name = "maxPrice", required = false) Long maxPrice
    ) {
        GetMenuRequest request = GetMenuRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .menu(menu)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        Page<MenuResponse> menus = menuService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(menus.getTotalPages())
                .totalElements(menus.getTotalElements())
                .page(menus.getPageable().getPageNumber() + 1)
                .size(menus.getPageable().getPageSize())
                .hasNext(menus.hasNext())
                .hasPrevious(menus.hasPrevious())
                .build();

        CommonResponse<List<MenuResponse>> response = CommonResponse.<List<MenuResponse>>builder()
                .statuscode(HttpStatus.OK.value())
                .message("menus fetched successfully")
                .data(menus.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<MenuResponse>> getMenuById(
            @PathVariable(name = "id") String id
    ) {
        MenuResponse menu = menuService.getByIdDTO(id);

        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statuscode(HttpStatus.OK.value())
                .message("menu fetched successfully")
                .data(menu)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MenuResponse>> updateMenu(
            @RequestBody MenuRequest request
    ) {
        MenuResponse menu = menuService.update(request);

        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statuscode(HttpStatus.OK.value())
                .message("menu updated successfully")
                .data(menu)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<MenuResponse>> deleteMenu(
            @PathVariable(name = "id") String id
    ) {
        MenuResponse menu = menuService.delete(id);

        CommonResponse<MenuResponse> response = CommonResponse.<MenuResponse>builder()
                .statuscode(HttpStatus.OK.value())
                .message("menu deleted successfully")
                .data(menu)
                .build();

        return ResponseEntity.ok(response);
    }
}