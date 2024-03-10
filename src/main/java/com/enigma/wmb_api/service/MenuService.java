package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.GetMenuRequest;
import com.enigma.wmb_api.dto.request.MenuRequest;
import com.enigma.wmb_api.dto.response.MenuResponse;
import com.enigma.wmb_api.entity.MMenu;
import org.springframework.data.domain.Page;

public interface MenuService {
    MenuResponse create(MenuRequest request);

    Page<MenuResponse> getAll(GetMenuRequest request);

    MMenu getByIdEntity(String id);

    MenuResponse getByIdDTO(String id);

    MenuResponse update(MenuRequest request);

    MenuResponse delete(String id);
}