package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import com.ftn.uns.travelplanerbackend.model.Object;

public interface ObjectService {

	Object findOne(Long id);
	List<Object> findAll();
	Object save(Object object);
	Object delete(Long id);
	void delete(List<Long> ids);
}
