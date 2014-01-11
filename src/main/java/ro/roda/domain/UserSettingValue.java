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
@Table(schema = "public", name = "user_setting_value")
@Configurable
@Audited
public class UserSettingValue {

	public static long countUserSettingValues() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UserSettingValue o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(UserSettingValue userSettingValue) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("usersettingvalue_" + userSettingValue.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new UserSettingValue().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<UserSettingValue> findAllUserSettingValues() {
		return entityManager().createQuery("SELECT o FROM UserSettingValue o", UserSettingValue.class).getResultList();
	}

	public static UserSettingValue findUserSettingValue(UserSettingValuePK id) {
		if (id == null)
			return null;
		return entityManager().find(UserSettingValue.class, id);
	}

	public static List<UserSettingValue> findUserSettingValueEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM UserSettingValue o", UserSettingValue.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<UserSettingValue> fromJsonArrayToUserSettingValues(String json) {
		return new JSONDeserializer<List<UserSettingValue>>().use(null, ArrayList.class)
				.use("values", UserSettingValue.class).deserialize(json);
	}

	public static UserSettingValue fromJsonToUserSettingValue(String json) {
		return new JSONDeserializer<UserSettingValue>().use(null, UserSettingValue.class).deserialize(json);
	}

	public static void indexUserSettingValue(UserSettingValue userSettingValue) {
		List<UserSettingValue> usersettingvalues = new ArrayList<UserSettingValue>();
		usersettingvalues.add(userSettingValue);
		indexUserSettingValues(usersettingvalues);
	}

	@Async
	public static void indexUserSettingValues(Collection<UserSettingValue> usersettingvalues) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (UserSettingValue userSettingValue : usersettingvalues) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "usersettingvalue_" + userSettingValue.getId());
			sid.addField("userSettingValue.usersettingid_t", userSettingValue.getUserSettingId());
			sid.addField("userSettingValue.userid_t", userSettingValue.getUserId());
			sid.addField("userSettingValue.value_s", userSettingValue.getValue());
			sid.addField("userSettingValue.id_t", userSettingValue.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"usersettingvalue_solrsummary_t",
					new StringBuilder().append(userSettingValue.getUserSettingId()).append(" ")
							.append(userSettingValue.getUserId()).append(" ").append(userSettingValue.getValue())
							.append(" ").append(userSettingValue.getId()));
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
		String searchString = "UserSettingValue_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new UserSettingValue().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<UserSettingValue> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@EmbeddedId
	private UserSettingValuePK id;

	@ManyToOne
	@JoinColumn(name = "user_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Users userId;

	@ManyToOne
	@JoinColumn(name = "user_setting_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private UserSetting userSettingId;

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

	public UserSettingValuePK getId() {
		return this.id;
	}

	public Users getUserId() {
		return userId;
	}

	public UserSetting getUserSettingId() {
		return userSettingId;
	}

	public String getValue() {
		return value;
	}

	@Transactional
	public UserSettingValue merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UserSettingValue merged = this.entityManager.merge(this);
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
			UserSettingValue attached = UserSettingValue.findUserSettingValue(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(UserSettingValuePK id) {
		this.id = id;
	}

	public void setUserId(Users userId) {
		this.userId = userId;
	}

	public void setUserSettingId(UserSetting userSettingId) {
		this.userSettingId = userSettingId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexUserSettingValue(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
