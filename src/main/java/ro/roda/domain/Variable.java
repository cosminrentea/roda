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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

import flexjson.JSON;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Entity
@Table(schema = "public", name = "variable")
@Configurable
@Audited
public class Variable implements Comparable {

	public static long countVariables() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Variable o", Long.class).getSingleResult();
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

	public static final EntityManager entityManager() {
		EntityManager em = new Variable().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Variable> findAllVariables() {
		return entityManager().createQuery("SELECT o FROM Variable o", Variable.class).getResultList();
	}

	public static Variable findVariable(Long id) {
		if (id == null)
			return null;
		return entityManager().find(Variable.class, id);
	}

	public static Variable findVariable(Question question, Integer order) {
		TypedQuery<Variable> query = entityManager().createQuery(
				"SELECT o FROM Variable o WHERE questionId = :question AND order = :order", Variable.class);
		query.setParameter("question", question);
		query.setParameter("order", order);

		List<Variable> queryResult = query.getResultList();
		if (queryResult.size() > 0) {
			return queryResult.get(0);
		}

		return null;
	}

	public static List<Variable> findVariableEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Variable o", Variable.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Variable> fromJsonArrayToVariables(String json) {
		return new JSONDeserializer<List<Variable>>().use(null, ArrayList.class).use("values", Variable.class)
				.deserialize(json);
	}

