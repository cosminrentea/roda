package ro.roda.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

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

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "user_profile")
@Configurable
@Audited
public class UserProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	public static long countUserProfiles() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UserProfile o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(UserProfile userProfile) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("userProfile_" + userProfile.getUserId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new UserProfile().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<UserProfile> findAllUserProfiles() {
		return entityManager().createQuery("SELECT o FROM UserProfile o", UserProfile.class).getResultList();
	}

	public static UserProfile findUserProfile(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(UserProfile.class, id);
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
		TypedQuery<Users> q = em.createQuery(
				"SELECT o FROM UserProfile AS o WHERE LOWER(o.username) LIKE LOWER(:username)", Users.class);
		q.setParameter("username", username);
		return q;
	}

	public static List<UserProfile> findUserPprofileEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM UserProfile o", UserProfile.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<UserProfile> fromJsonArrayToUserProfiles(String json) {
		return new JSONDeserializer<List<UserProfile>>().use(null, ArrayList.class).use("values", UserProfile.class)
				.deserialize(json);
	}

	public static UserProfile fromJsonToUserProfile(String json) {
		return new JSONDeserializer<UserProfile>().use(null, UserProfile.class).deserialize(json);
	}

	public static void indexUserProfile(UserProfile userProfile) {
		List<UserProfile> userProfiles = new ArrayList<UserProfile>();
		userProfiles.add(userProfile);
		indexUserProfiles(userProfiles);
	}

	@Async
	public static void indexUserProfiles(Collection<UserProfile> userProfiles) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (UserProfile userProfile : userProfiles) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "userProfile_" + userProfile.getUserId());
			sid.addField("userProfile.lastname_s", userProfile.getLastname());
			sid.addField("userProfile.firstname_s", userProfile.getFirstname());
			sid.addField("userProfile.id_i", userProfile.getUserId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("userProfile_solrsummary_t", new StringBuilder().append(userProfile.getLastname()).append(" ")
					.append(userProfile.getFirstname()).append(" ").append(userProfile.getUserId()));
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
		SolrServer _solrServer = new UserProfile().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<UserProfile> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>UserProfile</code> (profil
	 * utilizator) in baza de date; in caz afirmativ il returneaza, altfel,
	 * metoda il introduce in baza de date si apoi il returneaza. Verificarea
	 * existentei in baza de date se realizeaza fie dupa identificator, fie dupa
	 * un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>username
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul utilizatorului.
	 * @param username
	 *            - numele utilizatorului.
	 * @param password
	 *            - parola utilizatorului.
	 * @param enabled
	 *            - specifica daca utilizatorul este activ.
	 * @return
	 */
	public static UserProfile checkUserProfile(Integer id, Integer userId, String firstname, String middlename,
			String lastname, String title, String sex, String salutation, Date birthdate, String address1,
			String address2) {
		UserProfile object;

		if (id != null) {
			object = findUserProfile(id);

			if (object != null) {
				return object;
			}
		}

		List<UserProfile> queryResult;

		if (userId != null) {
			TypedQuery<UserProfile> query = entityManager().createQuery(
					"SELECT o FROM UserProfile o WHERE o.user_id = :userId", UserProfile.class);
			query.setParameter("userId", userId);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new UserProfile();
		object.userId = Users.findUsers(userId).getId();
		object.firstname = firstname;
		object.middlename = middlename;
		object.lastname = lastname;
		object.title = title;
		object.sex = sex;
		object.salutation = salutation;
		object.birthdate = birthdate;
		object.address1 = address1;
		object.address2 = address2;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	// @Column(name = "id")
	// private Integer id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
	private Users user;

	@Id
	@Column(name = "user_id", columnDefinition = "int4")
	private Integer userId;

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

	// public Integer getId() {
	// return this.id;
	// }

	public String getSalutation() {
		return salutation;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public String getTitle() {
		return title;
	}

	public String getAddress1() {
		return address1;
	}

	public String getAddress2() {
		return address2;
	}

	public String getSex() {
		return sex;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public Integer getUserId() {
		return userId;
	}

	public String getImage() {
		return image;
	}

	public Users getUser() {
		return user;
	}

	public String getPhone() {
		return phone;
	}

	@Transactional
	public UserProfile merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UserProfile merged = this.entityManager.merge(this);
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
			UserProfile attached = UserProfile.findUserProfile(this.userId);
			this.entityManager.remove(attached);
		}
	}

	// public void setId(Integer id) {
	// this.id = id;
	// }

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
		indexUserProfile(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (userId != null && userId.equals(((UserProfile) obj).userId))
				|| (userId != null && userId == (((UserProfile) obj).userId));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
