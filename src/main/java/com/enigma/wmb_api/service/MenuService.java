package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.GetMenuRequest;
import com.enigma.wmb_api.dto.request.MenuRequest;
import com.enigma.wmb_api.dto.response.MenuResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MenuService {
    MenuResponse create(MenuRequest request);
    Page<MenuResponse> getAll(GetMenuRequest request);
    MenuResponse getById(String id);
    MenuResponse update(MenuRequest request);
    MenuResponse delete(String id);
}