package com.enigma.wmb_api.controller;

import com.enigma.wmb_api.constant.APIUrl;
import com.enigma.wmb_api.dto.response.CommonResponse;
import com.enigma.wmb_api.entity.MTable;
import com.enigma.wmb_api.entity.MUser;
import com.enigma.wmb_api.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.TABLE)
public class TableController {
    public TableService tableService;

    @PostMapping
    public ResponseEntity<CommonResponse<MTable>> createNewTable(){
        return null;
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<MTable>>> getAllTables(){
        return null;
    }

    @GetMapping("{id}")
    public ResponseEntity<CommonResponse<MTable>> getTableById() {
        return null;
    }

    @PutMapping
    public ResponseEntity<CommonResponse<MTable>> updateTable() {
        return null;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<CommonResponse<MTable>> deleteTable() {
        return null;
    }
}
