package com.example.travelplanner;

public class RouteMapper {

    public Route mapFromDto(RouteDto dto) {
        Route route = new Route();

        route.setId(dto.getId());
        route.setDate(dto.getDate());
        //route.setTravel(dto.getTravelId());

        return route;
    }

    public RouteDto mapToDto(Route route) {
        RouteDto dto = new RouteDto();

        dto.setId(route.getId());
        dto.setDate(route.getDate());
        //dto.setTravelId(route.getTravel()/*.getId()*/);

        return dto;
    }
}
