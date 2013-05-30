package ro.roda.domain;

import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(schema = "public", name = "selection_variable")

public class SelectionVariable {

	public static long countSelectionVariables() {
		return entityManager().createQuery("SELECT COUNT(o) FROM SelectionVariable o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(SelectionVariable selectionVariable) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("selectionvariable_" + selectionVariable.getVariableId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new SelectionVariable().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<SelectionVariable> findAllSelectionVariables() {
		return entityManager().createQuery("SELECT o FROM SelectionVariable o", SelectionVariable.class)
				.getResultList();
	}

	public static SelectionVariable findSelectionVariable(Long variableId) {
		if (variableId == null)
			return null;
		return entityManager().find(SelectionVariable.class, variableId);
	}

	public static List<SelectionVariable> findSelectionVariableEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM SelectionVariable o", SelectionVariable.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<SelectionVariable> fromJsonArrayToSelectionVariables(String json) {
		return new JSONDeserializer<List<SelectionVariable>>().use(null, ArrayList.class)
				.use("values", SelectionVariable.class).deserialize(json);
	}

	public static SelectionVariable fromJsonToSelectionVariable(String json) {
		return new JSONDeserializer<SelectionVariable>().use(null, SelectionVariable.class).deserialize(json);
	}

	public static void indexSelectionVariable(SelectionVariable selectionVariable) {
		List<SelectionVariable> selectionvariables = new ArrayList<SelectionVariable>();
		selectionvariables.add(selectionVariable);
		indexSelectionVariables(selectionvariables);
	}

	@Async
	public static void indexSelectionVariables(Collection<SelectionVariable> selectionvariables) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (SelectionVariable selectionVariable : selectionvariables) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "selectionvariable_" + selectionVariable.getVariableId());
			sid.addField("selectionVariable.variable_t", selectionVariable.getVariable());
			sid.addField("selectionVariable.mincount_t", selectionVariable.getMinCount());
			sid.addField("selectionVariable.maxcount_t", selectionVariable.getMaxCount());
			sid.addField("selectionVariable.variableid_l", selectionVariable.getVariableId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"selectionvariable_solrsummary_t",
					new StringBuilder().append(selectionVariable.getVariable()).append(" ")
							.append(selectionVariable.getMinCount()).append(" ")
							.append(selectionVariable.getMaxCount()).append(" ")
							.append(selectionVariable.getVariableId()));
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
		String searchString = "SelectionVariable_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new SelectionVariable().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<SelectionVariable> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "max_count", columnDefinition = "int2")
	@NotNull
	private Short maxCount;

	@Column(name = "min_count", columnDefinition = "int2")
	@NotNull
	private Short minCount;

	@OneToMany(mappedBy = "variableId")
	private Set<SelectionVariableItem> selectionVariableItems;

	@OneToOne
	@JoinColumn(name = "variable_id", nullable = false, insertable = false, updatable = false)
	private Variable variable;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "variable_id", columnDefinition = "int8")
	private Long variableId;

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

	public Short getMaxCount() {
		return maxCount;
	}

	public Short getMinCount() {
		return minCount;
	}

	public Set<SelectionVariableItem> getSelectionVariableItems() {
		return selectionVariableItems;
	}

	public Variable getVariable() {
		return variable;
	}

	public Long getVariableId() {
		return this.variableId;
	}

	@Transactional
	public SelectionVariable merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		SelectionVariable merged = this.entityManager.merge(this);
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
			SelectionVariable attached = SelectionVariable.findSelectionVariable(this.variableId);
			this.entityManager.remove(attached);
		}
	}

	public void setMaxCount(Short maxCount) {
		this.maxCount = maxCount;
	}

	public void setMinCount(Short minCount) {
		this.minCount = minCount;
	}

	public void setSelectionVariableItems(Set<SelectionVariableItem> selectionVariableItems) {
		this.selectionVariableItems = selectionVariableItems;
	}

	public void setVariable(Variable variable) {
		this.variable = variable;
	}

	public void setVariableId(Long id) {
		this.variableId = id;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("variable")
				.toString();
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexSelectionVariable(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
