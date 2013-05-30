package ro.roda.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "form")

public class Form {

	public static long countForms() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Form o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Form form) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("form_" + form.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Form().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Form> findAllForms() {
		return entityManager().createQuery("SELECT o FROM Form o", Form.class).getResultList();
	}

	public static Form findForm(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Form.class, id);
	}

	public static List<Form> findFormEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Form o", Form.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Form> fromJsonArrayToForms(String json) {
		return new JSONDeserializer<List<Form>>().use(null, ArrayList.class).use("values", Form.class)
				.deserialize(json);
	}

	public static Form fromJsonToForm(String json) {
		return new JSONDeserializer<Form>().use(null, Form.class).deserialize(json);
	}

	public static void indexForm(Form form) {
		List<Form> forms = new ArrayList<Form>();
		forms.add(form);
		indexForms(forms);
	}

	@Async
	public static void indexForms(Collection<Form> forms) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Form form : forms) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "form_" + form.getId());
			sid.addField("form.operatorid_t", form.getOperatorId());
			sid.addField("form.operatornotes_s", form.getOperatorNotes());
			sid.addField("form.formfilledat_dt", form.getFormFilledAt().getTime());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("form_solrsummary_t",
					new StringBuilder().append(form.getOperatorId()).append(" ").append(form.getOperatorNotes())
							.append(" ").append(form.getFormFilledAt().getTime()));
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
		String searchString = "Form_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Form().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Form> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@OneToMany(mappedBy = "formId")
	private Set<FormEditedNumberVar> formEditedNumberVars;

	@OneToMany(mappedBy = "formId")
	private Set<FormEditedTextVar> formEditedTextVars;

	@Column(name = "form_filled_at", columnDefinition = "timestamp")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar formFilledAt;

	@OneToMany(mappedBy = "formId")
	private Set<FormSelectionVar> formSelectionVars;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "bigserial")
	private Long id;

	@OneToMany(mappedBy = "formId")
	private Set<InstanceForm> instanceForms;

	@ManyToOne
	@JoinColumn(name = "operator_id", referencedColumnName = "id")
	private Person operatorId;

	@Column(name = "operator_notes", columnDefinition = "text")
	private String operatorNotes;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired
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

	public Set<FormEditedNumberVar> getFormEditedNumberVars() {
		return formEditedNumberVars;
	}

	public Set<FormEditedTextVar> getFormEditedTextVars() {
		return formEditedTextVars;
	}

	public Calendar getFormFilledAt() {
		return formFilledAt;
	}

	public Set<FormSelectionVar> getFormSelectionVars() {
		return formSelectionVars;
	}

	public Long getId() {
		return this.id;
	}

	public Set<InstanceForm> getInstanceForms() {
		return instanceForms;
	}

	public Person getOperatorId() {
		return operatorId;
	}

	public String getOperatorNotes() {
		return operatorNotes;
	}

	@Transactional
	public Form merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Form merged = this.entityManager.merge(this);
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
			Form attached = Form.findForm(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setFormEditedNumberVars(Set<FormEditedNumberVar> formEditedNumberVars) {
		this.formEditedNumberVars = formEditedNumberVars;
	}

	public void setFormEditedTextVars(Set<FormEditedTextVar> formEditedTextVars) {
		this.formEditedTextVars = formEditedTextVars;
	}

	public void setFormFilledAt(Calendar formFilledAt) {
		this.formFilledAt = formFilledAt;
	}

	public void setFormSelectionVars(Set<FormSelectionVar> formSelectionVars) {
		this.formSelectionVars = formSelectionVars;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInstanceForms(Set<InstanceForm> instanceForms) {
		this.instanceForms = instanceForms;
	}

	public void setOperatorId(Person operatorId) {
		this.operatorId = operatorId;
	}

	public void setOperatorNotes(String operatorNotes) {
		this.operatorNotes = operatorNotes;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexForm(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
