package com.ftn.uns.travelplanerbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ftn.uns.travelplanerbackend.model.ActivityType;
import com.ftn.uns.travelplanerbackend.model.Location;
import com.ftn.uns.travelplanerbackend.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.uns.travelplanerbackend.model.Object;
import com.ftn.uns.travelplanerbackend.repository.ObjectRepository;

@Transactional
@Service
public class ObjectServiceImpl implements ObjectService {

	@Autowired
	private ObjectRepository objectRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Override
	public Object findOne(Long id) {
		return objectRepository.getOne(id);
	}

	@Override
	public List<Object> findAll() {
		return objectRepository.findAll();
	}

	@Override
	public Object save(Object object) {
		return objectRepository.save(object);
	}

	@Override
	public Object delete(Long id) {
		Object object = objectRepository.getOne(id);
		if(object == null) {
			throw new IllegalArgumentException("Tried to delete non existing entity");
		}
		objectRepository.delete(object);
		return object;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids) {
			this.delete(id);
		}
	}

	@Override
	public List<Object> getObjectsByLocationAndType(String locationString, String type) {
		String[] locationParts = locationString.split(",");

		Optional<Location> location = locationRepository.findByCityAndCountry(locationParts[0], locationParts[1]);

		if(!location.isPresent()) {
			return new ArrayList<>();
		}

		ActivityType activityType = ActivityType.valueOf(type);

		return objectRepository.findByTypeAndLocation(activityType, location.get());
	}
}
