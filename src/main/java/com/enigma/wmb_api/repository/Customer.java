package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.MCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Customer extends JpaRepository<MCustomer, String> {
}
