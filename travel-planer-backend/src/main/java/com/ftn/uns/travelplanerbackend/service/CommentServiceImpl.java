package com.ftn.uns.travelplanerbackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.uns.travelplanerbackend.model.Comment;
import com.ftn.uns.travelplanerbackend.repository.CommentRepository;

@Transactional
@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public Comment findOne(Long id) {
		return commentRepository.getOne(id);
	}

	@Override
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	public Comment save(Comment comment) {
		return commentRepository.save(comment);
	}

	@Override
	public Comment delete(Long id) {
		Comment comment = commentRepository.getOne(id);
		if(comment == null) {
			throw new IllegalArgumentException("Tried to delete non existing entity");
		}
		commentRepository.delete(comment);
		return comment;
	}

	@Override
	public void delete(List<Long> ids) {
		for(Long id:ids) {
			delete(id);
		}
	}

}
