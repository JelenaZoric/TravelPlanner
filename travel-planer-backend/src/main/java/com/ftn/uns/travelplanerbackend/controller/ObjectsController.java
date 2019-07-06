package com.ftn.uns.travelplanerbackend.controller;

import com.ftn.uns.travelplanerbackend.model.Activity;
import com.ftn.uns.travelplanerbackend.model.Comment;
import com.ftn.uns.travelplanerbackend.model.Object;
import com.ftn.uns.travelplanerbackend.model.User;
import com.ftn.uns.travelplanerbackend.model.dto.Converters;
import com.ftn.uns.travelplanerbackend.model.dto.ObjectDTO;
import com.ftn.uns.travelplanerbackend.service.ActivityService;
import com.ftn.uns.travelplanerbackend.service.CommentService;
import com.ftn.uns.travelplanerbackend.service.ObjectService;
import com.ftn.uns.travelplanerbackend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ObjectsController {

    @Autowired
    private ObjectService objectService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ActivityService activityService;
    @Autowired
	private UserService userService;

    @GetMapping(value = "/objects", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getObjects(@RequestParam("location") String location, @RequestParam("type") String type) {
        return objectService.getObjectsByLocationAndType(location, type);
    }

    @GetMapping(value = "/objects/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getAllObjects() {
        return objectService.findAll();
    }
    
    @GetMapping(value = "/objects/{activity_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectDTO getObject(@PathVariable Long activity_id) {
    	Activity activity = activityService.findOne(activity_id);
    	Object object = activity.getObject();
    	ObjectDTO dto = Converters.convertObjectToDTO(object);
    	return dto;
    }

    @GetMapping(value = "/comments/{objectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comment> getComments(@PathVariable("objectId") Long objectId) {
        return objectService.findOne(objectId).getComments();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/comments/{objectId}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment addComment(@PathVariable("objectId") Long objectId, @RequestBody Comment comment, Authentication authentication) {
        User user = userService.findOne(Long.valueOf(authentication.getName()));
        comment.setUser(user);
    	Comment dbComment = commentService.save(comment);
        Object object = objectService.findOne(objectId);
        object.getComments().add(dbComment);

        objectService.save(object);

        return dbComment;
    }
}
