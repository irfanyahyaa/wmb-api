package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.MTransType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransType extends JpaRepository<MTransType, TransType> {
}
