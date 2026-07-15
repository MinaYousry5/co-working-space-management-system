package com.workspace.booking.repository;

import com.workspace.booking.entity.feature.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}
