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
@Table(schema = "public", name = "user_group")
@Configurable
@Audited
public class UserGroup {

	public static long countUserGroups() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UserGroup o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(UserGroup userGroup) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("userGroup_" + userGroup.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new UserGroup().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<UserGroup> findAllUserGroups() {
		return entityManager().createQuery("SELECT o FROM UserGroup o", UserGroup.class).getResultList();
	}

	public static UserGroup findUserGroup(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(UserGroup.class, id);
	}

	public static List<UserGroup> findUserGroupEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM UserGroup o", UserGroup.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<UserGroup> fromJsonArrayToUserGroups(String json) {
		return new JSONDeserializer<List<UserGroup>>().use(null, ArrayList.class).use("values", UserGroup.class)
				.deserialize(json);
	}

	public static UserGroup fromJsonToUserGroup(String json) {
		return new JSONDeserializer<UserGroup>().use(null, UserGroup.class).deserialize(json);
	}

	public static void indexUserGroup(UserGroup userGroup) {
		List<UserGroup> userGroups = new ArrayList<UserGroup>();
		userGroups.add(userGroup);
		indexUserGroups(userGroups);
	}

	@Async
	public static void indexUserGroups(Collection<UserGroup> userGroups) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (UserGroup userGroup : userGroups) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "usergroup_" + userGroup.getId());
			sid.addField("userGroup.name_s", userGroup.getGroupname());
			sid.addField("userGroup.description_s", userGroup.getDescription());
			sid.addField("userGroup.id_i", userGroup.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("userGroup_solrsummary_t",
					new StringBuilder().append(userGroup.getGroupname()).append(" ").append(userGroup.getDescription())
							.append(" ").append(userGroup.getId()));
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
		String searchString = "UserGroup_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new UserGroup().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<UserGroup> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>UserGroup</code> (grup de
	 * utilizatori) in baza de date; in caz afirmativ il returneaza, altfel,
	 * metoda il introduce in baza de date si apoi il returneaza. Verificarea
	 * existentei in baza de date se realizeaza fie dupa identificator, fie dupa
	 * un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>userGroup
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul grupului.
	 * @param userGroup
	 *            - numele grupului.
	 * @return
	 */
	public static UserGroup checkUserGroup(Integer id, String userGroup, String userGroupType, Boolean enabled) {
		UserGroup object;

		if (id != null) {
			object = findUserGroup(id);

			if (object != null) {
				return object;
			}
		}

		List<UserGroup> queryResult;

		if (userGroup != null) {
			TypedQuery<UserGroup> query = entityManager().createQuery(
					"SELECT o FROM UserGroup o WHERE lower(o.userGroup) = lower(:userGroup)", UserGroup.class);
			query.setParameter("userGroup", userGroup);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new UserGroup();
		object.groupname = userGroup;
		object.description = userGroupType;
		object.enabled = enabled;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@OneToMany(mappedBy = "userGroupId")
	private Set<UserGroupUser> userGroupUsers;

	@OneToMany(mappedBy = "groupname")
	private Set<Authorities> authorities;

	@Column(name = "groupname", columnDefinition = "varchar", length = 30, unique=true)
	@NotNull
	private String groupname;

	@Column(name = "description", columnDefinition = "varchar", length = 100)
	private String description;

	@Column(name = "enabled", columnDefinition = "bool")
	@NotNull
	private boolean enabled;

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

	public Integer getId() {
		return this.id;
	}

	public Set<UserGroupUser> getUserGroupUsers() {
		return userGroupUsers;
	}

	public String getGroupname() {
		return groupname;
	}

	public String getDescription() {
		return description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Transactional
	public UserGroup merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UserGroup merged = this.entityManager.merge(this);
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
			UserGroup attached = UserGroup.findUserGroup(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserGroupUsers(Set<UserGroupUser> userGroupUsers) {
		this.userGroupUsers = userGroupUsers;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
		indexUserGroup(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((UserGroup) obj).id))
				|| (groupname != null && groupname.equalsIgnoreCase(((UserGroup) obj).groupname));
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
