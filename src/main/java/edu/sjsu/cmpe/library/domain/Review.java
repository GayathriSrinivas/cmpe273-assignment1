package edu.sjsu.cmpe.library.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id","rating","comment"})
public class Review {
	
	@Max(5)
	@Min(1)
	private int rating;
	@NotEmpty
	private String comment;
	private int id;
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getid(){
		return this.id;
	}
	public void setRating(int rating){
		this.rating = rating;
	}
	
	public int getRating(){
		return this.rating;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public String getComment(){
		return this.comment;
	}
	
}
