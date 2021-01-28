package servicemanager.ServiceApp.startup;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import servicemanager.ServiceApp.handler.ServiceHandler;

/**
 * Start up for starting up all services when application starts
 * 
 */
@Singleton
@Startup
public class ServiceAppStartup {

	private ServiceHandler serviceHandler;

	@Inject
	public void setServiceHandler(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
	}
	
	@PostConstruct
	public void init() {
		serviceHandler.startupAllServices();
	}
}