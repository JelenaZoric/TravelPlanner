package com.ftn.uns.travelplanerbackend.model.dto;

import com.ftn.uns.travelplanerbackend.model.Route;
import com.ftn.uns.travelplanerbackend.model.Travel;

public class Converters {
	
	public static TravelDTO convertTravelToDTO(Travel travel) {
		 TravelDTO dto = new TravelDTO();
		 dto.setAccommodation(travel.getAccommodation());
		 dto.setCurrency(travel.getCurrency());
		 dto.setDestination(travel.getDestination());
		 dto.setId(travel.getId());
		 dto.setItems(travel.getItems());
		 dto.setMode(travel.getMode());
		 dto.setOrigin(travel.getOrigin());
		 dto.setRoutes(travel.getRoutes());
		 return dto;
	}
	
	public static RouteDTO convertRouteToDTO(Route route) {
		RouteDTO dto = new RouteDTO();
		dto.setActivities(route.getActivities());
		dto.setDate(route.getDate());
		dto.setId(route.getId());
		dto.setName(route.getName());
		dto.setRouteTravel(route.getRouteTravel());
		return dto;
	}

}
