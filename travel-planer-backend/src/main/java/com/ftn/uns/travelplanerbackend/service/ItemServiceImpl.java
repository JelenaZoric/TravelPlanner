package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.uns.travelplanerbackend.model.Item;
import com.ftn.uns.travelplanerbackend.repository.ItemRepository;

@Transactional
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	@Override
	public Item findOne(Long id) {
		return itemRepository.getOne(id);
	}

	@Override
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	@Override
	public Item save(Item item) {
		return itemRepository.save(item);
	}

	@Override
	public Item delete(Long id) {
		Item item = itemRepository.getOne(id);
		if(item == null) {
			throw new IllegalArgumentException("Tried to delete non existing entity");
		}
		itemRepository.delete(item);
		return item;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids) {
			delete(id);
		}
	}
}
