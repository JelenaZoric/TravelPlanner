package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import com.ftn.uns.travelplanerbackend.model.Route;

public interface RouteService {

	Route findOne(Long id);
	List<Route> findAll();
	Route save(Route route);
	Route delete(Long id);
	void delete(List<Long> ids);
}
