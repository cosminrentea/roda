package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public", name = "user_setting_group")
@Configurable
public class UserSettingGroup {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new UserSettingGroup().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countUserSettingGroups() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UserSettingGroup o", Long.class).getSingleResult();
	}

	public static List<UserSettingGroup> findAllUserSettingGroups() {
		return entityManager().createQuery("SELECT o FROM UserSettingGroup o", UserSettingGroup.class).getResultList();
	}

	public static UserSettingGroup findUserSettingGroup(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(UserSettingGroup.class, id);
	}

	public static List<UserSettingGroup> findUserSettingGroupEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM UserSettingGroup o", UserSettingGroup.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
			UserSettingGroup attached = UserSettingGroup.findUserSettingGroup(this.id);
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
	public UserSettingGroup merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UserSettingGroup merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static UserSettingGroup fromJsonToUserSettingGroup(String json) {
		return new JSONDeserializer<UserSettingGroup>().use(null, UserSettingGroup.class).deserialize(json);
	}

	public static String toJsonArray(Collection<UserSettingGroup> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<UserSettingGroup> fromJsonArrayToUserSettingGroups(String json) {
		return new JSONDeserializer<List<UserSettingGroup>>().use(null, ArrayList.class)
				.use("values", UserSettingGroup.class).deserialize(json);
	}

	@OneToMany(mappedBy = "userSettingGroupId")
	private Set<UserSetting> userSettings;

	@Column(name = "name", columnDefinition = "varchar", length = 100)
	@NotNull
	private String name;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	public Set<UserSetting> getUserSettings() {
		return userSettings;
	}

	public void setUserSettings(Set<UserSetting> userSettings) {
		this.userSettings = userSettings;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "UserSettingGroup_solrsummary_t:" + queryString;
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

	public static void indexUserSettingGroup(UserSettingGroup userSettingGroup) {
		List<UserSettingGroup> usersettinggroups = new ArrayList<UserSettingGroup>();
		usersettinggroups.add(userSettingGroup);
		indexUserSettingGroups(usersettinggroups);
	}

	@Async
	public static void indexUserSettingGroups(Collection<UserSettingGroup> usersettinggroups) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (UserSettingGroup userSettingGroup : usersettinggroups) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "usersettinggroup_" + userSettingGroup.getId());
			sid.addField("userSettingGroup.name_s", userSettingGroup.getName());
			sid.addField("userSettingGroup.description_s", userSettingGroup.getDescription());
			sid.addField("userSettingGroup.id_i", userSettingGroup.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("usersettinggroup_solrsummary_t", new StringBuilder().append(userSettingGroup.getName())
					.append(" ").append(userSettingGroup.getDescription()).append(" ").append(userSettingGroup.getId()));
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
	public static void deleteIndex(UserSettingGroup userSettingGroup) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("usersettinggroup_" + userSettingGroup.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexUserSettingGroup(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new UserSettingGroup().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}
}
