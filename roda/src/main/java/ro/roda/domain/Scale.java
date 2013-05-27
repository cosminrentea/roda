package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(schema = "public", name = "scale")
@Configurable
@Audited
public class Scale {

	public static long countScales() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Scale o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Scale scale) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("scale_" + scale.getItemId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Scale().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Scale> findAllScales() {
		return entityManager().createQuery("SELECT o FROM Scale o", Scale.class).getResultList();
	}

	public static Scale findScale(Long itemId) {
		if (itemId == null)
			return null;
		return entityManager().find(Scale.class, itemId);
	}

	public static List<Scale> findScaleEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Scale o", Scale.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Scale> fromJsonArrayToScales(String json) {
		return new JSONDeserializer<List<Scale>>().use(null, ArrayList.class).use("values", Scale.class)
				.deserialize(json);
	}

	public static Scale fromJsonToScale(String json) {
		return new JSONDeserializer<Scale>().use(null, Scale.class).deserialize(json);
	}

	public static void indexScale(Scale scale) {
		List<Scale> scales = new ArrayList<Scale>();
		scales.add(scale);
		indexScales(scales);
	}

	@Async
	public static void indexScales(Collection<Scale> scales) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Scale scale : scales) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "scale_" + scale.getItemId());
			sid.addField("scale.item_t", scale.getItem());
			sid.addField("scale.maxvalueid_t", scale.getMaxValueId());
			sid.addField("scale.minvalueid_t", scale.getMinValueId());
			sid.addField("scale.units_t", scale.getUnits());
			sid.addField("scale.itemid_l", scale.getItemId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"scale_solrsummary_t",
					new StringBuilder().append(scale.getItem()).append(" ").append(scale.getMaxValueId()).append(" ")
							.append(scale.getMinValueId()).append(" ").append(scale.getUnits()).append(" ")
							.append(scale.getItemId()));
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
		String searchString = "Scale_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Scale().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Scale> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@OneToOne
	@JoinColumn(name = "item_id", nullable = false, insertable = false, updatable = false)
	private Item item;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "item_id", columnDefinition = "int8")
	private Long itemId;

	@ManyToOne
	@JoinColumn(name = "max_value_id", referencedColumnName = "item_id", nullable = false)
	private Value maxValueId;

	@ManyToOne
	@JoinColumn(name = "min_value_id", referencedColumnName = "item_id", nullable = false)
	private Value minValueId;

	@Column(name = "units", columnDefinition = "int2")
	@NotNull
	private Short units;

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

	public Item getItem() {
		return item;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public Value getMaxValueId() {
		return maxValueId;
	}

	public Value getMinValueId() {
		return minValueId;
	}

	public Short getUnits() {
		return units;
	}

	@Transactional
	public Scale merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Scale merged = this.entityManager.merge(this);
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
			Scale attached = Scale.findScale(this.itemId);
			this.entityManager.remove(attached);
		}
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void setItemId(Long id) {
		this.itemId = id;
	}

	public void setMaxValueId(Value maxValueId) {
		this.maxValueId = maxValueId;
	}

	public void setMinValueId(Value minValueId) {
		this.minValueId = minValueId;
	}

	public void setUnits(Short units) {
		this.units = units;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames("item")
				.toString();
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexScale(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
