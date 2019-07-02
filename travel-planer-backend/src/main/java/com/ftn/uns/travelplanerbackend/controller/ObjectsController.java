package com.ftn.uns.travelplanerbackend.controller;

import com.ftn.uns.travelplanerbackend.model.Object;
import com.ftn.uns.travelplanerbackend.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ObjectsController {

    @Autowired
    private ObjectService objectService;

    @GetMapping(value = "/objects", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getObjects(@RequestParam("location") String location, @RequestParam("type") String type) {
        return objectService.getObjectsByLocationAndType(location, type);
    }

    @GetMapping(value = "/objects/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getAllObjects() {
        return objectService.findAll();
    }
}
