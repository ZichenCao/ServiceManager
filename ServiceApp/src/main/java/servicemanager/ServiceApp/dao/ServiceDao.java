package servicemanager.ServiceApp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import servicemanager.ServiceApp.entities.Service;

/**
 * 
 * Database access layer for all CRUD activities
 */
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ServiceDao {

	@PersistenceContext
	private EntityManager em;

	public Service getById(long id) {
		return em.find(Service.class, id);
	}

	public List<Service> getAllServices() {
		TypedQuery<Service> query = em.createNamedQuery(Service.FIND_ALL, Service.class);
		List<Service> resultList = query.getResultList();
		return resultList != null ? resultList : new ArrayList<Service>();
	}

	public List<Service> findServicesByUser(String user) {
		TypedQuery<Service> query = em.createNamedQuery(Service.FIND_BY_USER, Service.class);
		query.setParameter("user", user);
		List<Service> resultList = query.getResultList();
		return resultList != null ? resultList : new ArrayList<Service>();
	}

	public Service findServiceByName(String name, String user) {
		TypedQuery<Service> query = em.createNamedQuery(Service.FIND_BY_NAME_AND_USER, Service.class);
		query.setParameter("name", name);
		query.setParameter("user", user);
		return query.getSingleResult();
	}

	public void insert(Service service) {
		em.persist(service);
	}

	public void update(Service service) {
		em.merge(service);
	}

	public void delete(long id) {
		Service service = getById(id);
		em.remove(service);
	}
}