	public static Variable fromJsonToVariable(String json) {
		return new JSONDeserializer<Variable>().use(null, Variable.class).deserialize(json);
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
			sid.addField("variable.operatorinstructions_s", variable.getOperatorInstructions());
			sid.addField("variable.variabletype_t", variable.getVariableType());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"variable_solrsummary_t",
					new StringBuilder().append(variable.getSelectionVariable()).append(" ")
							.append(variable.getFileId()).append(" ").append(variable.getLabel()).append(" ")
							.append(variable.getOperatorInstructions()).append(" ").append(variable.getVariableType()));
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
		String searchString = "Variable_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Variable().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Variable> collection) {
		return new JSONSerializer()
				.exclude("*.class")
				.exclude("classAuditReader", "auditReader", "instanceVariables.auditReader",
						"instanceVariables.classAuditReader", "otherStatistics.auditReader",
						"otherStatistics.classAuditReader", "selectionVariable.auditReader",
						"selectionVariable.classAuditReader", "skips.auditReader", "skips.classAuditReader",
						"skips1.auditReader", "skips1.classAuditReader", "vargroups.auditReader",
						"vargroups.classAuditReader", "concepts", "formEditedNumberVars", "formEditedTextVars",
						"fileId").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Variable</code> (variabila)
	 * in baza de date; in caz afirmativ il returneaza, altfel, metoda il
	 * introduce in baza de date si apoi il returneaza. Verificarea existentei
	 * in baza de date se realizeaza fie dupa identificator, fie dupa un
	 * criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul variabilei.
	 * @param label
	 *            - numele variabilei.
	 * @param type
	 *            - tipul variabilei (0 : edited, 1 : edited number, 2 :
	 *            selection).
	 * @param operatorInstructions
	 *            - text care informeaza operatorul ce chestioneaza asupra unor
	 *            actiuni pe care le are de facut cand ajunge la variabila.
	 * @param fileId
	 *            - fisierul din care provine variabila.
	 * @return
	 */
	public static Variable checkVariable(Long id, String label, Question questionId, Integer order, Short type,
			String operatorInstructions, File fileId) {
		Variable object = null;

		if (id != null) {
			object = findVariable(id);
		} else {
			object = findVariable(questionId, order);
		}

		if (object != null) {
			return object;
		}

		object = new Variable();
		object.label = label;
		object.variableType = type;
		object.questionId = questionId;
		object.orderInQuestion = order;
		object.operatorInstructions = operatorInstructions;
		object.fileId = fileId;
		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@ManyToMany(mappedBy = "variables")
	private Set<Concept> concepts;

	@ManyToOne
	@JoinColumn(name = "question_id", columnDefinition = "integer", referencedColumnName = "id")
	private Question questionId;

	@ManyToOne
	@JoinColumn(name = "file_id", columnDefinition = "integer", referencedColumnName = "id")
	private File fileId;

	@OneToMany(mappedBy = "variableId")
	private Set<FormEditedNumberVar> formEditedNumberVars;

	@OneToMany(mappedBy = "variableId")
	private Set<FormEditedTextVar> formEditedTextVars;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	// , columnDefinition = "bigserial")
	private Long id;

	@Column(name = "label", columnDefinition = "text")
	@NotNull
	private String label;

	@Column(name = "name", columnDefinition = "text")
	@NotNull
	private String name;

	@Column(name = "values", columnDefinition = "text")
	private String values;

	@Column(name = "order_in_question", columnDefinition = "int")
	// @NotNull
	private Integer orderInQuestion;

	@Column(name = "operator_instructions", columnDefinition = "text")
	private String operatorInstructions;

	@OneToMany(mappedBy = "variableId")
	private Set<OtherStatistic> otherStatistics;

	@Transient
	@JSON(name = "nrfreq")
	private Long categoriesNumber;

	@OneToOne(mappedBy = "variable")
	private SelectionVariable selectionVariable;

	@OneToMany(mappedBy = "nextVariableId")
	private Set<Skip> skips;

	@OneToMany(mappedBy = "variableId")
	private Set<Skip> skips1;

	@ManyToMany(mappedBy = "variables")
	private Set<Vargroup> vargroups;

	@Column(name = "variable_type", columnDefinition = "int2")
	@NotNull
	private Short variableType;

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

	public Set<Concept> getConcepts() {
		return concepts;
	}

	public File getFileId() {
		return fileId;
	}

	public Set<FormEditedNumberVar> getFormEditedNumberVars() {
		return formEditedNumberVars;
	}

	public Set<FormEditedTextVar> getFormEditedTextVars() {
		return formEditedTextVars;
	}

	public Long getId() {
		return this.id;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public Integer getOrderInQuestion() {
		return orderInQuestion;
	}

	public Question getQuestionId() {
		return questionId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOperatorInstructions() {
		return operatorInstructions;
	}

	public Set<OtherStatistic> getOtherStatistics() {
		return otherStatistics;
	}

	public SelectionVariable getSelectionVariable() {
		return selectionVariable;
	}

	public Set<Skip> getSkips() {
		return skips;
	}

	public Set<Skip> getSkips1() {
		return skips1;
	}

	public Set<Vargroup> getVargroups() {
		return vargroups;
	}

	public Short getVariableType() {
		return variableType;
	}

	@Transactional
	public Variable merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Variable merged = this.entityManager.merge(this);
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
			Variable attached = Variable.findVariable(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setConcepts(Set<Concept> concepts) {
		this.concepts = concepts;
	}

	public void setFileId(File fileId) {
		this.fileId = fileId;
	}

	public void setFormEditedNumberVars(Set<FormEditedNumberVar> formEditedNumberVars) {
		this.formEditedNumberVars = formEditedNumberVars;
	}

	public void setFormEditedTextVars(Set<FormEditedTextVar> formEditedTextVars) {
		this.formEditedTextVars = formEditedTextVars;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setOrderInQuestion(Integer order) {
		this.orderInQuestion = order;
	}

	public void setQuestionId(Question questionId) {
		this.questionId = questionId;
	}

	public void setOperatorInstructions(String operatorInstructions) {
		this.operatorInstructions = operatorInstructions;
	}

	public void setOtherStatistics(Set<OtherStatistic> otherStatistics) {
		this.otherStatistics = otherStatistics;
	}

	public void setSelectionVariable(SelectionVariable selectionVariable) {
		this.selectionVariable = selectionVariable;
	}

	public void setSkips(Set<Skip> skips) {
		this.skips = skips;
	}

	public String getValues() {
		return values;
	}

	public void setValues(String values) {
		this.values = values;
	}

	public void setSkips1(Set<Skip> skips1) {
		this.skips1 = skips1;
	}

	public void setVargroups(Set<Vargroup> vargroups) {
		this.vargroups = vargroups;
	}

	public void setVariableType(Short variableType) {
		this.variableType = variableType;
	}

	public Long getCategoriesNumber() {
		this.categoriesNumber = Long.valueOf(otherStatistics.size());
		return categoriesNumber;
	}

	public String toJson() {
		return new JSONSerializer()
				.exclude("*.class")
				.exclude("classAuditReader", "auditReader", "instanceVariables.auditReader",
						"instanceVariables.classAuditReader", "otherStatistics.auditReader",
						"otherStatistics.classAuditReader", "selectionVariable.auditReader",
						"selectionVariable.classAuditReader", "skips.auditReader", "skips.classAuditReader",
						"skips1.auditReader", "skips1.classAuditReader", "vargroups.auditReader",
						"vargroups.classAuditReader", "concepts", "formEditedNumberVars", "formEditedTextVars",
						"fileId").serialize(this);
	}

	public String toJsonWithFreq() {
		List<Variable> l = new ArrayList<Variable>();
		l.add(this);
		return new JSONSerializer()
				.exclude("*.class")
				.exclude("operatorInstructions", "selectionVariable", "type", "variableType", "fileId",
						"classAuditReader", "auditReader", "instanceVariables", "orderInQuestion",
						"otherStatistics.auditReader", "otherStatistics.classAuditReader",
						"otherStatistics.variableId.classAuditReader", "otherStatistics.variableId.auditReader",
						"skips", "skips1", "vargroups", "concepts", "formEditedNumberVars", "formEditedTextVars",
						"questionId.auditReader", "questionId.classAuditReader", "questionId.variables",
						"questionId.instanceId", "questionId.langId")
				.include("otherStatistics.id", "otherStatistics.name", "otherStatistics.value",
						"otherStatistics.variableId.id").rootName("data").serialize(l);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		// TODO temporarily disabled
		// indexVariable(this);
	}

	@PreRemove
	private void preRemove() {
		// TODO temporarily disabled
		// deleteIndex(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		Variable other = (Variable) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(id, other.id).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(id).toHashCode();
	}

	@Override
	public int compareTo(Object o) {
		Variable other = (Variable) o;
		return new CompareToBuilder().append(this.id, other.id).toComparison();
	}

	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}

}
