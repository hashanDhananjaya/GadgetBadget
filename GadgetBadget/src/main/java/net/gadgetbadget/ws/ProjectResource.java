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

@Path("/projects")
public class ProjectResource {

	private ProjectDAO dao = ProjectDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> list(){
		return dao.ListAll();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Project project) throws URISyntaxException {
		int newProjectId = dao.add(project);
		URI uri = new URI("/GB/rest/products/" + newProjectId);
		
		return Response.created(uri).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@FormParam("proj_Name") String proj_Name,
			          @FormParam("proj_Description") String proj_Description,
			          @FormParam("proj_Duration") int proj_Duration,
			          @FormParam("proj_Budget") float proj_Budget,
			          @FormParam("user_Id") int user_Id) throws URISyntaxException {
		Project project = new Project();
		project.setProj_Name(proj_Name);
		project.setProj_Description(proj_Description);
		project.setProj_Duration(proj_Duration);
		project.setProj_Budget(proj_Budget);
		project.setUser_Id(user_Id);
		int newProjectId = dao.add(project);
		
		return "Item added! new id = "+newProjectId;
	}
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") int id) {
		Project project = dao.get(id);
		if(project != null) {
			return Response.ok(project, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Project project) {
		project.setProj_Id(id);
		if(dao.update(project)) {
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
