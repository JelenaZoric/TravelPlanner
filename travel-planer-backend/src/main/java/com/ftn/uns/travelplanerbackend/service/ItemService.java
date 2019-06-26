package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import com.ftn.uns.travelplanerbackend.model.Item;

public interface ItemService {

	Item findOne(Long id);
	List<Item> findAll();
	Item save(Item item);
	Item delete(Long id);
	void delete(List<Long> ids);
}
