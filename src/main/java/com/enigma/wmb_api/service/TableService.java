package com.enigma.wmb_api.service;

import com.enigma.wmb_api.dto.request.TableRequest;
import com.enigma.wmb_api.dto.response.TableResponse;

import java.util.List;

public interface TableService {
    TableResponse create(TableRequest request);
    List<TableResponse> getAll();
    TableResponse getById(String id);
    TableResponse update(TableRequest request);
    TableResponse delete(String id);
}