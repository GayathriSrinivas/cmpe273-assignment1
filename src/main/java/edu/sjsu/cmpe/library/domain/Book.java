package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"isbn","title", "publication-date", "language","num-pages","status","reviews","authors" })
public class Book {
	
    private int isbn;
    
    @NotEmpty
    private String title;
    private String language;
    @Pattern(regexp="available|lost|checked-out|in-queue")
    private String status ;
    private static int counter = 0;
    @NotEmpty
    @JsonProperty("publication-date")
    private String publicationDate;
    @JsonProperty("num-pages")
    private Integer numPages;
    @JsonProperty("authors")
    private ArrayList<Link> author_links;
    @JsonProperty("links")
    private ArrayList<Link> links;
    @Valid
    private ArrayList<Author> authors;
    @JsonProperty("reviews")
    private ArrayList<Link> review_links;
    private ArrayList<Review> reviews;
  
      
    public Book() {
    	authors = new ArrayList<Author>();
    	reviews = new ArrayList<Review>();
    	author_links = new ArrayList<Link>();
    	links = new ArrayList<Link>();
    	review_links = new ArrayList<Link>();
    	status = "available";
    }
    
    public ArrayList<Link> getReviews() {
    	return review_links;
    }
    
    public Review fetchReview(int i) {
    	if(i < reviews.size())
    	return reviews.get(i);
    	else
    	return null; 
    }
    
    public ArrayList<Review> fetchAllReviews() {
    	return reviews;
    }

    public int addBookReview(Review review) {
    	if (reviews.size() == 0) {
    		links.add(new Link("view-all-reviews","/books/"+isbn+"/reviews","GET"));
    	}
    	review.setId(reviews.size() + 1);
    	reviews.add(review);
    	review_links.add(new Link("view-review", "/books/" + isbn + "/reviews/" + reviews.size(), "GET"));
    	return reviews.size();
    }
    
    public Author fetchAuthor(int i) {
    	if(i < authors.size())
    	return authors.get(i);
    	else
    	return null;
    }
    
    public ArrayList<Author> fetchAllAuthors() {
    	return authors;
    }
    
    public void setAuthors(Author[] authors){
    	for (int i = 0; i < authors.length; i++) {
    		authors[i].setId(i + 1);
			this.authors.add(authors[i]);
		}
    }
    
    public void setReviews(Review[] reviews) {
    	return;
    }
    
    public void generateLinks() {
    	links.add(new Link("view-book","/books/"+isbn,"GET"));
    	links.add(new Link("update-book","/books/"+isbn,"PUT"));
    	links.add(new Link("delete-book","/books/"+isbn,"DELETE"));
    	links.add(new Link("create-book","/books/"+isbn,"POST"));
    	for (int i = 0; i < authors.size(); i++) {
    		Author author = authors.get(i);
    		author_links.add(new Link("view-author", "/books/" + isbn + "/authors/" + author.getid(), "GET"));
    	}
    }
    
    public ArrayList<Link> getAuthors() {
    	return author_links;
    }
    
    public ArrayList<Link> getLinks() {
    	return links;
    }
    
    public static int getNextIsbn(){
    	return ++counter;
    }
    
    public int getIsbn() {
	return isbn;
    }

    public void setIsbn(int isbn) {
    	this.isbn = isbn;
    }
  
    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }
    
    public String getLanguage() {
	return language;
    }

   
    public void setLanguage(String language) {
	this.language = language;
    }

    public String getPublicationDate() {
	return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
	this.publicationDate = publicationDate;
    }
    
    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
    	this.status = status;
    }
    
    public Integer getNumPages() {
	return numPages;
    }

    public void setNumPages(Integer numPages) {
	this.numPages = numPages;
    }
    
    public boolean isValidStatus(String status){
    	
    	if(status.equals("available") || status.equals("lost") ||status.equals("checked-out")||status.equals("in-queue"))
    		return true;
    	else
    		return false;
    }
}
