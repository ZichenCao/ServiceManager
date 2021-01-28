
package servicemanager.ServiceApp.rest;

import java.net.URI;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import servicemanager.ServiceApp.handler.ServiceHandler;
import servicemanager.ServiceApp.rest.model.ServiceRequestModel;
import servicemanager.ServiceApp.rest.model.ServiceResponseModel;

/**
 * The rest layer for handling all the requests
 * 
 * NOTE: All exception handling can be revised with consideration of more flows
 *
 */
@Path(value = "/service")
public class ServiceRest {

	private ServiceHandler serviceHandler;

	@Context
	private SecurityContext securityContext;

	@Inject
	public void setServiceHandler(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
	}
	
	/**
	 * Loading all services for a specific user
	 * 
	 * @return A list of services
	 */
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllServices() {
		String user = getUser();
		List<ServiceResponseModel> responseModels = serviceHandler.getServicesByUser(user);
		return Response.status(Status.OK).entity(responseModels).build();
	}
	
	/**
	 * Get service by name
	 * @param name
	 * @return Service with name
	 */
	@GET
	@Path("/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getByName(@PathParam("name") String name) {
		String user = getUser();
		try {
			ServiceResponseModel responseModel = serviceHandler.getServiceByName(name, user);
			return Response.status(Status.OK).entity(responseModel).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("Not able to find service").build();			
		}
	}

	/**
	 * Create service based on data from {@link ServiceRequestModel}
	 * @param requestMode 
	 * @return 
	 */
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createService(ServiceRequestModel requestModel) {
		String user = getUser();
		try {
			serviceHandler.createService(requestModel, user);
			URI uri = new URI(String.format("/serviceapp/service/%s", requestModel.getName()));
			return Response.created(uri).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("Fail to create service").build();			
		}
	}
	
	/**
	 * Update service based on data from {@link ServiceRequestModel}
	 * @param requestMode 
	 * @return 
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateService(@PathParam("id") long id, ServiceRequestModel requestMode) {
		String user = getUser();
		try {
			serviceHandler.updateService(id, requestMode, user);
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("Fail to update service").build();			
		}
	}
	
	/**
	 * Delete service by its id
	 * @param id
	 * @return 
	 */
	@DELETE
	@Path("/{id}")
	public Response getById(@PathParam("id") long id) {
		String user = getUser();
		try {
			serviceHandler.deleteServiceById(id, user);
			return Response.status(Status.OK).build();			
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity("Fail to delete service").build();			
		}
	}
	
	private String getUser() {
		/* 
		 * I assume user name can be got from the SecurityContext simply
		 * Which ofc can be extracted from the token in a more secured manner 
		 */
		String user = securityContext.getUserPrincipal().getName();
		return user;
	}
	
}
