package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import org.hibernate.envers.Audited;
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

@Configurable
@Entity
@Table(schema = "public",name = "study_descr")






public class StudyDescr {

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new StudyDescr().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countStudyDescrs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM StudyDescr o", Long.class).getSingleResult();
    }

	public static List<StudyDescr> findAllStudyDescrs() {
        return entityManager().createQuery("SELECT o FROM StudyDescr o", StudyDescr.class).getResultList();
    }

	public static StudyDescr findStudyDescr(StudyDescrPK id) {
        if (id == null) return null;
        return entityManager().find(StudyDescr.class, id);
    }

	public static List<StudyDescr> findStudyDescrEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM StudyDescr o", StudyDescr.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            StudyDescr attached = StudyDescr.findStudyDescr(this.id);
            this.entityManager.remove(attached);
        }
    }

	@Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
    public StudyDescr merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        StudyDescr merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static StudyDescr fromJsonToStudyDescr(String json) {
        return new JSONDeserializer<StudyDescr>().use(null, StudyDescr.class).deserialize(json);
    }

	public static String toJsonArray(Collection<StudyDescr> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<StudyDescr> fromJsonArrayToStudyDescrs(String json) {
        return new JSONDeserializer<List<StudyDescr>>().use(null, ArrayList.class).use("values", StudyDescr.class).deserialize(json);
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "StudyDescr_solrsummary_t:" + queryString;
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

	public static void indexStudyDescr(StudyDescr studyDescr) {
        List<StudyDescr> studydescrs = new ArrayList<StudyDescr>();
        studydescrs.add(studyDescr);
        indexStudyDescrs(studydescrs);
    }

	@Async
    public static void indexStudyDescrs(Collection<StudyDescr> studydescrs) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (StudyDescr studyDescr : studydescrs) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "studydescr_" + studyDescr.getId());
            sid.addField("studyDescr.langid_t", studyDescr.getLangId());
            sid.addField("studyDescr.studyid_t", studyDescr.getStudyId());
            sid.addField("studyDescr.abstract1_s", studyDescr.getAbstract1());
            sid.addField("studyDescr.grantdetails_s", studyDescr.getGrantDetails());
            sid.addField("studyDescr.title_s", studyDescr.getTitle());
            sid.addField("studyDescr.notes_s", studyDescr.getNotes());
            sid.addField("studyDescr.weighting_s", studyDescr.getWeighting());
            sid.addField("studyDescr.researchinstrument_s", studyDescr.getResearchInstrument());
            sid.addField("studyDescr.scope_s", studyDescr.getScope());
            sid.addField("studyDescr.universe_s", studyDescr.getUniverse());
            sid.addField("studyDescr.subtitle_s", studyDescr.getSubtitle());
            sid.addField("studyDescr.alternativetitle_s", studyDescr.getAlternativeTitle());
            sid.addField("studyDescr.timecovered_s", studyDescr.getTimeCovered());
            sid.addField("studyDescr.geographiccoverage_s", studyDescr.getGeographicCoverage());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("studydescr_solrsummary_t", new StringBuilder().append(studyDescr.getLangId()).append(" ").append(studyDescr.getStudyId()).append(" ").append(studyDescr.getAbstract1()).append(" ").append(studyDescr.getGrantDetails()).append(" ").append(studyDescr.getTitle()).append(" ").append(studyDescr.getNotes()).append(" ").append(studyDescr.getWeighting()).append(" ").append(studyDescr.getResearchInstrument()).append(" ").append(studyDescr.getScope()).append(" ").append(studyDescr.getUniverse()).append(" ").append(studyDescr.getSubtitle()).append(" ").append(studyDescr.getAlternativeTitle()).append(" ").append(studyDescr.getTimeCovered()).append(" ").append(studyDescr.getGeographicCoverage()));
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
    public static void deleteIndex(StudyDescr studyDescr) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("studydescr_" + studyDescr.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexStudyDescr(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new StudyDescr().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	@ManyToOne
    @JoinColumn(name = "lang_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Lang langId;

	@ManyToOne
    @JoinColumn(name = "study_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Study studyId;

	@Column(name = "abstract", columnDefinition = "text")
    private String abstract1;

	@Column(name = "grant_details", columnDefinition = "text")
    private String grantDetails;

	@Column(name = "title", columnDefinition = "text")
    @NotNull
    private String title;

	@Column(name = "notes", columnDefinition = "text")
    private String notes;

	@Column(name = "weighting", columnDefinition = "text")
    private String weighting;

	@Column(name = "research_instrument", columnDefinition = "text")
    private String researchInstrument;

	@Column(name = "scope", columnDefinition = "text")
    private String scope;

	@Column(name = "universe", columnDefinition = "text")
    private String universe;

	@Column(name = "subtitle", columnDefinition = "text")
    private String subtitle;

	@Column(name = "alternative_title", columnDefinition = "text")
    private String alternativeTitle;

	@Column(name = "original_title_language", columnDefinition = "bool")
    @NotNull
    private boolean originalTitleLanguage;

	@Column(name = "time_covered", columnDefinition = "text")
    private String timeCovered;

	@Column(name = "geographic_coverage", columnDefinition = "text")
    private String geographicCoverage;

	public Lang getLangId() {
        return langId;
    }

	public void setLangId(Lang langId) {
        this.langId = langId;
    }

	public Study getStudyId() {
        return studyId;
    }

	public void setStudyId(Study studyId) {
        this.studyId = studyId;
    }

	public String getAbstract1() {
        return abstract1;
    }

	public void setAbstract1(String abstract1) {
        this.abstract1 = abstract1;
    }

	public String getGrantDetails() {
        return grantDetails;
    }

	public void setGrantDetails(String grantDetails) {
        this.grantDetails = grantDetails;
    }

	public String getTitle() {
        return title;
    }

	public void setTitle(String title) {
        this.title = title;
    }

	public String getNotes() {
        return notes;
    }

	public void setNotes(String notes) {
        this.notes = notes;
    }

	public String getWeighting() {
        return weighting;
    }

	public void setWeighting(String weighting) {
        this.weighting = weighting;
    }

	public String getResearchInstrument() {
        return researchInstrument;
    }

	public void setResearchInstrument(String researchInstrument) {
        this.researchInstrument = researchInstrument;
    }

	public String getScope() {
        return scope;
    }

	public void setScope(String scope) {
        this.scope = scope;
    }

	public String getUniverse() {
        return universe;
    }

	public void setUniverse(String universe) {
        this.universe = universe;
    }

	public String getSubtitle() {
        return subtitle;
    }

	public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

	public String getAlternativeTitle() {
        return alternativeTitle;
    }

	public void setAlternativeTitle(String alternativeTitle) {
        this.alternativeTitle = alternativeTitle;
    }

	public boolean isOriginalTitleLanguage() {
        return originalTitleLanguage;
    }

	public void setOriginalTitleLanguage(boolean originalTitleLanguage) {
        this.originalTitleLanguage = originalTitleLanguage;
    }

	public String getTimeCovered() {
        return timeCovered;
    }

	public void setTimeCovered(String timeCovered) {
        this.timeCovered = timeCovered;
    }

	public String getGeographicCoverage() {
        return geographicCoverage;
    }

	public void setGeographicCoverage(String geographicCoverage) {
        this.geographicCoverage = geographicCoverage;
    }

	@EmbeddedId
    private StudyDescrPK id;

	public StudyDescrPK getId() {
        return this.id;
    }

	public void setId(StudyDescrPK id) {
        this.id = id;
    }
}
