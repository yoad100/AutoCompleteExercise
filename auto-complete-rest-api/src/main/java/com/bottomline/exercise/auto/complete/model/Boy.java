package com.bottomline.exercise.auto.complete.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Boy {
	private String name;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	public Boy(String name) {
		this.name = name;
	}
	
	public Boy() {
		this.name = "";
	}


	public String getName() {
		return this.name;
	}
}
