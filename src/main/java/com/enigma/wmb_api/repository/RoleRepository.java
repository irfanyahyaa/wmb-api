package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.constant.UserRole;
import com.enigma.wmb_api.entity.MRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<MRole, String> {
    Optional<MRole> findByRole(UserRole role);
}
