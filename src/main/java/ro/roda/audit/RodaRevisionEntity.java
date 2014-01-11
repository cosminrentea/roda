package ro.roda.audit;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import org.apache.solr.client.solrj.SolrServer;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
@SuppressWarnings("serial")
@RevisionEntity(RodaRevisionListener.class)
@Table(schema = "audit", name = "revinfo")
public class RodaRevisionEntity extends DefaultRevisionEntity {

	private String username;

	public String getUsername() {
		return username;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired(required = false)
	transient SolrServer solrServer;

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public static final EntityManager entityManager() {
		EntityManager em = new RodaRevisionEntity().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<RodaRevisionEntity> findAllRodaRevisionEntities() {
		return entityManager().createQuery("SELECT o FROM RodaRevisionEntity o", RodaRevisionEntity.class)
				.getResultList();
	}

	public static RodaRevisionEntity findRodaRevisionEntity(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(RodaRevisionEntity.class, id);
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

}
