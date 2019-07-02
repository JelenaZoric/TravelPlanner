package com.ftn.uns.travelplanerbackend.service;

import java.util.List;
import java.util.Optional;

import com.ftn.uns.travelplanerbackend.model.Location;
import com.ftn.uns.travelplanerbackend.model.Transportation;
import com.ftn.uns.travelplanerbackend.repository.LocationRepository;
import com.ftn.uns.travelplanerbackend.repository.TransportationRepository;
import com.ftn.uns.travelplanerbackend.utils.GoogleCoordinatesService;
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
	private TransportationRepository transportationRepository;
	
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
		Transportation origin = travel.getOrigin();
		Transportation destination = travel.getDestination();

		origin.setLocation(getProperLocation(origin));
		destination.setLocation(getProperLocation(destination));

		return travelRepository.save(travel);
	}

	@Override
	public Travel delete(Long id) {
		Travel travel = travelRepository.getOne(id);
		if(travel == null) {
			throw new IllegalArgumentException("Tried to delete non existing entity");
		}
		travelRepository.delete(travel);
		return travel;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids) {
			this.delete(id);
		}
	}

	Location getProperLocation(Transportation transportation) {
		Location transportationLocation = transportation.getLocation();
		Optional<Location> optionalTransportationLocation = locationRepository.findByCityAndCountry(transportationLocation.getCity(), transportationLocation.getCountry());
		Location persistentTransportationLocation;

		if (optionalTransportationLocation.isPresent()) {
			persistentTransportationLocation = optionalTransportationLocation .get();
		}
		else {
			GoogleCoordinatesService googleCoordinatesService = new GoogleCoordinatesService();
			persistentTransportationLocation = locationRepository.save(googleCoordinatesService.getCoordinatesFromAddress(transportationLocation.getCity(), transportationLocation.getCountry()));
		}
		return persistentTransportationLocation;
	}
}
