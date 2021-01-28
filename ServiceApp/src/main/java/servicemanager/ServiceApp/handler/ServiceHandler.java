package servicemanager.ServiceApp.handler;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import servicemanager.ServiceApp.dao.ServiceDao;
import servicemanager.ServiceApp.entities.Service;
import servicemanager.ServiceApp.rest.model.ServiceRequestModel;
import servicemanager.ServiceApp.rest.model.ServiceResponseModel;
import servicemanager.ServiceApp.util.ServiceUtil;

/**
 * The business layer for handling all service related actions.
 * 
 * NOTE: My personal preference would name it ABCService, however it's a bit
 * conflicting with the entity naming, therefore used handler instead.
 */
@Singleton
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class ServiceHandler {

	private ServiceDao serviceDao;

	@Inject
	public void setServiceDao(ServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}

	public void startupAllServices() {
		List<Service> services = serviceDao.getAllServices();
		services.forEach(this::startupService);
	}

	private void startupService(Service service) {
		// TODO Start up service
	}

	public List<ServiceResponseModel> getServicesByUser(String user) {
		List<Service> services = serviceDao.findServicesByUser(user);

		return services.stream().map(ServiceUtil::convertServiceToResponseModel).collect(Collectors.toList());
	}

	public ServiceResponseModel getServiceByName(String name, String user) {
		Service service = serviceDao.findServiceByName(name, user);
		return ServiceUtil.convertServiceToResponseModel(service);
	}

	public void createService(ServiceRequestModel requestModel, String user) {
		Service service = ServiceUtil.convertServiceRequsetModelToService(requestModel, user);
		serviceDao.insert(service);
	}

	public void updateService(long id, ServiceRequestModel requestModel, String user) {
		Service service = serviceDao.getById(id);
		if (service == null) 
			throw new IllegalArgumentException(String.format("Not able to find the servie with id: %s", id));
		
		ServiceUtil.validUser(service, user);
		ServiceUtil.updateServiceByRequestModel(service, requestModel);
		serviceDao.update(service);
	}

	public void deleteServiceById(long id, String user) {
		Service service = serviceDao.getById(id);
		if (service == null) 
			throw new IllegalArgumentException(String.format("Not able to find the servie with id: %d", id));
		
		ServiceUtil.validUser(service, user);
		serviceDao.delete(id);
	}

}
