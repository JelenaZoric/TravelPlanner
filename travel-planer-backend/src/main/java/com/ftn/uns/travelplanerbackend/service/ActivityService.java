package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import com.ftn.uns.travelplanerbackend.model.Activity;

public interface ActivityService {

	Activity findOne(Long id);
	List<Activity> findAll();
	Activity save(Activity activity);
	Activity delete(Long id);
	void delete(List<Long> ids);
}
