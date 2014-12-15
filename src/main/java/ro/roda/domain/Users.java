package ro.roda.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSON;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "users")
@Configurable
@Audited
public class Users implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Users findOrCreate(Integer userId, String username, String password, Boolean enabled) {
		Users user = null;

		if (userId != null) {
			user = findUsers(userId);
			if (user != null) {
				return user;
			}
		}

		if (username != null) {
			TypedQuery<Users> query = entityManager().createQuery("SELECT o FROM Users o WHERE o.username = :username",
					Users.class);
			query.setParameter("username", username);

			List<Users> queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		// no User was found, so create it
		user = new Users();
		user.setUsername(username);
		user.setPassword(password);
		user.setEnabled(enabled);
		user.persist();

		return user;
	}

	public static Users create(String username2, String password2, Boolean enabled2, String email2, String firstname2,
			String middlename2, String lastname2, String title2, String sex2, String salutation2, Date birthdate2,
			String address1p, String address2p) {

		Users user = null;
		try {
			user = new Users();

			user.setUsername(username2);
			user.setPassword(password2);
			user.setEnabled(enabled2);
			user.setEmail(email2);
			user.setFirstname(firstname2);
			user.setMiddlename(middlename2);
			user.setLastname(lastname2);
			user.setTitle(title2);
			user.setSex(sex2);
			user.setSalutation(salutation2);
			user.setBirthdate(birthdate2);
			user.setAddress1(address1p);
			user.setAddress2(address2p);

			user.persist();

		} catch (Exception e) {
			user = null;
		}

		return user;
	}

	// used only for updating regular fields,
	// not fields used by Spring Security:
	// username, password, enabled
	public static Users save(Integer userId, String username2, String email2, String firstname2, String middlename2,
			String lastname2, String title2, String sex2, String salutation2, Date birthdate2, String address1p,
			String address2p) {

		Users user = null;

		// find User by ID, or by username
		if (userId != null) {
			user = findUsers(userId);
		} else {
			if (username2 != null) {
				TypedQuery<Users> query = entityManager().createQuery(
						"SELECT o FROM Users o WHERE o.username = :username", Users.class);
				query.setParameter("username", username2);
				List<Users> queryResult = query.getResultList();
				if (queryResult.size() > 0) {
					user = queryResult.get(0);
				}
			}
		}

		// if a User was found,
		// update the "user profile" (non-Spring-Security fields)
		// but only when given non-null parameters

		if (user != null) {

			if (email2 != null) {
				user.setEmail(email2);
			}
			if (firstname2 != null) {
				user.setFirstname(firstname2);
			}
			if (middlename2 != null) {
				user.setMiddlename(middlename2);
			}
			if (lastname2 != null) {
				user.setLastname(lastname2);
			}
			if (title2 != null) {
				user.setTitle(title2);
			}
			if (sex2 != null) {
				user.setSex(sex2);
			}
			if (salutation2 != null) {
				user.setSalutation(salutation2);
			}
			if (birthdate2 != null) {
				user.setBirthdate(birthdate2);
			}
			if (address1p != null) {
				user.setAddress1(address1p);
			}
			if (address2p != null) {
				user.setAddress2(address2p);
			}

			user.merge();
		}
		return user;
	}

	public static long countUserses() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Users o", Long.class).getSingleResult();
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

