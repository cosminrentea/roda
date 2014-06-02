package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "instance_form")
@Configurable
@Audited
public class InstanceForm {

	public static long countInstanceForms() {
		return entityManager().createQuery("SELECT COUNT(o) FROM InstanceForm o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(InstanceForm instanceForm) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("instanceform_" + instanceForm.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new InstanceForm().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<InstanceForm> findAllInstanceForms() {
		return entityManager().createQuery("SELECT o FROM InstanceForm o", InstanceForm.class).getResultList();
	}

	public static InstanceForm findInstanceForm(InstanceFormPK id) {
		if (id == null)
			return null;
		return entityManager().find(InstanceForm.class, id);
	}

	public static List<InstanceForm> findInstanceFormEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM InstanceForm o", InstanceForm.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<InstanceForm> fromJsonArrayToInstanceForms(String json) {
		return new JSONDeserializer<List<InstanceForm>>().use(null, ArrayList.class).use("values", InstanceForm.class)
				.deserialize(json);
	}

	public static InstanceForm fromJsonToInstanceForm(String json) {
		return new JSONDeserializer<InstanceForm>().use(null, InstanceForm.class).deserialize(json);
	}

	public static void indexInstanceForm(InstanceForm instanceForm) {
		List<InstanceForm> instanceforms = new ArrayList<InstanceForm>();
		instanceforms.add(instanceForm);
		indexInstanceForms(instanceforms);
	}

	@Async
	public static void indexInstanceForms(Collection<InstanceForm> instanceforms) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (InstanceForm instanceForm : instanceforms) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "instanceform_" + instanceForm.getId());
			sid.addField("instanceForm.formid_t", instanceForm.getFormId());
			sid.addField("instanceForm.instanceid_t", instanceForm.getInstanceId());
			sid.addField("instanceForm.orderformininstance_i", instanceForm.getOrderFormInInstance());
			sid.addField("instanceForm.id_t", instanceForm.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("instanceform_solrsummary_t", new StringBuilder().append(instanceForm.getFormId()).append(" ")
					.append(instanceForm.getInstanceId()).append(" ").append(instanceForm.getOrderFormInInstance())
					.append(" ").append(instanceForm.getId()));
			documents.add(sid);
		}
		try {
			SolrServer solrServer = solrServer();
			solrServer.add(documents);
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static QueryResponse search(String queryString) {
		String searchString = "InstanceForm_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new InstanceForm().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<InstanceForm> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@ManyToOne
	@JoinColumn(name = "form_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Form formId;

	@EmbeddedId
	private InstanceFormPK id;

	@ManyToOne
	@JoinColumn(name = "instance_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Instance instanceId;

	@Column(name = "order_form_in_instance", columnDefinition = "int4", unique = true)
	@NotNull
	private Integer orderFormInInstance;

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

	public Form getFormId() {
		return formId;
	}

	public InstanceFormPK getId() {
		return this.id;
	}

	public Instance getInstanceId() {
		return instanceId;
	}

	public Integer getOrderFormInInstance() {
		return orderFormInInstance;
	}

	@Transactional
	public InstanceForm merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		InstanceForm merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			InstanceForm attached = InstanceForm.findInstanceForm(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setFormId(Form formId) {
		this.formId = formId;
	}

	public void setId(InstanceFormPK id) {
		this.id = id;
	}

	public void setInstanceId(Instance instanceId) {
		this.instanceId = instanceId;
	}

	public void setOrderFormInInstance(Integer orderFormInInstance) {
		this.orderFormInInstance = orderFormInInstance;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexInstanceForm(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
