package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.TransType;
import com.enigma.wmb_api.entity.MTransType;
import com.enigma.wmb_api.repository.TransTypeRepository;
import com.enigma.wmb_api.service.TransTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.beans.Transient;

@Service
@RequiredArgsConstructor
public class TransTypeServiceImpl implements TransTypeService {
    private final TransTypeRepository transTypeRepository;

    @Transactional(readOnly = true)
    @Override
    public MTransType getByIdEntity(String id) {
        MTransType transType;

        if (id.equalsIgnoreCase(TransType.DI.toString())) {
            transType = getOrSave(TransType.DI);
        } else if (id.equalsIgnoreCase(TransType.TA.toString())) {
            transType = getOrSave(TransType.TA);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid transaction type");
        }

        return transType;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public MTransType getOrSave(TransType transType) {
        MTransType getTransType = transTypeRepository.findById(transType)
                .orElseGet(() -> MTransType.builder().id(transType).build());

        if (getTransType.getId().equals(TransType.DI) && getTransType.getDescription() == null) {
            getTransType.setDescription("Dine In");
        } else if (getTransType.getId().equals(TransType.TA) && getTransType.getDescription() == null) {
            getTransType.setDescription("Take Away");
        }
        transTypeRepository.saveAndFlush(getTransType);

        return getTransType;
    }
}