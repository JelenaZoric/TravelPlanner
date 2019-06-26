package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import com.ftn.uns.travelplanerbackend.model.Comment;

public interface CommentService {

	Comment findOne(Long id);
	List<Comment> findAll();
	Comment save(Comment comment);
	Comment delete(Long id);
	void delete(List<Long> ids);
}
