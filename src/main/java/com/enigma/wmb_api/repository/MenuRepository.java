package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.dto.request.GetMenuRequest;
import com.enigma.wmb_api.entity.MMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MMenu, String>, JpaSpecificationExecutor<MMenu> {
}
