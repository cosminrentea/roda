package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "form_selection_var")
@Configurable

public class FormSelectionVar {

	public static long countFormSelectionVars() {
		return entityManager().createQuery("SELECT COUNT(o) FROM FormSelectionVar o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(FormSelectionVar formSelectionVar) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("formselectionvar_" + formSelectionVar.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new FormSelectionVar().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<FormSelectionVar> findAllFormSelectionVars() {
		return entityManager().createQuery("SELECT o FROM FormSelectionVar o", FormSelectionVar.class).getResultList();
	}

	public static FormSelectionVar findFormSelectionVar(FormSelectionVarPK id) {
		if (id == null)
			return null;
		return entityManager().find(FormSelectionVar.class, id);
	}

	public static List<FormSelectionVar> findFormSelectionVarEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM FormSelectionVar o", FormSelectionVar.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<FormSelectionVar> fromJsonArrayToFormSelectionVars(String json) {
		return new JSONDeserializer<List<FormSelectionVar>>().use(null, ArrayList.class)
				.use("values", FormSelectionVar.class).deserialize(json);
	}

	public static FormSelectionVar fromJsonToFormSelectionVar(String json) {
		return new JSONDeserializer<FormSelectionVar>().use(null, FormSelectionVar.class).deserialize(json);
	}

	public static void indexFormSelectionVar(FormSelectionVar formSelectionVar) {
		List<FormSelectionVar> formselectionvars = new ArrayList<FormSelectionVar>();
		formselectionvars.add(formSelectionVar);
		indexFormSelectionVars(formselectionvars);
	}

	@Async
	public static void indexFormSelectionVars(Collection<FormSelectionVar> formselectionvars) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (FormSelectionVar formSelectionVar : formselectionvars) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "formselectionvar_" + formSelectionVar.getId());
			sid.addField("formSelectionVar.formid_t", formSelectionVar.getFormId());
			sid.addField("formSelectionVar.selectionvariableitem_t", formSelectionVar.getSelectionVariableItem());
			sid.addField("formSelectionVar.orderofitemsinresponse_i", formSelectionVar.getOrderOfItemsInResponse());
			sid.addField("formSelectionVar.id_t", formSelectionVar.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"formselectionvar_solrsummary_t",
					new StringBuilder().append(formSelectionVar.getFormId()).append(" ")
							.append(formSelectionVar.getSelectionVariableItem()).append(" ")
							.append(formSelectionVar.getOrderOfItemsInResponse()).append(" ")
							.append(formSelectionVar.getId()));
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
		String searchString = "FormSelectionVar_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new FormSelectionVar().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<FormSelectionVar> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@ManyToOne
	@JoinColumn(name = "form_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Form formId;

	@EmbeddedId
	private FormSelectionVarPK id;

	@Column(name = "order_of_items_in_response", columnDefinition = "int4")
	private Integer orderOfItemsInResponse;

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "variable_id", referencedColumnName = "variable_id", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "item_id", referencedColumnName = "item_id", nullable = false, insertable = false, updatable = false) })
	private SelectionVariableItem selectionVariableItem;

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

	public Form getFormId() {
		return formId;
	}

	public FormSelectionVarPK getId() {
		return this.id;
	}

	public Integer getOrderOfItemsInResponse() {
		return orderOfItemsInResponse;
	}

	public SelectionVariableItem getSelectionVariableItem() {
		return selectionVariableItem;
	}

	@Transactional
	public FormSelectionVar merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		FormSelectionVar merged = this.entityManager.merge(this);
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
			FormSelectionVar attached = FormSelectionVar.findFormSelectionVar(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setFormId(Form formId) {
		this.formId = formId;
	}

	public void setId(FormSelectionVarPK id) {
		this.id = id;
	}

	public void setOrderOfItemsInResponse(Integer orderOfItemsInResponse) {
		this.orderOfItemsInResponse = orderOfItemsInResponse;
	}

	public void setSelectionVariableItem(SelectionVariableItem selectionVariableItem) {
		this.selectionVariableItem = selectionVariableItem;
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
		indexFormSelectionVar(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
