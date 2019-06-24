package com.ftn.uns.travelplanerbackend.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7880272008163378980L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
    private boolean brought;
    
    public Item() {}

	public Item(String name, boolean brought) {
		super();
		this.name = name;
		this.brought = brought;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isBrought() {
		return brought;
	}

	public void setBrought(boolean brought) {
		this.brought = brought;
	}
}
