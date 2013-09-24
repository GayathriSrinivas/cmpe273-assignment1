package edu.sjsu.cmpe.library.api.resources;

import edu.sjsu.cmpe.library.domain.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RootResource {
	
    @GET
    public Response viewLinks(){
    	Links links =new Links();
    	links.add(new Link("create-books","/books","POST"));
    	return Response.ok(links).build();
    }
    
    
}