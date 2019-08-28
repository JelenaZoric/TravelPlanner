package com.ftn.uns.travelplanerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.travelplanerbackend.model.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findFirstByCityAndCountry(String city, String country);
}
