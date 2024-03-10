package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.TableRequest;
import com.enigma.wmb_api.dto.response.TableResponse;
import com.enigma.wmb_api.entity.MTable;

import java.util.List;

public interface TableService {
    TableResponse create(TableRequest request);

    List<TableResponse> getAll();

    MTable getByIdEntity(String id);

    TableResponse getByIdDTO(String id);

    TableResponse update(TableRequest request);

    TableResponse delete(String id);
}