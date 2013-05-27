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
@Table(schema = "public", name = "item")
@Audited
public class Item {

	@OneToOne(mappedBy = "item")
	private Scale scale;

	@OneToOne(mappedBy = "item")
	private Value value;

	@OneToMany(mappedBy = "itemId")
	private Set<SelectionVariableItem> selectionVariableItems;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
	@NotNull
	private String name;

	public Scale getScale() {
		return scale;
	}

	public void setScale(Scale scale) {
		this.scale = scale;
	}

	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}

	public Set<SelectionVariableItem> getSelectionVariableItems() {
		return selectionVariableItems;
	}

	public void setSelectionVariableItems(Set<SelectionVariableItem> selectionVariableItems) {
		this.selectionVariableItems = selectionVariableItems;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "Item_solrsummary_t:" + queryString;
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

	public static void indexItem(Item item) {
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		indexItems(items);
	}

	@Async
	public static void indexItems(Collection<Item> items) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Item item : items) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "item_" + item.getId());
			sid.addField("item.scale_t", item.getScale());
			sid.addField("item.value_t", item.getValue());
			sid.addField("item.name_s", item.getName());
			sid.addField("item.id_l", item.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"item_solrsummary_t",
					new StringBuilder().append(item.getScale()).append(" ").append(item.getValue()).append(" ")
							.append(item.getName()).append(" ").append(item.getId()));
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
	public static void deleteIndex(Item item) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("item_" + item.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexItem(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Item().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static Item fromJsonToItem(String json) {
		return new JSONDeserializer<Item>().use(null, Item.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Item> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<Item> fromJsonArrayToItems(String json) {
		return new JSONDeserializer<List<Item>>().use(null, ArrayList.class).use("values", Item.class)
				.deserialize(json);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "bigserial")
	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Item().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countItems() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Item o", Long.class).getSingleResult();
	}

	public static List<Item> findAllItems() {
		return entityManager().createQuery("SELECT o FROM Item o", Item.class).getResultList();
	}

	public static Item findItem(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Item.class, id);
	}

	public static List<Item> findItemEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Item o", Item.class).setFirstResult(firstResult)
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
			Item attached = Item.findItem(this.id);
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
	public Item merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Item merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
