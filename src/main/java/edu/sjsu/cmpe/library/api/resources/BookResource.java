package edu.sjsu.cmpe.library.api.resources;

import edu.sjsu.cmpe.library.domain.*;
import edu.sjsu.cmpe.library.dto.*;

import java.util.ArrayList;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


//import org.codehaus.jackson.map.SerializationConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Optional;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class BookResource {
	
	@POST
	public Response createBook(@Valid Book book){
		int isbn = Book.getNextIsbn();
		book.setIsbn(isbn);
		book.generateLinks();
		DataStore.books.put(isbn, book);
		return Response.status(201).entity(new Links(book.getLinks())).build();
	}
	
	@GET
    @Path("/{isbn}")
    public Response viewBook(@PathParam("isbn") int isbn) {
			if(DataStore.books.get(isbn) != null)
        return Response.status(200)
        			   .entity(DataStore.books.get(isbn))
        			   .build();
		else
			return Response.status(404).build();
	}
	
	@DELETE
	@Path("/{isbn}")
	public Response deleteBook(@PathParam("isbn") int isbn){
		if(DataStore.books.get(isbn) == null)
			return Response.status(404).entity("ERROR :404 Book with isbn :"+isbn+" not found").build();
		
		DataStore.books.remove(isbn);
		Links links = new Links();
		links.add(new Link("create-book","/books","POST"));
		return Response.status(200).entity(links).build();
	}
	
	@PUT
	@Path("/{isbn}")
	public Response updateBook(
			@PathParam("isbn") int isbn,
			@QueryParam("status") String status) {
		
		Book book = DataStore.books.get(isbn);
		if(book.isValidStatus(status)){
			book.setStatus(status);
			return Response.status(200).entity(new Links(book.getLinks())).build();
		}
		return Response.status(422).entity("Error : 422 , Status can only take"
				+ " <available|lost|checked-out|in-queue> values").build();
	}
	
	
	
	@POST
	@Path("/{isbn}/reviews")
	public Response createReview(@PathParam("isbn") int isbn,@Valid Review review){
		
		if (!DataStore.books.containsKey(isbn))
			return Response.status(404).entity("ERROR : 404 BOOK with isbn :"+isbn+"not found").build();
		int id = DataStore.books.get(isbn).addBookReview(review);
		//Response Body
		Links links = new Links();		
		links.add(new Link("view-reviews","/books/"+isbn+"/reviews/"+id,"GET"));
		return Response.status(201).entity(links).build();
	}
	
	@GET
    @Path("/{isbn}/reviews/{id}")
	public Response viewABookReview(
			@PathParam("isbn") int isbn,
			@PathParam("id") int id){
		
		int key = isbn;
		
		if(DataStore.books.get(key) == null)
			return Response.status(404).entity("ERROR :404 BOOK with isbn :"+isbn+"not found").build();
		
		Book book = DataStore.books.get(key);
		
		if(book.fetchReview(id - 1) == null)
			return Response.status(404).entity("ERROR :404 BOOK with isbn :"+isbn+" has no review id:"+id).build();
			
			
		
		ArrayList<Link> links = new ArrayList<Link>();
		links.add(new Link("view-review", "/books/" + key + "/reviews/" + id, "GET"));
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		JsonNode node1 = mapper.convertValue(book.fetchReview(id - 1), JsonNode.class);
		JsonNode node2 = mapper.convertValue(links, JsonNode.class);
		node.put("review", node1);
		node.put("links", node2);
        return Response.status(200).entity(node).build();
	}
	
	@GET
    @Path("/{isbn}/reviews")
	public Response viewAllBookReview(@PathParam("isbn") int isbn){
		Book book = DataStore.books.get(isbn);
		
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        JsonNode node1 = mapper.convertValue(book.fetchAllReviews(), JsonNode.class);
        JsonNode node2 = mapper.convertValue(new ArrayList<String>(), JsonNode.class);
        node.put("reviews", node1);
        node.put("links", node2);
        return Response.status(200).entity(node).build();
	}

	@GET
    @Path("/{isbn}/authors/{id}")
	public Response viewAnAuthor(
			@PathParam("isbn") int isbn,
			@PathParam("id") int id){
		
		if(DataStore.books.get(isbn) == null)
			return Response.status(404).entity("ERROR :404 BOOK with isbn :"+isbn+" not found").build();
		
		Book book = DataStore.books.get(isbn);
		
		if(book.fetchAuthor(id - 1) == null)
			return Response.status(404).entity("ERROR :404 BOOK with isbn :"+isbn+" has no Author id:"+id).build();
			
		ArrayList<Link> links = new ArrayList<Link>();
		links.add(new Link("view-author", "/books/" + isbn + "/authors/" + id, "GET"));
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		JsonNode node1 = mapper.convertValue(book.fetchAuthor(id - 1), JsonNode.class);
		JsonNode node2 = mapper.convertValue(links, JsonNode.class);
		node.put("author", node1);
		node.put("links", node2);
        return Response.status(200).entity(node).build();
	}
	 
	@GET
    @Path("/{isbn}/authors/")
	public Response viewAllAuthors(@PathParam("isbn") int isbn){
		Book book = DataStore.books.get(isbn);
		ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        JsonNode node1 = mapper.convertValue(book.fetchAllAuthors(), JsonNode.class);
        JsonNode node2 = mapper.convertValue(new ArrayList<String>(), JsonNode.class);
        node.put("authors", node1);
        node.put("links", node2);
        return Response.status(200).entity(node).build();
	}
}
