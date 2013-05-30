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
@Table(schema = "public", name = "regiontype")
@Configurable

public class Regiontype {

	public static long countRegiontypes() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Regiontype o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Regiontype regiontype) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("regiontype_" + regiontype.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Regiontype().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Regiontype> findAllRegiontypes() {
		return entityManager().createQuery("SELECT o FROM Regiontype o", Regiontype.class).getResultList();
	}

	public static Regiontype findRegiontype(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Regiontype.class, id);
	}

	public static List<Regiontype> findRegiontypeEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Regiontype o", Regiontype.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Regiontype> fromJsonArrayToRegiontypes(String json) {
		return new JSONDeserializer<List<Regiontype>>().use(null, ArrayList.class).use("values", Regiontype.class)
				.deserialize(json);
	}

	public static Regiontype fromJsonToRegiontype(String json) {
		return new JSONDeserializer<Regiontype>().use(null, Regiontype.class).deserialize(json);
	}

	public static void indexRegiontype(Regiontype regiontype) {
		List<Regiontype> regiontypes = new ArrayList<Regiontype>();
		regiontypes.add(regiontype);
		indexRegiontypes(regiontypes);
	}

	@Async
	public static void indexRegiontypes(Collection<Regiontype> regiontypes) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Regiontype regiontype : regiontypes) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "regiontype_" + regiontype.getId());
			sid.addField("regiontype.name_s", regiontype.getName());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("regiontype_solrsummary_t", new StringBuilder().append(regiontype.getName()));
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
		String searchString = "Regiontype_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Regiontype().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Regiontype> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@OneToMany(mappedBy = "regiontypeId")
	private Set<Region> regions;

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

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public Set<Region> getRegions() {
		return regions;
	}

	@Transactional
	public Regiontype merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Regiontype merged = this.entityManager.merge(this);
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
			Regiontype attached = Regiontype.findRegiontype(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegions(Set<Region> regions) {
		this.regions = regions;
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
		indexRegiontype(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
