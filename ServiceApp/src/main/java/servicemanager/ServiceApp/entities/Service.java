package servicemanager.ServiceApp.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import servicemanager.ServiceApp.entities.enums.ServiceState;

/**
 * Service entity class representing the data structure of corresponding db objects
 */
@Entity
@Table(name = "Service")
@NamedQueries(value = {
		@NamedQuery(name = Service.FIND_ALL, query = "SELECT s FROM Service s"),
		@NamedQuery(name = Service.FIND_BY_USER, query = "SELECT s FROM Service s WHERE createdBy = :user"),
		@NamedQuery(name = Service.FIND_BY_NAME_AND_USER, query = "SELECT s FROM Service s WHERE name = :name AND createdBy = :user"),
})
public class Service {

	public static final String FIND_ALL = "Service.FIND_ALL";
	public static final String FIND_BY_USER = "Service.FIND_BY_USER";
	public static final String FIND_BY_NAME_AND_USER = "Service.FIND_BY_NAME_AND_USER";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name =  "Name", length = 50, nullable = false)
	private String name;

	@Column(name =  "URL", length = 200, nullable = false)
	private String url;

	@Column(name =  "Servie_State", length = 10, nullable = false)
	@Enumerated(EnumType.STRING)
	private ServiceState serviceState = ServiceState.LIVE;

	@Column(name =  "Created_By", length = 50, nullable = false)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name =  "Create_Date", nullable = false)
	private Date createDate = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name =  "Udpate_Date")
	private Date updateDate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public ServiceState getServiceState() {
		return serviceState;
	}

	public void setServiceState(ServiceState serviceState) {
		this.serviceState = serviceState;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
