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
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

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

import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "setting")
@Audited
public class Setting {

	public static long countSettings() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Setting o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Setting setting) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("setting_" + setting.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Setting().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Setting> findAllSettings() {
		return entityManager().createQuery("SELECT o FROM Setting o", Setting.class).getResultList();
	}

	public static Setting findSetting(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Setting.class, id);
	}

	public static List<Setting> findSettingEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Setting o", Setting.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Setting> fromJsonArrayToSettings(String json) {
		return new JSONDeserializer<List<Setting>>().use(null, ArrayList.class).use("values", Setting.class)
				.deserialize(json);
	}

	public static Setting fromJsonToSetting(String json) {
		return new JSONDeserializer<Setting>().use(null, Setting.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Setting> collection) {
		JSONSerializer serializer = new JSONSerializer().exclude("*.class").exclude("defaultValue", "description");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"success\": true, \"data\":" + serializer.exclude("classAuditReader", "auditReader").serialize(collection) + "}";
	}

	public static void indexSetting(Setting setting) {
		List<Setting> settings = new ArrayList<Setting>();
		settings.add(setting);
		indexSettings(settings);
	}

	@Async
	public static void indexSettings(Collection<Setting> settings) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Setting setting : settings) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "setting_" + setting.getId());
			sid.addField("setting.id_i", setting.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("setting_solrsummary_t", new StringBuilder().append(setting.getId()));
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
		String searchString = "Setting_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Setting().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Setting</code> (setare de
	 * aplicatie) in baza de date; in caz afirmativ il returneaza, altfel,
	 * metoda il introduce in baza de date si apoi il returneaza. Verificarea
	 * existentei in baza de date se realizeaza fie dupa identificator, fie dupa
	 * un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>name + settingGroupId
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul setarii.
	 * @param name
	 *            - numele setarii.
	 * @param description
	 *            - descrierea setarii.
	 * @param defaultValue
	 *            - valoarea implicita a setarii.
	 * @param value
	 *            - valoarea setarii.
	 * @return
	 */
	public static Setting checkSetting(Integer id, String name, String description, String defaultValue, String value) {
		Setting object;

		if (id != null) {
			object = findSetting(id);

			if (object != null) {
				return object;
			}
		}

		List<Setting> queryResult;

		if (name != null) {
			TypedQuery<Setting> query = entityManager().createQuery(
					"SELECT o FROM Setting o WHERE lower(o.name) = lower(:name)", Setting.class);
			query.setParameter("name", name);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new Setting();
		object.name = name;
		object.description = description;
		object.defaultValue = defaultValue;
		object.value = value;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "default_value", columnDefinition = "text")
	private String defaultValue;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "text", unique = true)
	@NotNull
	private String name;

	@Column(name = "value", columnDefinition = "text")
	@NotNull
	private String value;

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

	public String getDefaultValue() {
		return defaultValue;
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	@Transactional
	public Setting merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Setting merged = this.entityManager.merge(this);
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
			Setting attached = Setting.findSetting(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexSetting(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((Setting) obj).id))
				|| ((name != null && name.equalsIgnoreCase(((Setting) obj).name)));
	}

	// @JsonIgnore public AuditReader getAuditReader() {
	// return AuditReaderFactory.get(entityManager);
	// }

}
