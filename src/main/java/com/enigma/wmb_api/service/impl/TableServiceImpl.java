package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.dto.request.TableRequest;
import com.enigma.wmb_api.dto.response.TableResponse;
import com.enigma.wmb_api.entity.MTable;
import com.enigma.wmb_api.repository.TableRepository;
import com.enigma.wmb_api.service.TableService;
import com.enigma.wmb_api.util.ValidationUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableRepository tableRepository;
    private final ValidationUtil validationUtil;

    @Transactional(rollbackFor = Exception.class)
    @PostConstruct
    public void initTakeAway() {
        Optional<MTable> takeAway = tableRepository.findByName("Take Away");
        if (takeAway.isPresent()) return;

        MTable noTable = MTable.builder()
                .name("Take Away")
                .build();

        tableRepository.save(noTable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TableResponse create(TableRequest request) {
        validationUtil.validate(request);

        MTable table = MTable.builder()
                .name(request.getName())
                .build();

        tableRepository.saveAndFlush(table);

        return TableResponse.builder()
                .id(table.getId())
                .name(table.getName())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TableResponse> getAll() {
        List<MTable> tableList = tableRepository.findAll();

        return tableList.stream().map(table -> TableResponse.builder()
                .id(table.getId())
                .name(table.getName())
                .build()).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public MTable getByNameEntity(String name) {
        return tableRepository.findByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "table not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public MTable getByIdEntity(String id) {
        return findByIdOrNotFound(id);
    }

    @Transactional(readOnly = true)
    @Override
    public TableResponse getByIdDTO(String id) {
        MTable table = findByIdOrNotFound(id);

        return TableResponse.builder()
                .id(table.getId())
                .name(table.getName())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TableResponse update(TableRequest request) {
        validationUtil.validate(request);
        findByIdOrNotFound(request.getId());

        MTable table = MTable.builder()
                .id(request.getId())
                .name(request.getName())
                .build();
        tableRepository.saveAndFlush(table);

        return TableResponse.builder()
                .id(table.getId())
                .name(table.getName())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TableResponse delete(String id) {
        MTable table = findByIdOrNotFound(id);
        tableRepository.delete(table);

        return TableResponse.builder()
                .id(table.getId())
                .name(table.getName())
                .build();
    }

    private MTable findByIdOrNotFound(String id) {
        return tableRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "table not found"));
    }
}
