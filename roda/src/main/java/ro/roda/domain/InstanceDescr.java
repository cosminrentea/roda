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

@Entity
@Table(schema = "public", name = "instance_descr")
@Configurable
public class InstanceDescr {

	public static long countInstanceDescrs() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM InstanceDescr o", Long.class)
				.getSingleResult();
	}

	@Async
	public static void deleteIndex(InstanceDescr instanceDescr) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("instancedescr_" + instanceDescr.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new InstanceDescr().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<InstanceDescr> findAllInstanceDescrs() {
		return entityManager().createQuery("SELECT o FROM InstanceDescr o",
				InstanceDescr.class).getResultList();
	}

	public static InstanceDescr findInstanceDescr(InstanceDescrPK id) {
		if (id == null)
			return null;
		return entityManager().find(InstanceDescr.class, id);
	}

	public static List<InstanceDescr> findInstanceDescrEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM InstanceDescr o",
						InstanceDescr.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<InstanceDescr> fromJsonArrayToInstanceDescrs(
			String json) {
		return new JSONDeserializer<List<InstanceDescr>>()
				.use(null, ArrayList.class).use("values", InstanceDescr.class)
				.deserialize(json);
	}

	public static InstanceDescr fromJsonToInstanceDescr(String json) {
		return new JSONDeserializer<InstanceDescr>().use(null,
				InstanceDescr.class).deserialize(json);
	}

	public static void indexInstanceDescr(InstanceDescr instanceDescr) {
		List<InstanceDescr> instancedescrs = new ArrayList<InstanceDescr>();
		instancedescrs.add(instanceDescr);
		indexInstanceDescrs(instancedescrs);
	}

	@Async
	public static void indexInstanceDescrs(
			Collection<InstanceDescr> instancedescrs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (InstanceDescr instanceDescr : instancedescrs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "instancedescr_" + instanceDescr.getId());
			sid.addField("instanceDescr.instanceid_t",
					instanceDescr.getInstanceId());
			sid.addField("instanceDescr.langid_t", instanceDescr.getLangId());
			sid.addField("instanceDescr.accessconditions_s",
					instanceDescr.getAccessConditions());
			sid.addField("instanceDescr.notes_s", instanceDescr.getNotes());
			sid.addField("instanceDescr.title_s", instanceDescr.getTitle());
			sid.addField("instanceDescr.id_t", instanceDescr.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"instancedescr_solrsummary_t",
					new StringBuilder().append(instanceDescr.getInstanceId())
							.append(" ").append(instanceDescr.getLangId())
							.append(" ")
							.append(instanceDescr.getAccessConditions())
							.append(" ").append(instanceDescr.getNotes())
							.append(" ").append(instanceDescr.getTitle())
							.append(" ").append(instanceDescr.getId()));
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
		String searchString = "InstanceDescr_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new InstanceDescr().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<InstanceDescr> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unei descrieri de instanta in baza de date; in caz
	 * afirmativ, returneaza obiectul corespunzator, altfel, metoda introduce
	 * descrierea in baza de date si apoi returneaza obiectul corespunzator.
	 * Verificarea existentei in baza de date se realizeaza fie dupa valoarea
	 * identificatorului, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>instanceId + title
	 * <ul>
	 * 
	 * <p>
	 * 
	 * @param instanceId
	 *            - identificatorul instantei.
	 * @param languageId
	 *            - identificatorul limbii.
	 * @param accessConditions
	 *            - conditiile de acces la date.
	 * @param notes
	 *            - observatii.
	 * @param title
	 *            - titlul descrierii.
	 * @param original
	 *            - TODO.
	 * @return
	 */
	public static InstanceDescr checkInstanceDescr(Integer instanceId,
			Integer languageId, String accessConditions, String notes,
			String title, Boolean original) {
		// TODO
		return null;
	}

	@Column(name = "access_conditions", columnDefinition = "text")
	private String accessConditions;

	@EmbeddedId
	private InstanceDescrPK id;

	@ManyToOne
	@JoinColumn(name = "instance_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Instance instanceId;

	@ManyToOne
	@JoinColumn(name = "lang_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Lang langId;

	@Column(name = "notes", columnDefinition = "text")
	private String notes;

	@Column(name = "original_title_language", columnDefinition = "bool")
	@NotNull
	private boolean originalTitleLanguage;

	@Column(name = "title", columnDefinition = "text")
	@NotNull
	private String title;

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

	public String getAccessConditions() {
		return accessConditions;
	}

	public InstanceDescrPK getId() {
		return this.id;
	}

	public Instance getInstanceId() {
		return instanceId;
	}

	public Lang getLangId() {
		return langId;
	}

	public String getNotes() {
		return notes;
	}

	public String getTitle() {
		return title;
	}

	public boolean isOriginalTitleLanguage() {
		return originalTitleLanguage;
	}

	@Transactional
	public InstanceDescr merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		InstanceDescr merged = this.entityManager.merge(this);
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
			InstanceDescr attached = InstanceDescr.findInstanceDescr(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAccessConditions(String accessConditions) {
		this.accessConditions = accessConditions;
	}

	public void setId(InstanceDescrPK id) {
		this.id = id;
	}

	public void setInstanceId(Instance instanceId) {
		this.instanceId = instanceId;
	}

	public void setLangId(Lang langId) {
		this.langId = langId;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setOriginalTitleLanguage(boolean originalTitleLanguage) {
		this.originalTitleLanguage = originalTitleLanguage;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexInstanceDescr(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
