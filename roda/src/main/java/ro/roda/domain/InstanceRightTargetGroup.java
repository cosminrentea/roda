package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
@Table(schema = "public", name = "instance_right_target_group")
@Configurable
public class InstanceRightTargetGroup {

	@ManyToOne
	@JoinColumn(name = "instance_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private Instance instanceId;

	@ManyToOne
	@JoinColumn(name = "instance_right_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private InstanceRight instanceRightId;

	@ManyToOne
	@JoinColumn(name = "instance_right_value_id", referencedColumnName = "id", nullable = false)
	private InstanceRightValue instanceRightValueId;

	@ManyToOne
	@JoinColumn(name = "target_group_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
	private TargetGroup targetGroupId;

	public Instance getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Instance instanceId) {
		this.instanceId = instanceId;
	}

	public InstanceRight getInstanceRightId() {
		return instanceRightId;
	}

	public void setInstanceRightId(InstanceRight instanceRightId) {
		this.instanceRightId = instanceRightId;
	}

	public InstanceRightValue getInstanceRightValueId() {
		return instanceRightValueId;
	}

	public void setInstanceRightValueId(InstanceRightValue instanceRightValueId) {
		this.instanceRightValueId = instanceRightValueId;
	}

	public TargetGroup getTargetGroupId() {
		return targetGroupId;
	}

	public void setTargetGroupId(TargetGroup targetGroupId) {
		this.targetGroupId = targetGroupId;
	}

	@EmbeddedId
	private InstanceRightTargetGroupPK id;

	public InstanceRightTargetGroupPK getId() {
		return this.id;
	}

	public void setId(InstanceRightTargetGroupPK id) {
		this.id = id;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "InstanceRightTargetGroup_solrsummary_t:" + queryString;
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

	public static void indexInstanceRightTargetGroup(InstanceRightTargetGroup instanceRightTargetGroup) {
		List<InstanceRightTargetGroup> instancerighttargetgroups = new ArrayList<InstanceRightTargetGroup>();
		instancerighttargetgroups.add(instanceRightTargetGroup);
		indexInstanceRightTargetGroups(instancerighttargetgroups);
	}

	@Async
	public static void indexInstanceRightTargetGroups(Collection<InstanceRightTargetGroup> instancerighttargetgroups) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (InstanceRightTargetGroup instanceRightTargetGroup : instancerighttargetgroups) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "instancerighttargetgroup_" + instanceRightTargetGroup.getId());
			sid.addField("instanceRightTargetGroup.instanceid_t", instanceRightTargetGroup.getInstanceId());
			sid.addField("instanceRightTargetGroup.instancerightid_t", instanceRightTargetGroup.getInstanceRightId());
			sid.addField("instanceRightTargetGroup.instancerightvalueid_t",
					instanceRightTargetGroup.getInstanceRightValueId());
			sid.addField("instanceRightTargetGroup.targetgroupid_t", instanceRightTargetGroup.getTargetGroupId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"instancerighttargetgroup_solrsummary_t",
					new StringBuilder().append(instanceRightTargetGroup.getInstanceId()).append(" ")
							.append(instanceRightTargetGroup.getInstanceRightId()).append(" ")
							.append(instanceRightTargetGroup.getInstanceRightValueId()).append(" ")
							.append(instanceRightTargetGroup.getTargetGroupId()));
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
	public static void deleteIndex(InstanceRightTargetGroup instanceRightTargetGroup) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("instancerighttargetgroup_" + instanceRightTargetGroup.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexInstanceRightTargetGroup(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new InstanceRightTargetGroup().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new InstanceRightTargetGroup().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countInstanceRightTargetGroups() {
		return entityManager().createQuery("SELECT COUNT(o) FROM InstanceRightTargetGroup o", Long.class)
				.getSingleResult();
	}

	public static List<InstanceRightTargetGroup> findAllInstanceRightTargetGroups() {
		return entityManager().createQuery("SELECT o FROM InstanceRightTargetGroup o", InstanceRightTargetGroup.class)
				.getResultList();
	}

	public static InstanceRightTargetGroup findInstanceRightTargetGroup(InstanceRightTargetGroupPK id) {
		if (id == null)
			return null;
		return entityManager().find(InstanceRightTargetGroup.class, id);
	}

	public static List<InstanceRightTargetGroup> findInstanceRightTargetGroupEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM InstanceRightTargetGroup o", InstanceRightTargetGroup.class)
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
			InstanceRightTargetGroup attached = InstanceRightTargetGroup.findInstanceRightTargetGroup(this.id);
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
	public InstanceRightTargetGroup merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		InstanceRightTargetGroup merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static InstanceRightTargetGroup fromJsonToInstanceRightTargetGroup(String json) {
		return new JSONDeserializer<InstanceRightTargetGroup>().use(null, InstanceRightTargetGroup.class).deserialize(
				json);
	}

	public static String toJsonArray(Collection<InstanceRightTargetGroup> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<InstanceRightTargetGroup> fromJsonArrayToInstanceRightTargetGroups(String json) {
		return new JSONDeserializer<List<InstanceRightTargetGroup>>().use(null, ArrayList.class)
				.use("values", InstanceRightTargetGroup.class).deserialize(json);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
