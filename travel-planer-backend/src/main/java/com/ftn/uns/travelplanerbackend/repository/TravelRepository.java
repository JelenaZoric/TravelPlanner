package com.ftn.uns.travelplanerbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.uns.travelplanerbackend.model.Travel;

@Repository
public interface TravelRepository extends JpaRepository<Travel, Long> {

}
