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
import javax.persistence.TypedQuery;
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
public class File {

	public static long countFiles() {
		return entityManager().createQuery("SELECT COUNT(o) FROM File o",
				Long.class).getSingleResult();
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

	public static final EntityManager entityManager() {
		EntityManager em = new File().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<File> findAllFiles() {
		return entityManager().createQuery("SELECT o FROM File o", File.class)
				.getResultList();
	}

	public static File findFile(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(File.class, id);
	}

	public static List<File> findFileEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM File o", File.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	public static Collection<File> fromJsonArrayToFiles(String json) {
		return new JSONDeserializer<List<File>>().use(null, ArrayList.class)
				.use("values", File.class).deserialize(json);
	}

	public static File fromJsonToFile(String json) {
		return new JSONDeserializer<File>().use(null, File.class).deserialize(
				json);
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
					new StringBuilder().append(file.getContent()).append(" ")
							.append(file.getUrl()).append(" ")
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

	public static QueryResponse search(SolrQuery query) {
		try {
			return solrServer().query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new QueryResponse();
	}

	public static QueryResponse search(String queryString) {
		String searchString = "File_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new File().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<File> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>File</code> (fisier) in baza
	 * de date; in caz afirmativ il returneaza, altfel, metoda il introduce in
	 * baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * <li>title
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul fisierului.
	 * @param title
	 *            - numele fisierului (nu cel de pe hard-disk).
	 * @param name
	 *            - numele fisierului (cel de pe hard-disk).
	 * @param description
	 *            - descrierea fisierului.
	 * @param size
	 *            - dimensiunea fisierului in octeti.
	 * @param fullPath
	 *            - calea completa a fisierului de pe hard-disk.
	 * @param contentType
	 * @return
	 */
	public static File checkFile(Integer id, String title, String name,
			String description, Long size, String fullPath, String contentType) {
		File object;

		if (id != null) {
			object = findFile(id);

			if (object != null) {
				return object;
			}
		}

		List<File> queryResult;

		if (title != null) {
			TypedQuery<File> query = entityManager()
					.createQuery(
							"SELECT o FROM File o WHERE lower(o.title) = lower(:title)",
							File.class);
			query.setParameter("title", title);

			queryResult = query.getResultList();
			if (queryResult.size() > 0) {
				return queryResult.get(0);
			}
		}

		object = new File();
		object.title = title;
		object.name = name;
		object.description = description;
		object.size = size;
		object.fullPath = fullPath;
		object.contentType = contentType;
		object.persist();

		return object;
	}

	@Transient
	private byte[] content;

	@Column(name = "content_type", columnDefinition = "varchar", length = 100)
	private String contentType;

	@Column(name = "description", columnDefinition = "text")
	private String description;

	@Column(name = "full_path", columnDefinition = "text")
	private String fullPath;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "serial")
	private Integer id;

	@ManyToMany
	@JoinTable(name = "instance_documents", joinColumns = { @JoinColumn(name = "document_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "instance_id", nullable = false) })
	private Set<Instance> instances;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@OneToMany(mappedBy = "responseCardFileId")
	private Set<SelectionVariableItem> selectionVariableItems;

	@Column(name = "size", columnDefinition = "int8")
	private Long size;

	@ManyToMany
	@JoinTable(name = "study_file", joinColumns = { @JoinColumn(name = "file_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "study_id", nullable = false) })
	private Set<Study> studies1;

	@Column(name = "title", columnDefinition = "text")
	private String title;

	@Transient
	private String url;

	@OneToMany(mappedBy = "fileId")
	private Set<Variable> variables;

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired(required=false)
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

	public byte[] getContent() {
		return this.content;
	}

	public String getContentType() {
		return contentType;
	}

	public String getDescription() {
		return description;
	}

	public String getFullPath() {
		return fullPath;
	}

	public Integer getId() {
		return this.id;
	}

	public Set<Instance> getInstances() {
		return instances;
	}

	public String getName() {
		return name;
	}

	public Set<SelectionVariableItem> getSelectionVariableItems() {
		return selectionVariableItems;
	}

	public Long getSize() {
		return size;
	}

	public Set<Study> getStudies1() {
		return studies1;
	}

	public String getTitle() {
		return title;
	}

	public String getUrl() {
		return this.url;
	}

	public Set<Variable> getVariables() {
		return variables;
	}

	@Transactional
	public File merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		File merged = this.entityManager.merge(this);
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
			File attached = File.findFile(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInstances(Set<Instance> instances) {
		this.instances = instances;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSelectionVariableItems(
			Set<SelectionVariableItem> selectionVariableItems) {
		this.selectionVariableItems = selectionVariableItems;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public void setStudies1(Set<Study> studies1) {
		this.studies1 = studies1;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setVariables(Set<Variable> variables) {
		this.variables = variables;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
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

	@Override
	public boolean equals(Object obj) {
		return (id != null && id.equals(((File) obj).id))
				|| (title != null && title.equalsIgnoreCase(((File) obj).title));
	}
}
