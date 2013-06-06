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
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "form_edited_text_var")

public class FormEditedTextVar {

	public static long countFormEditedTextVars() {
		return entityManager().createQuery("SELECT COUNT(o) FROM FormEditedTextVar o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(FormEditedTextVar formEditedTextVar) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("formeditedtextvar_" + formEditedTextVar.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new FormEditedTextVar().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<FormEditedTextVar> findAllFormEditedTextVars() {
		return entityManager().createQuery("SELECT o FROM FormEditedTextVar o", FormEditedTextVar.class)
				.getResultList();
	}

	public static FormEditedTextVar findFormEditedTextVar(FormEditedTextVarPK id) {
		if (id == null)
			return null;
		return entityManager().find(FormEditedTextVar.class, id);
	}

	public static List<FormEditedTextVar> findFormEditedTextVarEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM FormEditedTextVar o", FormEditedTextVar.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<FormEditedTextVar> fromJsonArrayToFormEditedTextVars(String json) {
		return new JSONDeserializer<List<FormEditedTextVar>>().use(null, ArrayList.class)
				.use("values", FormEditedTextVar.class).deserialize(json);
	}

	public static FormEditedTextVar fromJsonToFormEditedTextVar(String json) {
		return new JSONDeserializer<FormEditedTextVar>().use(null, FormEditedTextVar.class).deserialize(json);
	}

	public static void indexFormEditedTextVar(FormEditedTextVar formEditedTextVar) {
		List<FormEditedTextVar> formeditedtextvars = new ArrayList<FormEditedTextVar>();
		formeditedtextvars.add(formEditedTextVar);
		indexFormEditedTextVars(formeditedtextvars);
	}

	@Async
	public static void indexFormEditedTextVars(Collection<FormEditedTextVar> formeditedtextvars) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (FormEditedTextVar formEditedTextVar : formeditedtextvars) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "formeditedtextvar_" + formEditedTextVar.getId());
			sid.addField("formEditedTextVar.formid_t", formEditedTextVar.getFormId());
			sid.addField("formEditedTextVar.variableid_t", formEditedTextVar.getVariableId());
			sid.addField("formEditedTextVar.text_s", formEditedTextVar.getText());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"formeditedtextvar_solrsummary_t",
					new StringBuilder().append(formEditedTextVar.getFormId()).append(" ")
							.append(formEditedTextVar.getVariableId()).append(" ").append(formEditedTextVar.getText()));
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
		String searchString = "FormEditedTextVar_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new FormEditedTextVar().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<FormEditedTextVar> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@ManyToOne
	@JoinColumn(name = "form_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Form formId;

	@EmbeddedId
	private FormEditedTextVarPK id;

	@Column(name = "text", columnDefinition = "text")
	@NotNull
	private String text;

	@ManyToOne
	@JoinColumn(name = "variable_id", columnDefinition = "bigint", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Variable variableId;

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

	public FormEditedTextVarPK getId() {
		return this.id;
	}

	public String getText() {
		return text;
	}

	public Variable getVariableId() {
		return variableId;
	}

	@Transactional
	public FormEditedTextVar merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		FormEditedTextVar merged = this.entityManager.merge(this);
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
			FormEditedTextVar attached = FormEditedTextVar.findFormEditedTextVar(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setFormId(Form formId) {
		this.formId = formId;
	}

	public void setId(FormEditedTextVarPK id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setVariableId(Variable variableId) {
		this.variableId = variableId;
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
		indexFormEditedTextVar(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
