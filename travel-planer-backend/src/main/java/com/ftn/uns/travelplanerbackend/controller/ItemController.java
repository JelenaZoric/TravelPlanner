package com.ftn.uns.travelplanerbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.uns.travelplanerbackend.model.Item;
import com.ftn.uns.travelplanerbackend.model.Travel;
import com.ftn.uns.travelplanerbackend.service.ItemService;
import com.ftn.uns.travelplanerbackend.service.TravelService;

@RestController
@RequestMapping(value="items")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private TravelService travelService;
	
	@RequestMapping
	public ResponseEntity<List<Item>> getAllItems() {
		System.out.println("usao je u getItems na back-u");
		List<Item> items = itemService.findAll();
		System.out.println("nasao je " + items.size() + "itema sa getItems na backu");
		return new ResponseEntity<List<Item>>(items, HttpStatus.OK);
	}
	
	@RequestMapping(value="{id}")
	public ResponseEntity<Item> getItem(@PathVariable("id") Long id) {
		Item item = itemService.findOne(id);
		return new ResponseEntity<Item>(item, HttpStatus.OK);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes="application/json", value="{travel_id}")
	public ResponseEntity<Item> addItem(@RequestBody Item item, @PathVariable("travel_id") Long travel_id) {
		Item newItem = new Item();
		newItem.setName(item.getName());
		newItem.setBrought(item.isBrought());
		Travel travel = travelService.findOne(travel_id);
		travel.getItems().add(newItem);
		newItem = itemService.save(item);
		System.out.println("the new item is: " + item.getName());
		return new ResponseEntity<Item>(newItem, HttpStatus.CREATED);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/{id}")
	public ResponseEntity<Item> deleteItem(@PathVariable("id") Long id) {
		Item deletedItem = itemService.delete(id);
		return new ResponseEntity<Item>(deletedItem, HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/{id}", consumes="application/json", produces="application/json")
	public ResponseEntity<Item> editItem(@PathVariable("id") Long id, @RequestBody Item item) {
		Item editedItem = itemService.findOne(id);
		editedItem.setName(item.getName());
		editedItem.setBrought(item.isBrought());
		if(item.isBrought()){
			System.out.println("item " + item.getName() + "is brought");
		}
		else{
			System.out.println("item " + item.getName() + "is not brought");
		}
		itemService.save(editedItem);
		return new ResponseEntity<Item>(editedItem, HttpStatus.OK);
	}
}
