package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.math.BigDecimal;
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
@Table(schema = "public",name = "form_edited_number_var")






public class FormEditedNumberVar {

	@EmbeddedId
    private FormEditedNumberVarPK id;

	public FormEditedNumberVarPK getId() {
        return this.id;
    }

	public void setId(FormEditedNumberVarPK id) {
        this.id = id;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static FormEditedNumberVar fromJsonToFormEditedNumberVar(String json) {
        return new JSONDeserializer<FormEditedNumberVar>().use(null, FormEditedNumberVar.class).deserialize(json);
    }

	public static String toJsonArray(Collection<FormEditedNumberVar> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<FormEditedNumberVar> fromJsonArrayToFormEditedNumberVars(String json) {
        return new JSONDeserializer<List<FormEditedNumberVar>>().use(null, ArrayList.class).use("values", FormEditedNumberVar.class).deserialize(json);
    }

	@ManyToOne
    @JoinColumn(name = "form_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Form formId;

	@ManyToOne
    @JoinColumn(name = "variable_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Variable variableId;

	@Column(name = "value", columnDefinition = "numeric", precision = 10, scale = 2)
    @NotNull
    private BigDecimal value;

	public Form getFormId() {
        return formId;
    }

	public void setFormId(Form formId) {
        this.formId = formId;
    }

	public Variable getVariableId() {
        return variableId;
    }

	public void setVariableId(Variable variableId) {
        this.variableId = variableId;
    }

	public BigDecimal getValue() {
        return value;
    }

	public void setValue(BigDecimal value) {
        this.value = value;
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "FormEditedNumberVar_solrsummary_t:" + queryString;
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

	public static void indexFormEditedNumberVar(FormEditedNumberVar formEditedNumberVar) {
        List<FormEditedNumberVar> formeditednumbervars = new ArrayList<FormEditedNumberVar>();
        formeditednumbervars.add(formEditedNumberVar);
        indexFormEditedNumberVars(formeditednumbervars);
    }

	@Async
    public static void indexFormEditedNumberVars(Collection<FormEditedNumberVar> formeditednumbervars) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (FormEditedNumberVar formEditedNumberVar : formeditednumbervars) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "formeditednumbervar_" + formEditedNumberVar.getId());
            sid.addField("formEditedNumberVar.formid_t", formEditedNumberVar.getFormId());
            sid.addField("formEditedNumberVar.variableid_t", formEditedNumberVar.getVariableId());
            sid.addField("formEditedNumberVar.value_t", formEditedNumberVar.getValue());
            sid.addField("formEditedNumberVar.id_t", formEditedNumberVar.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("formeditednumbervar_solrsummary_t", new StringBuilder().append(formEditedNumberVar.getFormId()).append(" ").append(formEditedNumberVar.getVariableId()).append(" ").append(formEditedNumberVar.getValue()).append(" ").append(formEditedNumberVar.getId()));
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
    public static void deleteIndex(FormEditedNumberVar formEditedNumberVar) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("formeditednumbervar_" + formEditedNumberVar.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexFormEditedNumberVar(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new FormEditedNumberVar().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new FormEditedNumberVar().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countFormEditedNumberVars() {
        return entityManager().createQuery("SELECT COUNT(o) FROM FormEditedNumberVar o", Long.class).getSingleResult();
    }

	public static List<FormEditedNumberVar> findAllFormEditedNumberVars() {
        return entityManager().createQuery("SELECT o FROM FormEditedNumberVar o", FormEditedNumberVar.class).getResultList();
    }

	public static FormEditedNumberVar findFormEditedNumberVar(FormEditedNumberVarPK id) {
        if (id == null) return null;
        return entityManager().find(FormEditedNumberVar.class, id);
    }

	public static List<FormEditedNumberVar> findFormEditedNumberVarEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM FormEditedNumberVar o", FormEditedNumberVar.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            FormEditedNumberVar attached = FormEditedNumberVar.findFormEditedNumberVar(this.id);
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
    public FormEditedNumberVar merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        FormEditedNumberVar merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
