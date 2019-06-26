package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.uns.travelplanerbackend.model.Location;
import com.ftn.uns.travelplanerbackend.repository.LocationRepository;

@Transactional
@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public Location findOne(Long id) {
		return locationRepository.getOne(id);
	}

	@Override
	public List<Location> findAll() {
		return locationRepository.findAll();
	}

	@Override
	public Location save(Location location) {
		return locationRepository.save(location);
	}

	@Override
	public Location delete(Long id) {
		Location location = locationRepository.getOne(id);
		if(location == null) {
			throw new IllegalArgumentException("Tried to delete non existing entity");
		}
		locationRepository.delete(location);
		return location;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids) {
			this.delete(id);
		}
	}
}
