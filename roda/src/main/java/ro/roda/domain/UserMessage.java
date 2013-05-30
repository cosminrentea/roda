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
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "user_message")

public class UserMessage {

	public static long countUserMessages() {
		return entityManager().createQuery("SELECT COUNT(o) FROM UserMessage o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(UserMessage userMessage) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("usermessage_" + userMessage.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new UserMessage().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<UserMessage> findAllUserMessages() {
		return entityManager().createQuery("SELECT o FROM UserMessage o", UserMessage.class).getResultList();
	}

	public static UserMessage findUserMessage(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(UserMessage.class, id);
	}

	public static List<UserMessage> findUserMessageEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM UserMessage o", UserMessage.class)
				.setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static Collection<UserMessage> fromJsonArrayToUserMessages(String json) {
		return new JSONDeserializer<List<UserMessage>>().use(null, ArrayList.class).use("values", UserMessage.class)
				.deserialize(json);
	}

	public static UserMessage fromJsonToUserMessage(String json) {
		return new JSONDeserializer<UserMessage>().use(null, UserMessage.class).deserialize(json);
	}

	public static void indexUserMessage(UserMessage userMessage) {
		List<UserMessage> usermessages = new ArrayList<UserMessage>();
		usermessages.add(userMessage);
		indexUserMessages(usermessages);
	}

	@Async
	public static void indexUserMessages(Collection<UserMessage> usermessages) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (UserMessage userMessage : usermessages) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "usermessage_" + userMessage.getId());
			sid.addField("userMessage.touserid_t", userMessage.getToUserId());
			sid.addField("userMessage.fromuserid_t", userMessage.getFromUserId());
			sid.addField("userMessage.message_s", userMessage.getMessage());
			sid.addField("userMessage.id_i", userMessage.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("usermessage_solrsummary_t", new StringBuilder().append(userMessage.getToUserId()).append(" ")
					.append(userMessage.getFromUserId()).append(" ").append(userMessage.getMessage()).append(" ")
					.append(userMessage.getId()));
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
		String searchString = "UserMessage_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new UserMessage().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<UserMessage> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@ManyToOne
	@JoinColumn(name = "from_user_id", referencedColumnName = "id", nullable = false)
	private Users fromUserId;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@Column(name = "message", columnDefinition = "text")
	@NotNull
	private String message;

	@ManyToOne
	@JoinColumn(name = "to_user_id", referencedColumnName = "id", nullable = false)
	private Users toUserId;

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

	public Users getFromUserId() {
		return fromUserId;
	}

	public Integer getId() {
		return this.id;
	}

	public String getMessage() {
		return message;
	}

	public Users getToUserId() {
		return toUserId;
	}

	@Transactional
	public UserMessage merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		UserMessage merged = this.entityManager.merge(this);
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
			UserMessage attached = UserMessage.findUserMessage(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setFromUserId(Users fromUserId) {
		this.fromUserId = fromUserId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setToUserId(Users toUserId) {
		this.toUserId = toUserId;
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
		indexUserMessage(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}
}
