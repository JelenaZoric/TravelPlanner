package com.ftn.uns.travelplanerbackend.repository;

import com.ftn.uns.travelplanerbackend.model.ActivityType;
import com.ftn.uns.travelplanerbackend.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.travelplanerbackend.model.Object;

import java.util.List;

@Repository
public interface ObjectRepository extends JpaRepository<Object, Long> {

    List<Object> findByTypeAndLocation(ActivityType type, Location location);
}
