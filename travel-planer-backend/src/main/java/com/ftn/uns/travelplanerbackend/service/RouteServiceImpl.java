package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.uns.travelplanerbackend.model.Route;
import com.ftn.uns.travelplanerbackend.repository.RouteRepository;

@Transactional
@Service
public class RouteServiceImpl implements RouteService {

	@Autowired
	private RouteRepository routeRepository;

	@Override
	public Route findOne(Long id) {
		return routeRepository.getOne(id);
	}

	@Override
	public List<Route> findAll() {
		return routeRepository.findAll();
	}

	@Override
	public Route save(Route route) {
		return routeRepository.save(route);
	}

	@Override
	public Route delete(Long id) {
		Route route = routeRepository.getOne(id);
		if(route == null) {
			throw new IllegalArgumentException("Tried to delete non existing entity");
		}
		routeRepository.delete(route);
		return route;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids) {
			this.delete(id);
		}
	}
}
