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

@Entity
@Table(schema = "public", name = "value")
@Configurable
@Audited
public class Value {

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Value().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countValues() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Value o", Long.class).getSingleResult();
	}

	public static List<Value> findAllValues() {
		return entityManager().createQuery("SELECT o FROM Value o", Value.class).getResultList();
	}

	public static Value findValue(Long itemId) {
		if (itemId == null)
			return null;
		return entityManager().find(Value.class, itemId);
	}

	public static List<Value> findValueEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Value o", Value.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
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
			Value attached = Value.findValue(this.itemId);
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public Value merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Value merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@OneToOne
	@JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
	private Item item;

	@OneToMany(mappedBy = "maxValueId")
	private Set<Scale> scales;

	@OneToMany(mappedBy = "minValueId")
	private Set<Scale> scales1;

	@Column(name = "value", columnDefinition = "int4")
	@NotNull
	private Integer value;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Set<Scale> getScales() {
		return scales;
	}

	public void setScales(Set<Scale> scales) {
		this.scales = scales;
	}

	public Set<Scale> getScales1() {
		return scales1;
	}

	public void setScales1(Set<Scale> scales1) {
		this.scales1 = scales1;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("item")
				.toString();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_id", columnDefinition = "int8")
	private Long itemId;

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long id) {
		this.itemId = id;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static Value fromJsonToValue(String json) {
		return new JSONDeserializer<Value>().use(null, Value.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Value> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<Value> fromJsonArrayToValues(String json) {
		return new JSONDeserializer<List<Value>>().use(null, ArrayList.class).use("values", Value.class)
				.deserialize(json);
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "Value_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static void indexValue(Value value) {
		List<Value> values = new ArrayList<Value>();
		values.add(value);
		indexValues(values);
	}

	@Async
	public static void indexValues(Collection<Value> values) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Value value : values) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "value_" + value.getItemId());
			sid.addField("value.item_t", value.getItem());
			sid.addField("value.value_i", value.getValue());
			sid.addField("value.itemid_l", value.getItemId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("value_solrsummary_t",
					new StringBuilder().append(value.getItem()).append(" ").append(value.getValue()).append(" ")
							.append(value.getItemId()));
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

	@Async
	public static void deleteIndex(Value value) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("value_" + value.getItemId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexValue(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Value().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}
}
