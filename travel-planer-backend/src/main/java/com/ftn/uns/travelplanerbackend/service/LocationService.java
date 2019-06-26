package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import com.ftn.uns.travelplanerbackend.model.Location;

public interface LocationService {

	Location findOne(Long id);
	List<Location> findAll();
	Location save(Location location);
	Location delete(Long id);
	void delete(List<Long> ids);
}
