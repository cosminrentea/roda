package ro.roda.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "study")
@Audited
public class Study {

	public static long countStudys() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Study o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Study study) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("study_" + study.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Study().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Study> findAllStudys() {
		return entityManager().createQuery("SELECT o FROM Study o", Study.class).getResultList();
	}

	public static Study findStudy(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Study.class, id);
	}

	public static List<Study> findStudyEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Study o", Study.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Study> fromJsonArrayToStudys(String json) {
		return new JSONDeserializer<List<Study>>().use(null, ArrayList.class).use("values", Study.class)
				.deserialize(json);
	}

	public static Study fromJsonToStudy(String json) {
		return new JSONDeserializer<Study>().use(null, Study.class).deserialize(json);
	}

	public static void indexStudy(Study study) {
		List<Study> studys = new ArrayList<Study>();
		studys.add(study);
		indexStudys(studys);
	}

	@Async
	public static void indexStudys(Collection<Study> studys) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Study study : studys) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "study_" + study.getId());
			sid.addField("study.id_i", study.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField("study_solrsummary_t", new StringBuilder().append(study.getId()));
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
		String searchString = "Study_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Study().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Study> collection) {
		return new JSONSerializer()
				.exclude("*.class")
				.include("studyDescrs", "catalogStudies", "collectionModelTypes", "dataSourceTypes", "files1",
						"instances", "samplingProcedures", "sources", "studyKeywords", "studyOrgs", "studypeople",
						"topics").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Study</code> (studiu) in baza
	 * de date; in caz afirmativ il returneaza, altfel, metoda il introduce in
	 * baza de date si apoi il returneaza. Verificarea existentei in baza de
	 * date se realizeaza fie dupa identificator, fie dupa un criteriu de
	 * unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul studiului.
	 * @param dateStart
	 *            - data de inceput a studiului.
	 * @param dateEnd
	 *            - data de sfarsit a studiului.
	 * @param insertionStatus
	 *            - pasul din wizard-ul de introducere a metadatelor.
	 * @param addedBy
	 *            - utilizatorul care a adaugat studiul.
	 * @param added
	 *            - data de adaugare a studiului in baza de date.
	 * @param digitizable
	 * @param anonymousUsage
	 * @param unitAnalysisId
	 *            - unitatea de analiza specifica instantei.
	 * @param studyVersion
	 *            - versiunea studiului.
	 * @param rawData
	 * @param rawMetadata
	 * @param timeMethId
	 *            - tipul de metoda temporala.
	 * @param yearStart
	 *            - anul de inceput al studiului.
	 * @param yearEnd
	 *            - anul de sfarsit al studiului.
	 * @return
	 */
	public static Study checkStudy(Integer id, Date dateStart, Date dateEnd, Integer insertionStatus, Users addedBy,
			Calendar added, Boolean digitizable, Boolean anonymousUsage, UnitAnalysis unitAnalysisId,
			Integer studyVersion, Boolean rawData, Boolean rawMetadata, TimeMeth timeMethId, Integer yearStart,
			Integer yearEnd) {
		Study object;

		if (id != null) {
			object = findStudy(id);

			if (object != null) {
				return object;
			}
		}

		object = new Study();
		object.dateStart = dateStart;
		object.dateEnd = dateEnd;
		object.insertionStatus = insertionStatus;
		object.addedBy = addedBy;
		object.added = added;
		object.digitizable = digitizable;
		object.anonymousUsage = anonymousUsage;
		object.unitAnalysisId = unitAnalysisId;
		object.studyVersion = studyVersion;
		object.rawData = rawData;
		object.rawMetadata = rawMetadata;
		object.timeMethId = timeMethId;
		object.yearStart = yearStart;
		object.yearEnd = yearEnd;
		object.persist();

		return object;
	}

	public static Study findFirstStudyWithFilename(String filename) {
		Study result = null;
		if (filename == null || filename.length() == 0)
			throw new IllegalArgumentException("The filename argument is required");
		EntityManager em = Study.entityManager();
		TypedQuery<Study> q = em.createQuery("SELECT o FROM Study AS o WHERE o.importedFilename = :importedFilename",
				Study.class);
		q.setParameter("importedFilename", filename);
		List<Study> results = q.getResultList();
		if (results.size() > 0) {
			result = results.get(0);
		}
		return result;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Column(name = "added", columnDefinition = "timestamp")
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "MM")
	private Calendar added;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "added_by", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private Users addedBy;

	@Column(name = "anonymous_usage", columnDefinition = "bool")
	@NotNull
	private boolean anonymousUsage;

	@Column(name = "imported_filename", columnDefinition = "text")
	private String importedFilename;

	@OneToMany(mappedBy = "studyId", fetch = FetchType.LAZY)
	private Set<CatalogStudy> catalogStudies;

	@ManyToMany(mappedBy = "studies", fetch = FetchType.LAZY)
	private Set<CollectionModelType> collectionModelTypes;

	@ManyToMany(mappedBy = "studies", fetch = FetchType.LAZY)
	private Set<DataSourceType> dataSourceTypes;

	@Column(name = "date_end", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateEnd;

	@Column(name = "date_start", columnDefinition = "date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date dateStart;

	@Column(name = "year_start", columnDefinition = "int4")
	private Integer yearStart;

	@Column(name = "year_end", columnDefinition = "int4")
	private Integer yearEnd;

	@Column(name = "digitizable", columnDefinition = "bool")
	@NotNull
	private boolean digitizable;

	@ManyToMany(mappedBy = "studies1", fetch = FetchType.LAZY)
	private Set<File> files1;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "serial")
	private Integer id;

	@Column(name = "insertion_status", columnDefinition = "int4")
	@NotNull
	private Integer insertionStatus;

	@OneToMany(mappedBy = "studyId", fetch = FetchType.LAZY)
	private Set<Instance> instances;

	@Column(name = "raw_data", columnDefinition = "bool")
	@NotNull
	private boolean rawData;

	@Column(name = "raw_metadata", columnDefinition = "bool")
	@NotNull
	private boolean rawMetadata;

	@ManyToMany(mappedBy = "studies", fetch = FetchType.LAZY)
	private Set<SamplingProcedure> samplingProcedures;

	@ManyToMany(mappedBy = "studies", fetch = FetchType.LAZY)
	private Set<Source> sources;

	@OneToMany(mappedBy = "studyId")
	private Set<StudyDescr> studyDescrs;

	@OneToMany(mappedBy = "studyId", fetch = FetchType.LAZY)
	private Set<StudyKeyword> studyKeywords;

	@OneToMany(mappedBy = "studyId", fetch = FetchType.LAZY)
	private Set<StudyOrg> studyOrgs;

	@OneToMany(mappedBy = "studyId", fetch = FetchType.LAZY)
	private Set<StudyPerson> studypeople;

	@ManyToOne
	@JoinColumn(name = "time_meth_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private TimeMeth timeMethId;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "study_topic", joinColumns = { @JoinColumn(name = "study_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "topic_id", nullable = false) })
	private Set<Topic> topics;

	@ManyToOne
	@JoinColumn(name = "unit_analysis_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private UnitAnalysis unitAnalysisId;

	@Column(name = "study_version")
	private Integer studyVersion;

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

	public Calendar getAdded() {
		return added;
	}

	public Users getAddedBy() {
		return addedBy;
	}

	public String getImportedFilename() {
		return importedFilename;
	}

	public Set<CatalogStudy> getCatalogStudies() {
		return catalogStudies;
	}

	public Set<CollectionModelType> getCollectionModelTypes() {
		return collectionModelTypes;
	}

	public Set<DataSourceType> getDataSourceTypes() {
		return dataSourceTypes;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public Set<File> getFiles1() {
		return files1;
	}

	public Integer getId() {
		return this.id;
	}

	public Integer getInsertionStatus() {
		return insertionStatus;
	}

	public Set<Instance> getInstances() {
		return instances;
	}

	public Set<SamplingProcedure> getSamplingProcedures() {
		return samplingProcedures;
	}

	public Set<Source> getSources() {
		return sources;
	}

	public Set<StudyDescr> getStudyDescrs() {
		return studyDescrs;
	}

	public Set<StudyKeyword> getStudyKeywords() {
		return studyKeywords;
	}

	public Set<StudyOrg> getStudyOrgs() {
		return studyOrgs;
	}

	public Set<StudyPerson> getStudypeople() {
		return studypeople;
	}

	public TimeMeth getTimeMethId() {
		return timeMethId;
	}

	public Set<Topic> getTopics() {
		return topics;
	}

	public Integer getYearStart() {
		return yearStart;
	}

	public Integer getYearEnd() {
		return yearEnd;
	}

	public UnitAnalysis getUnitAnalysisId() {
		return unitAnalysisId;
	}

	public Integer getStudyVersion() {
		return this.studyVersion;
	}

	public boolean isAnonymousUsage() {
		return anonymousUsage;
	}

	public boolean isDigitizable() {
		return digitizable;
	}

	public boolean isRawData() {
		return rawData;
	}

	public boolean isRawMetadata() {
		return rawMetadata;
	}

	@Transactional
	public Study merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Study merged = this.entityManager.merge(this);
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
			Study attached = Study.findStudy(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setAdded(Calendar added) {
		this.added = added;
	}

	public void setAddedBy(Users addedBy) {
		this.addedBy = addedBy;
	}

	public void setAnonymousUsage(boolean anonymousUsage) {
		this.anonymousUsage = anonymousUsage;
	}

	public void setImportedFilename(String importedFilename) {
		this.importedFilename = importedFilename;
	}

	public void setCatalogStudies(Set<CatalogStudy> catalogStudies) {
		this.catalogStudies = catalogStudies;
	}

	public void setCollectionModelTypes(Set<CollectionModelType> collectionModelTypes) {
		this.collectionModelTypes = collectionModelTypes;
	}

	public void setDataSourceTypes(Set<DataSourceType> dataSourceTypes) {
		this.dataSourceTypes = dataSourceTypes;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public void setDigitizable(boolean digitizable) {
		this.digitizable = digitizable;
	}

	public void setFiles1(Set<File> files1) {
		this.files1 = files1;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setInsertionStatus(Integer insertionStatus) {
		this.insertionStatus = insertionStatus;
	}

	public void setInstances(Set<Instance> instances) {
		this.instances = instances;
	}

	public void setRawData(boolean rawData) {
		this.rawData = rawData;
	}

	public void setRawMetadata(boolean rawMetadata) {
		this.rawMetadata = rawMetadata;
	}

	public void setSamplingProcedures(Set<SamplingProcedure> samplingProcedures) {
		this.samplingProcedures = samplingProcedures;
	}

	public void setSources(Set<Source> sources) {
		this.sources = sources;
	}

	public void setStudyDescrs(Set<StudyDescr> studyDescrs) {
		this.studyDescrs = studyDescrs;
	}

	public void setStudyKeywords(Set<StudyKeyword> studyKeywords) {
		this.studyKeywords = studyKeywords;
	}

	public void setStudyOrgs(Set<StudyOrg> studyOrgs) {
		this.studyOrgs = studyOrgs;
	}

	public void setStudypeople(Set<StudyPerson> studypeople) {
		this.studypeople = studypeople;
	}

	public void setTimeMethId(TimeMeth timeMeth) {
		this.timeMethId = timeMeth;
	}

	public void setYearStart(Integer yearStart) {
		this.yearStart = yearStart;
	}

	public void setYearEnd(Integer yearEnd) {
		this.yearEnd = yearEnd;
	}

	public void setTopics(Set<Topic> topics) {
		this.topics = topics;
	}

	public void setUnitAnalysisId(UnitAnalysis unitAnalysisId) {
		this.unitAnalysisId = unitAnalysisId;
	}

	public void setStudyVersion(Integer studyVersion) {
		this.studyVersion = studyVersion;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(
				"timeMethType").toString();
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		indexStudy(this);
	}

	@PreRemove
	private void preRemove() {
		deleteIndex(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((added == null) ? 0 : added.hashCode());
		result = prime * result + ((addedBy == null) ? 0 : addedBy.hashCode());
		result = prime * result + (anonymousUsage ? 1231 : 1237);
		result = prime * result + ((dateEnd == null) ? 0 : dateEnd.hashCode());
		result = prime * result + ((dateStart == null) ? 0 : dateStart.hashCode());
		result = prime * result + (digitizable ? 1231 : 1237);
		result = prime * result + ((insertionStatus == null) ? 0 : insertionStatus.hashCode());
		result = prime * result + (rawData ? 1231 : 1237);
		result = prime * result + (rawMetadata ? 1231 : 1237);
		result = prime * result + ((studyVersion == null) ? 0 : studyVersion.hashCode());
		result = prime * result + ((yearEnd == null) ? 0 : yearEnd.hashCode());
		result = prime * result + ((yearStart == null) ? 0 : yearStart.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Study other = (Study) obj;
		if (added == null) {
			if (other.added != null)
				return false;
		} else if (!added.equals(other.added))
			return false;
		if (addedBy == null) {
			if (other.addedBy != null)
				return false;
		} else if (!addedBy.equals(other.addedBy))
			return false;
		if (anonymousUsage != other.anonymousUsage)
			return false;
		if (dateEnd == null) {
			if (other.dateEnd != null)
				return false;
		} else if (!dateEnd.equals(other.dateEnd))
			return false;
		if (dateStart == null) {
			if (other.dateStart != null)
				return false;
		} else if (!dateStart.equals(other.dateStart))
			return false;
		if (digitizable != other.digitizable)
			return false;
		if (insertionStatus == null) {
			if (other.insertionStatus != null)
				return false;
		} else if (!insertionStatus.equals(other.insertionStatus))
			return false;
		if (rawData != other.rawData)
			return false;
		if (rawMetadata != other.rawMetadata)
			return false;
		if (studyVersion == null) {
			if (other.studyVersion != null)
				return false;
		} else if (!studyVersion.equals(other.studyVersion))
			return false;
		if (yearEnd == null) {
			if (other.yearEnd != null)
				return false;
		} else if (!yearEnd.equals(other.yearEnd))
			return false;
		if (yearStart == null) {
			if (other.yearStart != null)
				return false;
		} else if (!yearStart.equals(other.yearStart))
			return false;
		return true;
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

}
