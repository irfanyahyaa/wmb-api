package com.enigma.wmb_api.repository;

import com.enigma.wmb_api.entity.MImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<MImage, String> {
}