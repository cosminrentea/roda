package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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

@Entity
@Table(schema = "public",name = "variable")
@Configurable






public class Variable {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "bigserial")
    private Long id;

	public Long getId() {
        return this.id;
    }

	public void setId(Long id) {
        this.id = id;
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Variable().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countVariables() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Variable o", Long.class).getSingleResult();
    }

	public static List<Variable> findAllVariables() {
        return entityManager().createQuery("SELECT o FROM Variable o", Variable.class).getResultList();
    }

	public static Variable findVariable(Long id) {
        if (id == null) return null;
        return entityManager().find(Variable.class, id);
    }

	public static List<Variable> findVariableEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Variable o", Variable.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Variable attached = Variable.findVariable(this.id);
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
    public Variable merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Variable merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Variable fromJsonToVariable(String json) {
        return new JSONDeserializer<Variable>().use(null, Variable.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Variable> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Variable> fromJsonArrayToVariables(String json) {
        return new JSONDeserializer<List<Variable>>().use(null, ArrayList.class).use("values", Variable.class).deserialize(json);
    }

	@ManyToMany(mappedBy = "variables")
    private Set<Concept> concepts;

	@ManyToMany(mappedBy = "variables")
    private Set<Vargroup> vargroups;

	@OneToOne(mappedBy = "variable")
    private SelectionVariable selectionVariable;

	@OneToMany(mappedBy = "variableId")
    private Set<FormEditedNumberVar> formEditedNumberVars;

	@OneToMany(mappedBy = "variableId")
    private Set<FormEditedTextVar> formEditedTextVars;

	@OneToMany(mappedBy = "variableId")
    private Set<InstanceVariable> instanceVariables;

	@OneToMany(mappedBy = "variableId")
    private Set<OtherStatistic> otherStatistics;

	@OneToMany(mappedBy = "nextVariableId")
    private Set<Skip> skips;

	@OneToMany(mappedBy = "variableId")
    private Set<Skip> skips1;

	@ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File fileId;

	@Column(name = "label", columnDefinition = "text")
    @NotNull
    private String label;

	@Column(name = "type", columnDefinition = "int2")
    @NotNull
    private Short type;

	@Column(name = "operator_instructions", columnDefinition = "text")
    private String operatorInstructions;

	@Column(name = "variable_type", columnDefinition = "int2")
    @NotNull
    private Short variableType;

	public Set<Concept> getConcepts() {
        return concepts;
    }

	public void setConcepts(Set<Concept> concepts) {
        this.concepts = concepts;
    }

	public Set<Vargroup> getVargroups() {
        return vargroups;
    }

	public void setVargroups(Set<Vargroup> vargroups) {
        this.vargroups = vargroups;
    }

	public SelectionVariable getSelectionVariable() {
        return selectionVariable;
    }

	public void setSelectionVariable(SelectionVariable selectionVariable) {
        this.selectionVariable = selectionVariable;
    }

	public Set<FormEditedNumberVar> getFormEditedNumberVars() {
        return formEditedNumberVars;
    }

	public void setFormEditedNumberVars(Set<FormEditedNumberVar> formEditedNumberVars) {
        this.formEditedNumberVars = formEditedNumberVars;
    }

	public Set<FormEditedTextVar> getFormEditedTextVars() {
        return formEditedTextVars;
    }

	public void setFormEditedTextVars(Set<FormEditedTextVar> formEditedTextVars) {
        this.formEditedTextVars = formEditedTextVars;
    }

	public Set<InstanceVariable> getInstanceVariables() {
        return instanceVariables;
    }

	public void setInstanceVariables(Set<InstanceVariable> instanceVariables) {
        this.instanceVariables = instanceVariables;
    }

	public Set<OtherStatistic> getOtherStatistics() {
        return otherStatistics;
    }

	public void setOtherStatistics(Set<OtherStatistic> otherStatistics) {
        this.otherStatistics = otherStatistics;
    }

	public Set<Skip> getSkips() {
        return skips;
    }

	public void setSkips(Set<Skip> skips) {
        this.skips = skips;
    }

	public Set<Skip> getSkips1() {
        return skips1;
    }

	public void setSkips1(Set<Skip> skips1) {
        this.skips1 = skips1;
    }

	public File getFileId() {
        return fileId;
    }

	public void setFileId(File fileId) {
        this.fileId = fileId;
    }

	public String getLabel() {
        return label;
    }

	public void setLabel(String label) {
        this.label = label;
    }

	public Short getType() {
        return type;
    }

	public void setType(Short type) {
        this.type = type;
    }

	public String getOperatorInstructions() {
        return operatorInstructions;
    }

	public void setOperatorInstructions(String operatorInstructions) {
        this.operatorInstructions = operatorInstructions;
    }

	public Short getVariableType() {
        return variableType;
    }

	public void setVariableType(Short variableType) {
        this.variableType = variableType;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "Variable_solrsummary_t:" + queryString;
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

	public static void indexVariable(Variable variable) {
        List<Variable> variables = new ArrayList<Variable>();
        variables.add(variable);
        indexVariables(variables);
    }

	@Async
    public static void indexVariables(Collection<Variable> variables) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Variable variable : variables) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "variable_" + variable.getId());
            sid.addField("variable.selectionvariable_t", variable.getSelectionVariable());
            sid.addField("variable.fileid_t", variable.getFileId());
            sid.addField("variable.label_s", variable.getLabel());
            sid.addField("variable.type_t", variable.getType());
            sid.addField("variable.operatorinstructions_s", variable.getOperatorInstructions());
            sid.addField("variable.variabletype_t", variable.getVariableType());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("variable_solrsummary_t", new StringBuilder().append(variable.getSelectionVariable()).append(" ").append(variable.getFileId()).append(" ").append(variable.getLabel()).append(" ").append(variable.getType()).append(" ").append(variable.getOperatorInstructions()).append(" ").append(variable.getVariableType()));
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
    public static void deleteIndex(Variable variable) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("variable_" + variable.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexVariable(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new Variable().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
}
