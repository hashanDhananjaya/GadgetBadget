package net.gadgetbadget.ws;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {

	private UserDAO dao = UserDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> list(){
		return dao.ListAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(User user) throws URISyntaxException {
		int newUserId = dao.add(user);
		URI uri = new URI("/GB/rest/users/" + newUserId);
		
		return Response.created(uri).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@FormParam("user_Type") String userType,
			          @FormParam("user_Name") String userName,
			          @FormParam("password") String password,
			          @FormParam("user_email") String email,
			          @FormParam("user_phone") String phone,
			          @FormParam("user_address") String address) throws URISyntaxException {
		
		User user = new User();
		user.setUserType(userType);
		user.setUserName(userName);
		user.setPassword(password);
		user.setEmail(email);
		user.setPhone(phone);
		user.setAddress(address);
		int newUserId = dao.add(user);
		
		return "User Registered! new id = "+newUserId;
	}
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") int id) {
		User user = dao.get(id);
		if(user != null) {
			return Response.ok(user, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, User user) {
		user.setUserId(id);
		if(dao.update(user)) {
			return Response.ok().build();
		}else {
			return Response.notModified().build();
		}
	}
	
	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") int id) {
		if(dao.delete(id)) {
			return Response.ok().build();
		}else {
			return Response.notModified().build();
		}
	}

}