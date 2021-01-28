package servicemanager.ServiceApp.util;

import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import servicemanager.ServiceApp.entities.Service;
import servicemanager.ServiceApp.rest.model.ServiceRequestModel;
import servicemanager.ServiceApp.rest.model.ServiceResponseModel;

/**
 * Util class handling data/model conversion and validation
 *
 */
public class ServiceUtil {

	//Simple regex pattern, only for specific dummy data purpose
	private static final String URL_PATTERN = ".*/service/[\\d].*";

	/**
	 * Create {@link ServiceResponseModel} based on {@link Service}
	 * 
	 * @param service
	 * @return
	 */
	public static ServiceResponseModel convertServiceToResponseModel(Service service) {
		if (service == null) 
			throw new IllegalArgumentException("No service to convert");
		
		ServiceResponseModel responseModel = new ServiceResponseModel();
		responseModel.setId(service.getId());
		responseModel.setName(service.getName());
		responseModel.setUrl(service.getUrl());
		responseModel.setServiceState(service.getServiceState().toString());
		responseModel.setCreatedBy(service.getCreatedBy());
		responseModel.setCreateDate(service.getCreateDate());
		responseModel.setUpdateDate(service.getUpdateDate());
		return responseModel;
	}

	/**
	 * Create {@link Service} based on {@link ServiceRequestModel}
	 * 
	 * @param requestModel
	 * @param user
	 * @return
	 */
	public static Service convertServiceRequsetModelToService(ServiceRequestModel requestModel, String user) {
		validRequestModel(requestModel);

		Service service = new Service();
		service.setCreatedBy(user);
		service.setName(requestModel.getName());
		service.setUrl(requestModel.getUrl());
		return service;
	}

	/**
	 * Update {@link Service} based on {@link ServiceRequestModel}
	 * 
	 * @param service
	 * @param requestMode
	 */
	public static void updateServiceByRequestModel(Service service, ServiceRequestModel requestModel) {
		validRequestModel(requestModel);
		if (service.getName().equalsIgnoreCase(requestModel.getName())) 
			throw new IllegalArgumentException(
					String.format("Not able to find the servie with name: %s", requestModel.getName()));

		service.setUrl(requestModel.getUrl());
		service.setUpdateDate(new Date());
	}
	
	/**
	 * Validate the user on {@link Service}
	 * 
	 * @param service
	 * @param user
	 */
	public static void validUser(Service service, String user) {
		if (service != null && !service.getCreatedBy().equalsIgnoreCase(user)) 
			throw new IllegalArgumentException(String.format("User: %s is not the creator of service: %s", user, service.getName()));
	}

	/**
	 * Validate the {@link ServiceRequestModel}
	 * Currently it's only called internally, can be set to private as well, considering more use cases
	 * 
	 * @param requestModel
	 */
	public static void validRequestModel(ServiceRequestModel requestModel) {
		if (requestModel == null) 
			throw new IllegalArgumentException("ServiceRequestModel shouldn't be null");
		if (StringUtils.isBlank(requestModel.getName())) 
			throw new IllegalArgumentException("ServiceRequestModel is missing name");
		if (StringUtils.isBlank(requestModel.getUrl())) 
			throw new IllegalArgumentException("ServiceRequestModel is missing url");
		validURL(requestModel.getUrl());
	}

	/**
	 * Validate the url 
	 * 
	 * @param url
	 */
	public static void validURL(String url) {
		if (!Pattern.matches(URL_PATTERN, url)) 
			throw new IllegalArgumentException("URL is following standard pattern");
	}

}
