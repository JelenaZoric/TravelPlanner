package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import com.ftn.uns.travelplanerbackend.model.Travel;

public interface TravelService {

	Travel findOne(Long id);
	List<Travel> findAll();
	Travel save(Travel travel);
	Travel delete(Long id);
	void delete(List<Long> ids);
}