	public static final EntityManager entityManager() {
		EntityManager em = new Users().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
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

	public static TypedQuery<Users> findUsersByUsernameLikeAndEnabled(String username, boolean enabled) {
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

	public static TypedQuery<Users> findUsersByUsernameAndEnabled(String username, boolean enabled) {
		if (username == null || username.length() == 0)
			throw new IllegalArgumentException("The username argument is required");
		EntityManager em = Users.entityManager();
		TypedQuery<Users> q = em.createQuery(
				"SELECT o FROM Users AS o WHERE LOWER(o.username) = LOWER(:username)  AND o.enabled = :enabled",
				Users.class);
		q.setParameter("username", username);
		q.setParameter("enabled", enabled);
		return q;
	}

	public static TypedQuery<Users> findUsersByUsernameLike(String username) {
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

	public static Collection<Users> fromJsonArrayToUserses(String json) {
		return new JSONDeserializer<List<Users>>().use(null, ArrayList.class).use("values", Users.class)
				.deserialize(json);
	}

	public static Users fromJsonToUsers(String json) {
		return new JSONDeserializer<Users>().use(null, Users.class).deserialize(json);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
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

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static QueryResponse search(String queryString) {
		String searchString = "Users_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Users().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Users> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "username", columnDefinition = "varchar", length = 64, unique = true)
	@NotNull
	private String username;

	@Column(name = "password", columnDefinition = "varchar", length = 64)
	@NotNull
	@JSON(include = false)
	@JsonIgnore
	private String password;

	@Column(name = "enabled", columnDefinition = "bool")
	@NotNull
	private boolean enabled;

	@Column(name = "email", columnDefinition = "varchar", length = 200)
	private String email;

	@Column(name = "salutation", columnDefinition = "varchar", length = 100)
	private String salutation;

	@Column(name = "firstname", columnDefinition = "varchar", length = 100)
	private String firstname;

	@Column(name = "lastname", columnDefinition = "varchar", length = 100)
	private String lastname;

	@Column(name = "middlename", columnDefinition = "varchar", length = 100)
	private String middlename;

	@Column(name = "title", columnDefinition = "varchar", length = 100)
	private String title;

	@Column(name = "image", columnDefinition = "varchar", length = 100)
	private String image;

	@Column(name = "address1", columnDefinition = "varchar", length = 200)
	private String address1;

	@Column(name = "address2", columnDefinition = "varchar", length = 200)
	private String address2;

	@Column(name = "sex", columnDefinition = "char", length = 1)
	private String sex;

	@Column(name = "city", columnDefinition = "varchar", length = 100)
	private String city;

	@Column(name = "country", columnDefinition = "varchar", length = 100)
	private String country;

	@Column(name = "phone", columnDefinition = "varchar", length = 30)
	private String phone;

	@Column(name = "birthdate", columnDefinition = "date")
	private Date birthdate;

	@OneToMany(mappedBy = "username", cascade = CascadeType.ALL)
	private Set<Authorities> authorities;

	@OneToMany(mappedBy = "owner")
	private Set<Catalog> catalogs;

	@OneToMany(mappedBy = "addedBy")
	private Set<Instance> instances;

	@OneToMany(mappedBy = "userId")
	private Set<PersonLinks> personLinkss;

	@OneToMany(mappedBy = "userId")
	private Set<StudySaved> studiesSaved;

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

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((Users) obj).id))
				|| (username != null && username.equalsIgnoreCase(((Users) obj).username));
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	@JsonIgnore
	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	public Set<Authorities> getAuthorities() {
		return authorities;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public Set<Catalog> getCatalogs() {
		return catalogs;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstname() {
		return firstname;
	}

	public Integer getId() {
		return this.id;
	}

	public String getImage() {
		return image;
	}

	public Set<Instance> getInstances() {
		return instances;
	}

	public String getLastname() {
		return lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public String getPassword() {
		return password;
	}

	public Set<PersonLinks> getPersonLinkss() {
		return personLinkss;
	}

	public Set<PersonLinks> getPersonLinkss1() {
		return personLinkss1;
	}

	public String getPhone() {
		return phone;
	}

	public String getSalutation() {
		return salutation;
	}

	public String getSex() {
		return sex;
	}

	public Set<Study> getStudies() {
		return studies;
	}

	// @OneToMany(mappedBy = "userId")
	// private Set<UserGroupUser> userGroupUsers;

	public Set<StudySaved> getStudiesSaved() {
		return studiesSaved;
	}

	public Set<StudyKeyword> getStudyKeywords() {
		return studyKeywords;
	}

	public String getTitle() {
		return title;
	}

	public Set<UserAuthLog> getUserAuthLogs() {
		return userAuthLogs;
	}

	public Set<UserMessage> getUserMessages() {
		return userMessages;
	}

	public Set<UserMessage> getUserMessages1() {
		return userMessages1;
	}

	public String getUsername() {
		return username;
	}

	public Set<UserSettingValue> getUserSettingValues() {
		return userSettingValues;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Transactional
	public Users merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Users merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
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

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setAuthorities(Set<Authorities> authorities) {
		this.authorities = authorities;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setCatalogs(Set<Catalog> catalogs) {
		this.catalogs = catalogs;
	}

	// public Set<UserGroupUser> getUserGroupUsers() {
	// return userGroupUsers;
	// }

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setInstances(Set<Instance> instances) {
		this.instances = instances;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	/**
	 * Encode first SHA-256 (!) and then set the provided password (which should
	 * be plaintext).
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = new ShaPasswordEncoder(256).encodePassword(password, null);
	}

	public void setPersonLinkss(Set<PersonLinks> personLinkss) {
		this.personLinkss = personLinkss;
	}

	public void setPersonLinkss1(Set<PersonLinks> personLinkss1) {
		this.personLinkss1 = personLinkss1;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setStudies(Set<Study> studies) {
		this.studies = studies;
	}

	public void setStudiesSaved(Set<StudySaved> studiesSaved) {
		this.studiesSaved = studiesSaved;
	}

	public void setStudyKeywords(Set<StudyKeyword> studyKeywords) {
		this.studyKeywords = studyKeywords;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUserAuthLogs(Set<UserAuthLog> userAuthLogs) {
		this.userAuthLogs = userAuthLogs;
	}

	// public void setUserGroupUsers(Set<UserGroupUser> userGroupUsers) {
	// this.userGroupUsers = userGroupUsers;
	// }

	public void setUserMessages(Set<UserMessage> userMessages) {
		this.userMessages = userMessages;
	}

	// public String toString() {
	// return ReflectionToStringBuilder.toString(this,
	// ToStringStyle.SHORT_PREFIX_STYLE);
	// }

	public void setUserMessages1(Set<UserMessage> userMessages1) {
		this.userMessages1 = userMessages1;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUserSettingValues(Set<UserSettingValue> userSettingValues) {
		this.userSettingValues = userSettingValues;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}
}
