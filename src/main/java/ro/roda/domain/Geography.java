package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.Table;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Configurable
@Entity
@Table(schema = "public", name = "geography")
@Audited
public class Geography {

	public static long countGeographys() {
		return entityManager().createQuery("SELECT COUNT(o) FROM Geography o", Long.class).getSingleResult();
	}

	@Async
	public static void deleteIndex(Geography geography) {
		SolrServer solrServer = solrServer();
		try {
			solrServer.deleteById("geography_" + geography.getId());
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final EntityManager entityManager() {
		EntityManager em = new Geography().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<Geography> findAllGeographys() {
		return entityManager().createQuery("SELECT o FROM Geography o", Geography.class).getResultList();
	}

	public static Geography findGeography(Integer id) {
		if (id == null)
			return null;
		return entityManager().find(Geography.class, id);
	}

	public static List<Geography> findGeographyEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM Geography o", Geography.class).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();
	}

	public static Collection<Geography> fromJsonArrayToGeographys(String json) {
		return new JSONDeserializer<List<Geography>>().use(null, ArrayList.class).use("values", Geography.class)
				.deserialize(json);
	}

	public static Geography fromJsonToGeography(String json) {
		return new JSONDeserializer<Geography>().use(null, Geography.class).deserialize(json);
	}

	public static void indexGeography(Geography geography) {
		List<Geography> geographys = new ArrayList<Geography>();
		geographys.add(geography);
		indexGeographys(geographys);
	}

	@Async
	public static void indexGeographys(Collection<Geography> geographys) {
		List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
		for (Geography geography : geographys) {
			SolrInputDocument sid = new SolrInputDocument();
			sid.addField("id", "geography_" + geography.getId());
			sid.addField("instance.geoversionid_t", geography.getGeoVersionId());
			sid.addField("geography.siruta_i", geography.getSiruta());
			sid.addField("geography.denloc_s", geography.getDenloc());
			sid.addField("geography.codp_i", geography.getCodp());
			sid.addField("geography.jud_i", geography.getJud());
			sid.addField("geography.sirsup_i", geography.getSirsup());
			sid.addField("geography.tip_i", geography.getTip());
			sid.addField("geography.niv_i", geography.getNiv());
			sid.addField("geography.med_i", geography.getMed());
			sid.addField("geography.regiune_i", geography.getRegiune());
			sid.addField("geography.fsj_s", geography.getFsj());
			sid.addField("geography.fs2_s", geography.getFs2());
			sid.addField("geography.fs3_s", geography.getFs3());
			sid.addField("geography.fsl_s", geography.getFsl());
			sid.addField("geography.rang_s", geography.getRang());
			sid.addField("geography.id_i", geography.getId());
			// Add summary field to allow searching documents for objects of
			// this type
			sid.addField(
					"geography_solrsummary_t",
					new StringBuilder().append(geography.getSiruta()).append(" ").append(geography.getGeoVersionId())
							.append(" ").append(geography.getDenloc()).append(geography.getCodp()).append(" ")
							.append(geography.getJud()).append(geography.getSirsup()).append(geography.getTip())
							.append(geography.getNiv()).append(geography.getMed()).append(geography.getRegiune())
							.append(geography.getFsj()).append(geography.getFs2()).append(geography.getFs3())
							.append(geography.getFsl()).append(geography.getRang()).append(" ")
							.append(geography.getId()));
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
		String searchString = "Geography_solrsummary_t:" + queryString;
		return search(new SolrQuery(searchString.toLowerCase()));
	}

	public static SolrServer solrServer() {
		SolrServer _solrServer = new Geography().solrServer;
		if (_solrServer == null)
			throw new IllegalStateException(
					"Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return _solrServer;
	}

	public static String toJsonArray(Collection<Geography> collection) {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(collection);
	}

	/**
	 * Verifica existenta unui obiect de tip <code>Geography</code> in baza de
	 * date; in caz afirmativ il returneaza, altfel, metoda il introduce in baza
	 * de date si apoi il returneaza. Verificarea existentei in baza de date se
	 * realizeaza fie dupa identificator, fie dupa un criteriu de unicitate.
	 * 
	 * <p>
	 * Criterii de unicitate:
	 * <ul>
	 * </ul>
	 * 
	 * <p>
	 * 
	 * @param id
	 *            - identificatorul obiectului.
	 * @param siruta
	 *            - codul SIRUTA.
	 * @param denloc
	 *            - denumirea localitatii.
	 * @param codp
	 * @param jud
	 * @param sirsup
	 * @param tip
	 * @param niv
	 * @param med
	 * @param regiune
	 * @param fsj
	 * @param fs2
	 * @param fs3
	 * @param fsl
	 * @param rang
	 * @return
	 */
	public static Geography checkGeography(Integer id, GeoVersion geoVersionId, Integer siruta, String denloc,
			Integer codp, Integer jud, Integer sirsup, Integer tip, Integer niv, Integer med, Integer regiune,
			String fsj, String fs2, String fs3, String fsl, String rang) {
		Geography object;

		if (id != null) {
			object = findGeography(id);

			if (object != null) {
				return object;
			}
		}

		object = new Geography();
		object.geoVersionId = geoVersionId;
		object.siruta = siruta;
		object.codp = codp;
		object.denloc = denloc;
		object.jud = jud;
		object.sirsup = sirsup;
		object.tip = tip;
		object.niv = niv;
		object.med = med;
		object.regiune = regiune;
		object.fsj = fsj;
		object.fs2 = fs2;
		object.fs3 = fs3;
		object.fsl = fsl;
		object.rang = rang;

		object.persist();

		return object;
	}

	public static AuditReader getClassAuditReader() {
		return AuditReaderFactory.get(entityManager());
	}

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "siruta", columnDefinition = "int4")
	private Integer siruta;

	@Column(name = "denloc", columnDefinition = "varchar", length = 40)
	private String denloc;

	@Column(name = "codp", columnDefinition = "int4")
	private Integer codp;

	@Column(name = "jud", columnDefinition = "int4", length = 2)
	private Integer jud;

	@Column(name = "sirsup", columnDefinition = "int4")
	private Integer sirsup;

	@Column(name = "tip", columnDefinition = "int4")
	private Integer tip;

	@Column(name = "niv", columnDefinition = "int4")
	private Integer niv;

	@Column(name = "med", columnDefinition = "int4")
	private Integer med;

	@Column(name = "regiune", columnDefinition = "int4")
	private Integer regiune;

	@Column(name = "fsj", columnDefinition = "varchar", length = 2)
	private String fsj;

	@Column(name = "fs2", columnDefinition = "varchar", length = 7)
	private String fs2;

	@Column(name = "fs3", columnDefinition = "varchar", length = 7)
	private String fs3;

	@Column(name = "fsl", columnDefinition = "varchar", length = 13)
	private String fsl;

	@Column(name = "rang", columnDefinition = "varchar", length = 3)
	private String rang;

	@ManyToOne
	@JoinColumn(name = "geoversion_id", columnDefinition = "integer", referencedColumnName = "id", nullable = false)
	private GeoVersion geoVersionId;

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

	public Integer getId() {
		return id;
	}

	public Integer getSiruta() {
		return siruta;
	}

	public String getDenloc() {
		return denloc;
	}

	public Integer getCodp() {
		return codp;
	}

	public Integer getJud() {
		return jud;
	}

	public Integer getSirsup() {
		return sirsup;
	}

	public Integer getTip() {
		return tip;
	}

	public Integer getNiv() {
		return niv;
	}

	public Integer getMed() {
		return med;
	}

	public Integer getRegiune() {
		return regiune;
	}

	public String getFsj() {
		return fsj;
	}

	public String getFs2() {
		return fs2;
	}

	public String getFs3() {
		return fs3;
	}

	public String getFsl() {
		return fsl;
	}

	public String getRang() {
		return rang;
	}

	public GeoVersion getGeoVersionId() {
		return geoVersionId;
	}

	@Transactional
	public Geography merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		Geography merged = this.entityManager.merge(this);
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
			Geography attached = Geography.findGeography(this.id);
			this.entityManager.remove(attached);
		}
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setSiruta(Integer siruta) {
		this.siruta = siruta;
	}

	public void setDenloc(String denloc) {
		this.denloc = denloc;
	}

	public void setCodp(Integer codp) {
		this.codp = codp;
	}

	public void setJud(Integer jud) {
		this.jud = jud;
	}

	public void setTip(Integer tip) {
		this.tip = tip;
	}

	public void setNiv(Integer niv) {
		this.niv = niv;
	}

	public void setMed(Integer med) {
		this.med = med;
	}

	public void setRegiune(Integer regiune) {
		this.regiune = regiune;
	}

	public void setFsj(String fsj) {
		this.fsj = fsj;
	}

	public void setFs2(String fs2) {
		this.fs2 = fs2;
	}

	public void setFs3(String fs3) {
		this.fs3 = fs3;
	}

	public void setFsl(String fsl) {
		this.fsl = fsl;
	}

	public void setRang(String rang) {
		this.rang = rang;
	}

	public void setGeoVersionId(GeoVersion geoVersionId) {
		this.geoVersionId = geoVersionId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").exclude("classAuditReader", "auditReader").serialize(this);
	}

	// public String toString() {
	// return ReflectionToStringBuilder.toString(this,
	// ToStringStyle.SHORT_PREFIX_STYLE);
	// }

	@PostUpdate
	@PostPersist
	private void postPersistOrUpdate() {
		// indexGeography(this);
	}

	@PreRemove
	private void preRemove() {
		// deleteIndex(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denloc == null) ? 0 : denloc.hashCode());
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

		Geography other = (Geography) obj;

		if (siruta == null) {
			if (other.siruta != null)
				return false;
		} else if (!siruta.equals(other.siruta))
			return false;

		if (denloc == null) {
			if (other.denloc != null)
				return false;
		} else if (!denloc.equals(other.denloc))
			return false;

		if (codp == null) {
			if (other.codp != null)
				return false;
		} else if (!codp.equals(other.codp))
			return false;

		if (jud == null) {
			if (other.jud != null)
				return false;
		} else if (!jud.equals(other.jud))
			return false;

		if (sirsup == null) {
			if (other.sirsup != null)
				return false;
		} else if (!sirsup.equals(other.sirsup))
			return false;

		if (tip == null) {
			if (other.tip != null)
				return false;
		} else if (!tip.equals(other.tip))
			return false;

		if (niv == null) {
			if (other.niv != null)
				return false;
		} else if (!codp.equals(other.niv))
			return false;

		if (med == null) {
			if (other.med != null)
				return false;
		} else if (!med.equals(other.med))
			return false;

		if (regiune == null) {
			if (other.regiune != null)
				return false;
		} else if (!regiune.equals(other.regiune))
			return false;

		if (fsj == null) {
			if (other.fsj != null)
				return false;
		} else if (!fsj.equals(other.fsj))
			return false;

		if (fs2 == null) {
			if (other.fs2 != null)
				return false;
		} else if (!fs2.equals(other.fs2))
			return false;

		if (fs3 == null) {
			if (other.fs3 != null)
				return false;
		} else if (!fs3.equals(other.fs3))
			return false;

		if (fsl == null) {
			if (other.fsl != null)
				return false;
		} else if (!fsl.equals(other.fsl))
			return false;

		if (rang == null) {
			if (other.rang != null)
				return false;
		} else if (!rang.equals(other.rang))
			return false;

		return true;
	}

	@JsonIgnore
	public AuditReader getAuditReader() {
		return AuditReaderFactory.get(entityManager);
	}
}
