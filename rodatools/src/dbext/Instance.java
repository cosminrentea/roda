package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the instance database table.
 * 
 */
@Entity
@Table(name = "instance")
public class Instance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private Timestamp dateend;

	@Column(nullable = false)
	private Timestamp datestart;

	// bi-directional many-to-one association to Answer
	@OneToMany(mappedBy = "instance")
	private List<Answer> answers;

	// bi-directional many-to-one association to DemoScope
	@ManyToOne
	@JoinColumn(name = "demoscope_id", nullable = false)
	private DemoScope demoScope;

	// bi-directional many-to-many association to Document
	@ManyToMany
	@JoinTable(name = "instance_documents", joinColumns = { @JoinColumn(name = "instance_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "document_id", nullable = false) })
	private List<Document> documents;

	// bi-directional many-to-one association to Study
	@ManyToOne
	@JoinColumn(name = "study_id", nullable = false)
	private Study study;

	// bi-directional many-to-one association to UnitAnalysi
	@ManyToOne
	@JoinColumn(name = "unit_analysis_id", nullable = false)
	private UnitAnalysi unitAnalysi;

	// bi-directional many-to-one association to InstanceAcl
	@OneToMany(mappedBy = "instance")
	private List<InstanceAcl> instanceAcls;

	// bi-directional many-to-one association to InstanceDescr
	@OneToMany(mappedBy = "instance")
	private List<InstanceDescr> instanceDescrs;

	// bi-directional many-to-one association to InstanceOrg
	@OneToMany(mappedBy = "instance")
	private List<InstanceOrg> instanceOrgs;

	// bi-directional many-to-one association to InstancePerson
	@OneToMany(mappedBy = "instance")
	private List<InstancePerson> instancePersons;

	// bi-directional many-to-one association to Methodology
	@OneToMany(mappedBy = "instance")
	private List<Methodology> methodologies;

	// bi-directional many-to-one association to Variable
	@OneToMany(mappedBy = "instance")
	private List<Variable> variables;

	public Instance() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDateend() {
		return this.dateend;
	}

	public void setDateend(Timestamp dateend) {
		this.dateend = dateend;
	}

	public Timestamp getDatestart() {
		return this.datestart;
	}

	public void setDatestart(Timestamp datestart) {
		this.datestart = datestart;
	}

	public List<Answer> getAnswers() {
		return this.answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public DemoScope getDemoScope() {
		return this.demoScope;
	}

	public void setDemoScope(DemoScope demoScope) {
		this.demoScope = demoScope;
	}

	public List<Document> getDocuments() {
		return this.documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public Study getStudy() {
		return this.study;
	}

	public void setStudy(Study study) {
		this.study = study;
	}

	public UnitAnalysi getUnitAnalysi() {
		return this.unitAnalysi;
	}

	public void setUnitAnalysi(UnitAnalysi unitAnalysi) {
		this.unitAnalysi = unitAnalysi;
	}

	public List<InstanceAcl> getInstanceAcls() {
		return this.instanceAcls;
	}

	public void setInstanceAcls(List<InstanceAcl> instanceAcls) {
		this.instanceAcls = instanceAcls;
	}

	public List<InstanceDescr> getInstanceDescrs() {
		return this.instanceDescrs;
	}

	public void setInstanceDescrs(List<InstanceDescr> instanceDescrs) {
		this.instanceDescrs = instanceDescrs;
	}

	public List<InstanceOrg> getInstanceOrgs() {
		return this.instanceOrgs;
	}

	public void setInstanceOrgs(List<InstanceOrg> instanceOrgs) {
		this.instanceOrgs = instanceOrgs;
	}

	public List<InstancePerson> getInstancePersons() {
		return this.instancePersons;
	}

	public void setInstancePersons(List<InstancePerson> instancePersons) {
		this.instancePersons = instancePersons;
	}

	public List<Methodology> getMethodologies() {
		return this.methodologies;
	}

	public void setMethodologies(List<Methodology> methodologies) {
		this.methodologies = methodologies;
	}

	public List<Variable> getVariables() {
		return this.variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

}