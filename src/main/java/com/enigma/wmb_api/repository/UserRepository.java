package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.MUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<MUser, String>, JpaSpecificationExecutor<MUser> {
}
