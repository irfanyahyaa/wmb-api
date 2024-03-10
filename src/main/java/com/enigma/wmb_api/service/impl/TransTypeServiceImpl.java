package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.constant.TransType;
import com.enigma.wmb_api.entity.MTransType;
import com.enigma.wmb_api.repository.TransTypeRepository;
import com.enigma.wmb_api.service.TransTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransTypeServiceImpl implements TransTypeService {
    private final TransTypeRepository transTypeRepository;

    @Override
    public MTransType getByIdEntity(String id) {
        MTransType transType = null;

        if (id.equalsIgnoreCase(TransType.DI.toString())) {
            transType = getOrSave(TransType.DI);
        } else if (id.equalsIgnoreCase(TransType.TA.toString())) {
            transType = getOrSave(TransType.TA);
        }

        return transType;
    }

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