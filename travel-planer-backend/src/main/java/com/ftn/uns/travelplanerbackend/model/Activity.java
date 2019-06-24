package com.ftn.uns.travelplanerbackend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Activity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4860389777899947140L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private ActivityType type;
    private Object object;
    private Date time;
    
    public Activity() {}
    
	public Activity(ActivityType type, Object object, Date time) {
		super();
		this.type = type;
		this.object = object;
		this.time = time;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ActivityType getType() {
		return type;
	}

	public void setType(ActivityType type) {
		this.type = type;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
