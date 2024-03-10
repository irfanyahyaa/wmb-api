package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.constant.TransType;
import com.enigma.wmb_api.entity.MTransType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransTypeRepository extends JpaRepository<MTransType, TransTypeRepository> {
    Optional<MTransType> findById(TransType transType);
}