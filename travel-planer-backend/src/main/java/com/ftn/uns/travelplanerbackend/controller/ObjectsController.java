package com.ftn.uns.travelplanerbackend.controller;

import com.ftn.uns.travelplanerbackend.model.Comment;
import com.ftn.uns.travelplanerbackend.model.Object;
import com.ftn.uns.travelplanerbackend.service.CommentService;
import com.ftn.uns.travelplanerbackend.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ObjectsController {

    @Autowired
    private ObjectService objectService;

    @Autowired
    private CommentService commentService;

    @GetMapping(value = "/objects", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getObjects(@RequestParam("location") String location, @RequestParam("type") String type) {
        return objectService.getObjectsByLocationAndType(location, type);
    }

    @GetMapping(value = "/objects/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Object> getAllObjects() {
        return objectService.findAll();
    }

    @GetMapping(value = "/comments/{objectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Comment> getComments(@PathVariable("objectId") Long objectId) {
        return objectService.findOne(objectId).getComments();
    }

    @PostMapping(value = "/comments/{objectId}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment addComment(@PathVariable("objectId") Long objectId, @RequestBody Comment comment) {
        Comment dbComment = commentService.save(comment);
        Object object = objectService.findOne(objectId);
        object.getComments().add(dbComment);

        objectService.save(object);

        return dbComment;
    }
}
