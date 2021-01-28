package servicemanager.ServiceApp.util;

import org.junit.Test;

/**
 * Simple tests for validating urls
 *
 */
public class ServiceUtilTest {

	@Test
	public void testValidUrl() {
		final String url = "localhost:8080/service/1";
		ServiceUtil.validURL(url);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidUrl() {
		final String url = "localhost:8080/service/sdgf";
		ServiceUtil.validURL(url);
	}
	
}
