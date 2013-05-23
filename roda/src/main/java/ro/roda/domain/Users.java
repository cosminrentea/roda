package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.io.Serializable;
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
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public", name = "users")
@Configurable
public class Users implements Serializable {

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static Users fromJsonToUsers(String json) {
		return new JSONDeserializer<Users>().use(null, Users.class).deserialize(json);
	}

	public static String toJsonArray(Collection<Users> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<Users> fromJsonArrayToUserses(String json) {
		return new JSONDeserializer<List<Users>>().use(null, ArrayList.class).use("values", Users.class)
				.deserialize(json);
	}

	@OneToMany(mappedBy = "username")
	private Set<Authorities> authoritieses;

	@OneToMany(mappedBy = "owner")
	private Set<Catalog> catalogs;

	@OneToMany(mappedBy = "addedBy")
	private Set<Instance> instances;

	@OneToMany(mappedBy = "userId")
	private Set<PersonLinks> personLinkss;

	@OneToMany(mappedBy = "statusBy")
	private Set<PersonLinks> personLinkss1;

	@OneToMany(mappedBy = "addedBy")
	private Set<Study> studies;

	@OneToMany(mappedBy = "addedBy")
	private Set<StudyKeyword> studyKeywords;

	@OneToMany(mappedBy = "userId")
	private Set<UserAuthLog> userAuthLogs;

	@OneToMany(mappedBy = "toUserId")
	private Set<UserMessage> userMessages;

	@OneToMany(mappedBy = "fromUserId")
	private Set<UserMessage> userMessages1;

	@OneToMany(mappedBy = "userId")
	private Set<UserSettingValue> userSettingValues;

	@Column(name = "username", columnDefinition = "varchar", length = 64, unique = true)
	@NotNull
	private String username;

	@Column(name = "password", columnDefinition = "varchar", length = 64)
	@NotNull
	private String password;

	@Column(name = "enabled", columnDefinition = "bool")
	@NotNull
	private boolean enabled;

	public Set<Authorities> getAuthoritieses() {
		return authoritieses;
	}

	public void setAuthoritieses(Set<Authorities> authoritieses) {
		this.authoritieses = authoritieses;
	}

	public Set<Catalog> getCatalogs() {
		return catalogs;
	}

	public void setCatalogs(Set<Catalog> catalogs) {
		this.catalogs = catalogs;
	}

	public Set<Instance> getInstances() {
		return instances;
	}

	public void setInstances(Set<Instance> instances) {
		this.instances = instances;
	}

	public Set<PersonLinks> getPersonLinkss() {
		return personLinkss;
	}

	public void setPersonLinkss(Set<PersonLinks> personLinkss) {
		this.personLinkss = personLinkss;
	}

	public Set<PersonLinks> getPersonLinkss1() {
		return personLinkss1;
	}

	public void setPersonLinkss1(Set<PersonLinks> personLinkss1) {
		this.personLinkss1 = personLinkss1;
	}

	public Set<Study> getStudies() {
		return studies;
	}

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
	}

	public Set<StudyKeyword> getStudyKeywords() {
		return studyKeywords;
	}

	public void setStudyKeywords(Set<StudyKeyword> studyKeywords) {
		this.studyKeywords = studyKeywords;
	}

	public Set<UserAuthLog> getUserAuthLogs() {
		return userAuthLogs;
	}

	public void setUserAuthLogs(Set<UserAuthLog> userAuthLogs) {
		this.userAuthLogs = userAuthLogs;
	}

	public Set<UserMessage> getUserMessages() {
		return userMessages;
	}

	public void setUserMessages(Set<UserMessage> userMessages) {
		this.userMessages = userMessages;
	}

	public Set<UserMessage> getUserMessages1() {
		return userMessages1;
	}

	public void setUserMessages1(Set<UserMessage> userMessages1) {
		this.userMessages1 = userMessages1;
	}

	public Set<UserSettingValue> getUserSettingValues() {
		return userSettingValues;
	}

	public void setUserSettingValues(Set<UserSettingValue> userSettingValues) {
		this.userSettingValues = userSettingValues;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "Users_solrsummary_t:" + queryString;
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

	public static void indexUsers(Users users) {
		List<Users> userses = new ArrayList<Users>();
		userses.add(users);
		indexUserses(userses);
	}

	@Async
	public static void indexUserses(Collection<Users> userses) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Users users : userses) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "users_" + users.getId());
			sid.addField("users.username_s", users.getUsername());
			sid.addField("users.password_s", users.getPassword());
			sid.addField("users.id_i", users.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("users_solrsummary_t",
					new StringBuilder().append(users.getUsername()).append(" ").append(users.getPassword()).append(" ")
							.append(users.getId()));
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
	public static void deleteIndex(Users users) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("users_" + users.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexUsers(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Users().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

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

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new Users().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countUserses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Users o", Long.class).getSingleResult();
	}

	public static List<Users> findAllUserses() {
		return entityManager().createQuery("SELECT o FROM Users o", Users.class).getResultList();
	}

	public static Users findUsers(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Users.class, id);
	}

	public static List<Users> findUsersEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Users o", Users.class).setFirstResult(firstResult)
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
			Users attached = Users.findUsers(this.id);
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
	public Users merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Users merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public static TypedQuery<Users> findUsersesByUsernameLike(String username) {
		if (username == null || username.length() == 0)
			throw new IllegalArgumentException("The username argument is required");
		username = username.replace('*', '%');
		if (username.charAt(0) != '%') {
			username = "%" + username;
		}
		if (username.charAt(username.length() - 1) != '%') {
			username = username + "%";
		}
		EntityManager em = Users.entityManager();
		TypedQuery<Users> q = em.createQuery("SELECT o FROM Users AS o WHERE LOWER(o.username) LIKE LOWER(:username)",
				Users.class);
		q.setParameter("username", username);
		return q;
	}

	public static TypedQuery<Users> findUsersesByUsernameLikeAndEnabled(String username, boolean enabled) {
		if (username == null || username.length() == 0)
			throw new IllegalArgumentException("The username argument is required");
		username = username.replace('*', '%');
		if (username.charAt(0) != '%') {
			username = "%" + username;
		}
		if (username.charAt(username.length() - 1) != '%') {
			username = username + "%";
		}
		EntityManager em = Users.entityManager();
		TypedQuery<Users> q = em.createQuery(
				"SELECT o FROM Users AS o WHERE LOWER(o.username) LIKE LOWER(:username)  AND o.enabled = :enabled",
				Users.class);
		q.setParameter("username", username);
		q.setParameter("enabled", enabled);
		return q;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	private static final long serialVersionUID = 1L;
}
