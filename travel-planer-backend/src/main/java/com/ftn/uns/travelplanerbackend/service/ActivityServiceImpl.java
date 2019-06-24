package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.uns.travelplanerbackend.model.Activity;
import com.ftn.uns.travelplanerbackend.repository.ActivityRepository;

@Transactional
@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityRepository activityRepository;
	
	@Override
	public Activity findOne(Long id) {
		return activityRepository.getOne(id);
	}

	@Override
	public List<Activity> findAll() {
		return activityRepository.findAll();
	}

	@Override
	public Activity save(Activity activity) {
		return activityRepository.save(activity);
	}

	@Override
	public Activity delete(Long id) {
		Activity activity = activityRepository.getOne(id);
		if(activity == null) {
			throw new IllegalArgumentException("Tried to delete non existing entity");
		}
		activityRepository.delete(activity);
		return activity;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids) {
			delete(id);
		}
	}

}
