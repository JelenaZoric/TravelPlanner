package com.ftn.uns.travelplanerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.travelplanerbackend.model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

}
