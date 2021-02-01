package servicemanager.ServiceApp.client;

import java.util.concurrent.TimeUnit;

import javax.ejb.LocalBean;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.Response;

@LocalBean
public class ServiceClient {

	public Response sendGet(String url) {
		Client client = getClient();
		ClientRequestFilter authFilter = getAuthenticationFilter();
		Response response = client.target(url).register(authFilter).request().get();
		return response;
	}

	private Client getClient() {
		return ClientBuilder.newBuilder().connectTimeout(100, TimeUnit.MILLISECONDS).readTimeout(100, TimeUnit.SECONDS).build();
	}

	private ClientRequestFilter getAuthenticationFilter() {
		// TODO Add necessary authentication properties, need to create a separated implementation of ClientRequestFilter, which is omitted here
		return null;
	}
}
