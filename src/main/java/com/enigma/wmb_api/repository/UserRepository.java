package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.MUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<MUser, String>, JpaSpecificationExecutor<MUser> {
    @Modifying
    @Query(value = "UPDATE m_user SET is_member = :isMember WHERE id = :id", nativeQuery = true)
    void updateMember(@Param("id") String id, @Param("isMember") Boolean isMember);
}