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

@Path("/product")
public class ProductResource {

	private ProductDAO dao = ProductDAO.getInstance();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> list(){
		return dao.ListAll();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response add(Product product) throws URISyntaxException {
		int newProduct_Id = dao.add(product);
		URI uri = new URI("/GB/rest/product/" + newProduct_Id);
		
		return Response.created(uri).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String add(@FormParam("product_Name") String product_Name,
			          @FormParam("product_Description") String product_Description,
			          @FormParam("manufactured_date") String manufactured_date,
			          @FormParam("price") float price) throws URISyntaxException {
			          
		Product product = new Product();
		product.setProduct_Name(product_Name);
		product.setProduct_Description(product_Description);
		product.setManufactured_date(manufactured_date);
		product.setPrice(price);
		
		int newProductId = dao.add(product);
		
		
		return "Item added! new id = "+newProductId;
	}
	
	
	@GET
	@Path("{id}")
	public Response get(@PathParam("id") int id) {
		Product product = dao.get(id);
		if(product != null) {
			return Response.ok(product, MediaType.APPLICATION_JSON).build();
		}else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
	}
	
	@PUT
	@Consumes (MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response update(@PathParam("id") int id, Product product) {
		product.setProduct_Id(id);
		if(dao.update(product)) {
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
