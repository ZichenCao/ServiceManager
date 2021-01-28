package servicemanager.ServiceApp.rest.model;

/**
 * The request model for create or update service
 */
public class ServiceRequestModel {
	
	private String name;

	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
