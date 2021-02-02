package servicemanager.ServiceApp.poller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Response;

import servicemanager.ServiceApp.client.ServiceClient;
import servicemanager.ServiceApp.dao.ServiceDao;
import servicemanager.ServiceApp.entities.Service;
import servicemanager.ServiceApp.entities.enums.ServiceState;

@Startup
@Singleton
public class ServicePoller {

	private ServiceDao serviceDao;
	private ServiceClient serviceClient;

	@Inject
	public void setServiceDao(ServiceDao serviceDao) {
		this.serviceDao = serviceDao;
	}

	@Inject
	public void setServiceClient(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
	}

	/**
	 * Set shedule to check all services every hour, can be upgraded to multi-thread, however here only sequential manner applied for simplicity
	 * 
	 * Not sure how often to the poller to check all the servies is proper, set it to run every quarter.
	 * 
	 * Meanwhile can use {@link Timer} & {@link TimerService} can be another alternative, if more config needed.
	 */
	@Schedule(hour = "*", minute = "*/15", persistent = false)
	public void pollServices() {
		//serviceDao.getAllServices().forEach(this::updateServiceState);
		
		int batchSize = 100; 
		List<Service> allServices = serviceDao.getAllServices();
		int rows = allServices.size()/batchSize + 1;
		IntStream.range(0, rows).mapToObj(r -> allServices.subList(r*rows, Math.min(allServices.size(), rows + rows*r)))
				.forEach(this::createNewThread);
	}
	
	/**
	 * Ofc this one need to discussed as well, depends on cpu, memory etc.
	 * @param services
	 */
	private void createNewThread(List<Service> services) {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				services.forEach(this::updateServiceState);
			}
			
			private void updateServiceState(Service service) {
				try {
					Response response = serviceClient.sendGet(service.getUrl());
					ServiceState state = getState(response);
					serviceDao.updateServieState(service.getId(), state);
				} catch (ProcessingException e) {
					serviceDao.updateServieState(service.getId(), ServiceState.FAIL);
				}
			}
		});
		t.run();
	}

	private void updateServiceState(Service service) {
		try {
			Response response = serviceClient.sendGet(service.getUrl());
			ServiceState state = getState(response);
			serviceDao.updateServieState(service.getId(), state);
		} catch (ProcessingException e) {
			serviceDao.updateServieState(service.getId(), ServiceState.FAIL);
		}
	}

	private ServiceState getState(Response response) {
		int status = response.getStatus();
		return (status / 100) == 2 ? ServiceState.OK : ServiceState.FAIL;
	}
}
