package uk.ac.cam.cl.kilo.backend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test")
public class RESTEasyTest {
	
	@GET
	public Response simpleResponse() {
		
		String responseString = "Welcome to the Project Kilo WebApp";
		
		return Response.status(200).entity(responseString).build();
	}
}