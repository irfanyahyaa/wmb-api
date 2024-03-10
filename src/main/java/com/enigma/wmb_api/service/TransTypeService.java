package com.enigma.wmb_api.service;

import com.enigma.wmb_api.constant.TransType;
import com.enigma.wmb_api.entity.MTransType;

public interface TransTypeService {
    MTransType getByIdEntity(String id);

    MTransType getOrSave(TransType transType);
}