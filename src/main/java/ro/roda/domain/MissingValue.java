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

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "missing_value")
@Audited
public class MissingValue {

	public static long countMissingValues() {
		return entityManager().createQuery("SELECT COUNT(o) FROM MissingValue o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(MissingValue missingValue) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("studydescr_" + missingValue.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new MissingValue().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<MissingValue> findAllMissingValues() {
		return entityManager().createQuery("SELECT o FROM MissingValue o", MissingValue.class).getResultList();
	}

	public static MissingValue findMissingValue(MissingValuePK id) {
		if (id == null)
			return null;
		return entityManager().find(MissingValue.class, id);
	}

	public static List<MissingValue> findMissingValueEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM MissingValue o", MissingValue.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<MissingValue> fromJsonArrayToMissingValues(String json) {
		return new JSONDeserializer<List<MissingValue>>().use(null, ArrayList.class).use("values", MissingValue.class)
				.deserialize(json);
	}

	public static MissingValue fromJsonToMissingValue(String json) {
		return new JSONDeserializer<MissingValue>().use(null, MissingValue.class).deserialize(json);
	}

	public static void indexMissingValue(MissingValue missingValue) {
		List<MissingValue> missingvals = new ArrayList<MissingValue>();
		missingvals.add(missingValue);
		indexMissingValues(missingvals);
	}

	@Async
	public static void indexMissingValues(Collection<MissingValue> missingvals) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (MissingValue missingValue : missingvals) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "missingval_" + missingValue.getId());
			sid.addField("missingValue.questionid_t", missingValue.getQuestionId());
			sid.addField("missingValue.valueid_t", missingValue.getValueId());
			sid.addField("missingValue.label_s", missingValue.getLabel());
			sid.addField("missingValue.value_s", missingValue.getValue());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"studydescr_solrsummary_t",
					new StringBuilder().append(missingValue.getQuestionId()).append(" ")
							.append(missingValue.getValueId()).append(" ").append(missingValue.getLabel()).append(" ")
							.append(missingValue.getValue()));
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
		String searchString = "MissingValue_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new MissingValue().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<MissingValue> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unei valori speciale (preluata prin valorile
	 * parametrilor de intrare) in baza de date; in caz afirmativ, returneaza
	 * obiectul corespunzator, altfel, metoda introduce valoarea speciala in
	 * baza de date si apoi returneaza obiectul corespunzator. Verificarea
	 * existentei in baza de date se realizeaza fie dupa valoarea cheii primare,
	 * fie dupa un criteriu de unicitate.
	 * 
	 * Criteriu de unicitate: questionId + label
	 * 
	 * @param questionId
	 * @param label
	 * @return
	 */
	public static Study checkMissingValue(Integer questionId, Integer valueId, String label, Integer value) {
		// TODO
		return null;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "label", columnDefinition = "text")
	private String label;

	@Column(name = "value", columnDefinition = "integer")
	private Integer value;

	@EmbeddedId
	private MissingValuePK id;

	@ManyToOne
	@JoinColumn(name = "question_id", columnDefinition = "int8", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Question questionId;

	@Column(name = "value_id", columnDefinition = "int4", nullable = false, insertable = false, updatable = false)
	private Integer valueId;

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

	public String getLabel() {
		return label;
	}

	public MissingValuePK getId() {
		return this.id;
	}

	public Question getQuestionId() {
		return questionId;
	}

	public Integer getValue() {
		return value;
	}

	public Integer getValueId() {
		return valueId;
	}

	@Transactional
	public MissingValue merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		MissingValue merged = this.entityManager.merge(this);
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
			MissingValue attached = MissingValue.findMissingValue(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setQuestionId(Question questionId) {
		this.questionId = questionId;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public void setValueId(Integer valueId) {
		this.valueId = valueId;
	}

	public void setId(MissingValuePK id) {
		this.id = id;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@JsonIgnore public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexMissingValue(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
