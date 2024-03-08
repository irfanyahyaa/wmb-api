package com.enigma.wmb_api.service;

import com.enigma.wmb_api.constant.TransType;
import com.enigma.wmb_api.entity.MTransType;

import java.util.List;

public interface TransTypeService {
    MTransType getOrSave(TransType transType);
}