package ro.roda.domain;

import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "user_auth_log")
@Configurable
@Audited
public class UserAuthLog {

	public static long countUserAuthLogs() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UserAuthLog o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(UserAuthLog userAuthLog) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("userauthlog_" + userAuthLog.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new UserAuthLog().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<UserAuthLog> findAllUserAuthLogs() {
		return entityManager().createQuery("SELECT o FROM UserAuthLog o", UserAuthLog.class).getResultList();
	}

	public static UserAuthLog findUserAuthLog(Long id) {
		if (id == null)
			return null;
		return entityManager().find(UserAuthLog.class, id);
	}

	public static List<UserAuthLog> findUserAuthLogEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM UserAuthLog o", UserAuthLog.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<UserAuthLog> fromJsonArrayToUserAuthLogs(String json) {
		return new JSONDeserializer<List<UserAuthLog>>().use(null, ArrayList.class).use("values", UserAuthLog.class)
				.deserialize(json);
	}

	public static UserAuthLog fromJsonToUserAuthLog(String json) {
		return new JSONDeserializer<UserAuthLog>().use(null, UserAuthLog.class).deserialize(json);
	}

	public static void indexUserAuthLog(UserAuthLog userAuthLog) {
		List<UserAuthLog> userauthlogs = new ArrayList<UserAuthLog>();
		userauthlogs.add(userAuthLog);
		indexUserAuthLogs(userauthlogs);
	}

	@Async
	public static void indexUserAuthLogs(Collection<UserAuthLog> userauthlogs) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (UserAuthLog userAuthLog : userauthlogs) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "userauthlog_" + userAuthLog.getId());
			sid.addField("userAuthLog.userid_t", userAuthLog.getUserId());
			sid.addField("userAuthLog.authattemptedat_dt", userAuthLog.getAuthAttemptedAt().getTime());
			sid.addField("userAuthLog.action_s", userAuthLog.getAction());
			sid.addField("userAuthLog.credentialprovider_s", userAuthLog.getCredentialProvider());
			sid.addField("userAuthLog.credentialidentifier_s", userAuthLog.getCredentialIdentifier());
			sid.addField("userAuthLog.errormessage_s", userAuthLog.getErrorMessage());
			sid.addField("userAuthLog.id_l", userAuthLog.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"userauthlog_solrsummary_t",
					new StringBuilder().append(userAuthLog.getUserId()).append(" ")
							.append(userAuthLog.getAuthAttemptedAt().getTime()).append(" ")
							.append(userAuthLog.getAction()).append(" ").append(userAuthLog.getCredentialProvider())
							.append(" ").append(userAuthLog.getCredentialIdentifier()).append(" ")
							.append(userAuthLog.getErrorMessage()).append(" ").append(userAuthLog.getId()));
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
		String searchString = "UserAuthLog_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new UserAuthLog().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<UserAuthLog> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>UserAuthLog</code> (incercare
	 * de autentificare) in baza de date; in caz afirmativ il returneaza,
	 * altfel, metoda il introduce in baza de date si apoi il returneaza.
	 * Verificarea existentei in baza de date se realizeaza fie dupa
	 * identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul incercarii de autentificare.
	 * @param userId
	 *            - utilizatorul care a realizat incercarea de autentificare.
	 * @param authAttemptedAt
	 *            - data incercarii de autentificare.
	 * @param action
	 *            - tipul de actiune realizata (login/logout/session expire).
	 * @param credentialIdentifier
	 *            - detalii despre incercarea de autentificare.
	 * @param credentialProvider
	 *            - tipul de furnizor de informatii pentru incercarea de
	 *            autentificare.
	 * @param errorMessage
	 *            - mesajul de eroare aparut in urma incercarii de
	 *            autentificare.
	 * @return
	 */
	public static UserAuthLog checkUserAuthLog(Long id, Users userId, Calendar authAttemptedAt, String action,
			String credentialIdentifier, String credentialProvider, String errorMessage) {
		UserAuthLog object;

		if (id != null) {
			object = findUserAuthLog(id);

			if (object != null) {
				return object;
			}
		}

		object = new UserAuthLog();
		object.userId = userId;
		object.authAttemptedAt = authAttemptedAt;
		object.action = action;
		object.credentialIdentifier = credentialIdentifier;
		object.credentialProvider = credentialProvider;
		object.errorMessage = errorMessage;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "action", columnDefinition = "varchar", length = 30)
	private String action;

	@Column(name = "auth_attempted_at", columnDefinition = "timestamp")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar authAttemptedAt;

	@Column(name = "credential_identifier", columnDefinition = "text")
	private String credentialIdentifier;

	@Column(name = "credential_provider", columnDefinition = "text")
	private String credentialProvider;

	@Column(name = "details", columnDefinition = "text")
	private String details;

	@Column(name = "error", columnDefinition = "text")
	private String error;

	@Column(name = "error_message", columnDefinition = "text")
	private String errorMessage;

	@Column(name = "error_details", columnDefinition = "text")
	private String errorDetails;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "bigserial")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Users userId;

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

	public String getAction() {
		return action;
	}

	public Calendar getAuthAttemptedAt() {
		return authAttemptedAt;
	}

	public String getCredentialIdentifier() {
		return credentialIdentifier;
	}

	public String getCredentialProvider() {
		return credentialProvider;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Long getId() {
		return this.id;
	}

	public Users getUserId() {
		return userId;
	}

	public String getDetails() {
		return details;
	}

	public String getError() {
		return error;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	@Transactional
	public UserAuthLog merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UserAuthLog merged = this.entityManager.merge(this);
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
			UserAuthLog attached = UserAuthLog.findUserAuthLog(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAction(String action) {
		this.action = action;
	}

	public void setAuthAttemptedAt(Calendar authAttemptedAt) {
		this.authAttemptedAt = authAttemptedAt;
	}

	public void setCredentialIdentifier(String credentialIdentifier) {
		this.credentialIdentifier = credentialIdentifier;
	}

	public void setCredentialProvider(String credentialProvider) {
		this.credentialProvider = credentialProvider;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUserId(Users userId) {
		this.userId = userId;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
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
		indexUserAuthLog(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return id != null && id.equals(((UserAuthLog) obj).id);
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
