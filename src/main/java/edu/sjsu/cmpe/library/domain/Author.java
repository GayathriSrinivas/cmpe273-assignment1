package edu.sjsu.cmpe.library.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class Author {
	
	private int id;
	@NotEmpty
	public String name;

	public void setId(int id){
		this.id = id;
	}
	
	public int getid(){
		return this.id;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
}
