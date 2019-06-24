package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import com.ftn.uns.travelplanerbackend.model.Transportation;

public interface TransportationService {

	Transportation findOne(Long id);
	List<Transportation> findAll();
	Transportation save(Transportation transportation);
	Transportation delete(Long id);
	void delete(List<Long> ids);
}
