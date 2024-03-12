package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.MUserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<MUserAccount, String> {
    Optional<MUserAccount> findByUsername(String string);
}