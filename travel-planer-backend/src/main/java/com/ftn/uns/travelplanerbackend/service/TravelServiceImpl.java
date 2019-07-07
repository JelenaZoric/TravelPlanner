package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import com.ftn.uns.travelplanerbackend.repository.LocationRepository;
import com.ftn.uns.travelplanerbackend.repository.ObjectRepository;
import com.ftn.uns.travelplanerbackend.repository.TransportationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.uns.travelplanerbackend.model.Travel;
import com.ftn.uns.travelplanerbackend.repository.TravelRepository;

@Transactional
@Service
public class TravelServiceImpl implements TravelService {

	@Autowired
	private TravelRepository travelRepository;

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private LocationService locationService;

	@Autowired
	private TransportationRepository transportationRepository;
	@Autowired
	private ObjectRepository objectRepository;
	
	@Override
	public Travel findOne(Long id) {
		return travelRepository.getOne(id);
	}

	@Override
	public List<Travel> findAll() {
		return travelRepository.findAll();
	}

	@Override
	public Travel save(Travel travel) {
		return travelRepository.save(travel);
	}

	@Override
	public Travel delete(Long id) {
		Travel travel = travelRepository.getOne(id);
		if(travel == null) {
			throw new IllegalArgumentException("Tried to delete non existing entity");
		}
		transportationRepository.delete(travel.getOrigin());
		transportationRepository.delete(travel.getDestination());
		objectRepository.delete(travel.getAccommodation());
		travelRepository.delete(travel);
		return travel;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids) {
			this.delete(id);
		}
	}
}
