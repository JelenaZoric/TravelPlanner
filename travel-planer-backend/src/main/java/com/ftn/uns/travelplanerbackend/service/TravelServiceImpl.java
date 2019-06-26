package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

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
