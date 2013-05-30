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
import javax.persistence.ManyToOne;
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
@Table(schema = "public", name = "user_setting")
@Configurable

public class UserSetting {

	public static long countUserSettings() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UserSetting o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(UserSetting userSetting) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("usersetting_" + userSetting.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new UserSetting().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<UserSetting> findAllUserSettings() {
		return entityManager().createQuery("SELECT o FROM UserSetting o", UserSetting.class).getResultList();
	}

	public static UserSetting findUserSetting(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(UserSetting.class, id);
	}

	public static List<UserSetting> findUserSettingEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM UserSetting o", UserSetting.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<UserSetting> fromJsonArrayToUserSettings(String json) {
		return new JSONDeserializer<List<UserSetting>>().use(null, ArrayList.class).use("values", UserSetting.class)
				.deserialize(json);
	}

	public static UserSetting fromJsonToUserSetting(String json) {
		return new JSONDeserializer<UserSetting>().use(null, UserSetting.class).deserialize(json);
	}

	public static void indexUserSetting(UserSetting userSetting) {
		List<UserSetting> usersettings = new ArrayList<UserSetting>();
		usersettings.add(userSetting);
		indexUserSettings(usersettings);
	}

	@Async
	public static void indexUserSettings(Collection<UserSetting> usersettings) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (UserSetting userSetting : usersettings) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "usersetting_" + userSetting.getId());
			sid.addField("userSetting.usersettinggroupid_t", userSetting.getUserSettingGroupId());
			sid.addField("userSetting.name_s", userSetting.getName());
			sid.addField("userSetting.description_s", userSetting.getDescription());
			sid.addField("userSetting.defaultvalue_s", userSetting.getDefaultValue());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"usersetting_solrsummary_t",
					new StringBuilder().append(userSetting.getUserSettingGroupId()).append(" ")
							.append(userSetting.getName()).append(" ").append(userSetting.getDescription()).append(" ")
							.append(userSetting.getDefaultValue()));
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
		String searchString = "UserSetting_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new UserSetting().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<UserSetting> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "default_value", columnDefinition = "text")
	private String defaultValue;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@ManyToOne
	@JoinColumn(name = "user_setting_group_id", referencedColumnName = "id", nullable = false)
	private UserSettingGroup userSettingGroupId;

	@OneToMany(mappedBy = "userSettingId")
	private Set<UserSettingValue> userSettingValues;

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

	public UserSettingGroup getUserSettingGroupId() {
		return userSettingGroupId;
	}

	public Set<UserSettingValue> getUserSettingValues() {
		return userSettingValues;
	}

	@Transactional
	public UserSetting merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UserSetting merged = this.entityManager.merge(this);
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
			UserSetting attached = UserSetting.findUserSetting(this.id);
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

	public void setUserSettingGroupId(UserSettingGroup userSettingGroupId) {
		this.userSettingGroupId = userSettingGroupId;
	}

	public void setUserSettingValues(Set<UserSettingValue> userSettingValues) {
		this.userSettingValues = userSettingValues;
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
		indexUserSetting(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
