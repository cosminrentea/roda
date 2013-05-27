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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
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

@Entity
@Table(schema = "public", name = "file")
@Configurable
@Audited
public class File {

	@Transient
	private byte[] content;

	@Transient
	private String url;

	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Autowired
	transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
		String searchString = "File_solrsummary_t:" + queryString;
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

	public static void indexFile(File file) {
		List<File> files = new ArrayList<File>();
		files.add(file);
		indexFiles(files);
	}

	@Async
	public static void indexFiles(Collection<File> files) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (File file : files) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "file_" + file.getId());
			sid.addField("file.content_t", file.getContent());
			sid.addField("file.url_s", file.getUrl());
			sid.addField("file.id_i", file.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"file_solrsummary_t",
					new StringBuilder().append(file.getContent()).append(" ").append(file.getUrl()).append(" ")
							.append(file.getId()));
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
	public static void deleteIndex(File file) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("file_" + file.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexFile(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new File().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new File().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countFiles() {
		return entityManager().createQuery("SELECT COUNT(o) FROM File o", Long.class).getSingleResult();
	}

	public static List<File> findAllFiles() {
		return entityManager().createQuery("SELECT o FROM File o", File.class).getResultList();
	}

	public static File findFile(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(File.class, id);
	}

	public static List<File> findFileEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM File o", File.class).setFirstResult(firstResult)
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
			File attached = File.findFile(this.id);
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
	public File merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		File merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@ManyToMany
	@JoinTable(name = "instance_documents", joinColumns = { @JoinColumn(name = "document_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "instance_id", nullable = false) })
	private Set<Instance> instances;

	@ManyToMany
	@JoinTable(name = "study_file", joinColumns = { @JoinColumn(name = "file_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "study_id", nullable = false) })
	private Set<Study> studies1;

	@OneToMany(mappedBy = "responseCardFileId")
	private Set<SelectionVariableItem> selectionVariableItems;

	@OneToMany(mappedBy = "fileId")
	private Set<Variable> variables;

	@Column(name = "title", columnDefinition = "text")
	private String title;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@Column(name = "size", columnDefinition = "int8")
	private Long size;

	@Column(name = "full_path", columnDefinition = "text")
	private String fullPath;

	@Column(name = "content_type", columnDefinition = "varchar", length = 100)
	private String contentType;

	public Set<Instance> getInstances() {
		return instances;
	}

	public void setInstances(Set<Instance> instances) {
		this.instances = instances;
	}

	public Set<Study> getStudies1() {
		return studies1;
	}

	public void setStudies1(Set<Study> studies1) {
		this.studies1 = studies1;
	}

	public Set<SelectionVariableItem> getSelectionVariableItems() {
		return selectionVariableItems;
	}

	public void setSelectionVariableItems(Set<SelectionVariableItem> selectionVariableItems) {
		this.selectionVariableItems = selectionVariableItems;
	}

	public Set<Variable> getVariables() {
		return variables;
	}

	public void setVariables(Set<Variable> variables) {
		this.variables = variables;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFullPath() {
		return fullPath;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
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

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public static File fromJsonToFile(String json) {
		return new JSONDeserializer<File>().use(null, File.class).deserialize(json);
	}

	public static String toJsonArray(Collection<File> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	public static Collection<File> fromJsonArrayToFiles(String json) {
		return new JSONDeserializer<List<File>>().use(null, ArrayList.class).use("values", File.class)
				.deserialize(json);
	}
}
