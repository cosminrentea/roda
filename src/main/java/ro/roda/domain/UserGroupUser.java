package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
@Table(schema = "public", name = "user_group_user")
@Configurable
@Audited
public class UserGroupUser {

	public static long countUserGroupUsers() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UserGroupUser o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(UserGroupUser userGroupUser) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("personphone_" + userGroupUser.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new UserGroupUser().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<UserGroupUser> findAllUserGroupUsers() {
		return entityManager().createQuery("SELECT o FROM UserGroupUser o", UserGroupUser.class).getResultList();
	}

	public static UserGroupUser findUserGroupUser(UserGroupUserPK id) {
		if (id == null)
			return null;
		return entityManager().find(UserGroupUser.class, id);
	}

	public static List<UserGroupUser> findUserGroupUserEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM UserGroupUser o", UserGroupUser.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<UserGroupUser> fromJsonArrayToUserGroupUsers(String json) {
		return new JSONDeserializer<List<UserGroupUser>>().use(null, ArrayList.class)
				.use("values", UserGroupUser.class).deserialize(json);
	}

	public static UserGroupUser fromJsonToUserGroupUser(String json) {
		return new JSONDeserializer<UserGroupUser>().use(null, UserGroupUser.class).deserialize(json);
	}

	public static void indexUserGroupUser(UserGroupUser userGroupUser) {
		List<UserGroupUser> userGroupUsers = new ArrayList<UserGroupUser>();
		userGroupUsers.add(userGroupUser);
		indexUserGroupUsers(userGroupUsers);
	}

	@Async
	public static void indexUserGroupUsers(Collection<UserGroupUser> userGroupUsers) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (UserGroupUser userGroupUser : userGroupUsers) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "usergroupuser_" + userGroupUser.getId());
			sid.addField("userGroupUser.userid_t", userGroupUser.getUserId());
			sid.addField("userGroupUser.usergroupid_t", userGroupUser.getUserGroupId());
			sid.addField("userGroupUser.id_t", userGroupUser.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"usergroupuser_solrsummary_t",
					new StringBuilder().append(userGroupUser.getUserId()).append(" ")
							.append(userGroupUser.getUserGroupId()).append(" ").append(userGroupUser.getId()));
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
		String searchString = "UserGroupUser_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new UserGroupUser().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<UserGroupUser> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@EmbeddedId
	private UserGroupUserPK id;

	@ManyToOne
	@JoinColumn(name = "user_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Users userId;

	@ManyToOne
	@JoinColumn(name = "user_group_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private UserGroup userGroupId;

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

	public UserGroupUserPK getId() {
		return this.id;
	}

	public Users getUserId() {
		return userId;
	}

	public UserGroup getUserGroupId() {
		return userGroupId;
	}

	@Transactional
	public UserGroupUser merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UserGroupUser merged = this.entityManager.merge(this);
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
			UserGroupUser attached = UserGroupUser.findUserGroupUser(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(UserGroupUserPK id) {
		this.id = id;
	}

	public void setUserId(Users userId) {
		this.userId = userId;
	}

	public void setUserGroupId(UserGroup userGroupId) {
		this.userGroupId = userGroupId;
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
		indexUserGroupUser(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
