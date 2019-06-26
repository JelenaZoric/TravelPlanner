package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.uns.travelplanerbackend.model.Transportation;
import com.ftn.uns.travelplanerbackend.repository.TransportationRepository;

@Transactional
@Service
public class TransportationServiceImpl implements TransportationService {

	@Autowired
	private TransportationRepository transportationRepository;
	
	@Override
	public Transportation findOne(Long id) {
		return transportationRepository.getOne(id);
	}

	@Override
	public List<Transportation> findAll() {
		return transportationRepository.findAll();
	}

	@Override
	public Transportation save(Transportation transportation) {
		return transportationRepository.save(transportation);
	}

	@Override
	public Transportation delete(Long id) {
		Transportation transportation = transportationRepository.getOne(id);
		if(transportation == null) {
			throw new IllegalArgumentException("Tried to delete non existing entity");
		}
		transportationRepository.delete(transportation);
		return transportation;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids) {
			this.delete(id);
		}
	}

}
