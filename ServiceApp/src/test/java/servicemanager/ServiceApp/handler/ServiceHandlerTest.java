package servicemanager.ServiceApp.handler;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import servicemanager.ServiceApp.dao.ServiceDao;

/**
 * Only one simple test case, which only show the testability by using Mockito.
 *  
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceHandlerTest {

	@InjectMocks
	ServiceHandler serviceHandler;
	
	@Mock
	ServiceDao serviceDao;
	
	@Test
	public void testStartupAllServices() {
		serviceHandler.startupAllServices();
		verify(serviceDao, times(1)).getAllServices();
	}
}
