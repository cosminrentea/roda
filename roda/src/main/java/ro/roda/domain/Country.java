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
import javax.persistence.OneToMany;
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
@Table(schema = "public",name = "country")






public class Country {

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static Country fromJsonToCountry(String json) {
        return new JSONDeserializer<Country>().use(null, Country.class).deserialize(json);
    }

	public static String toJsonArray(Collection<Country> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<Country> fromJsonArrayToCountrys(String json) {
        return new JSONDeserializer<List<Country>>().use(null, ArrayList.class).use("values", Country.class).deserialize(json);
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "Country_solrsummary_t:" + queryString;
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

	public static void indexCountry(Country country) {
        List<Country> countrys = new ArrayList<Country>();
        countrys.add(country);
        indexCountrys(countrys);
    }

	@Async
    public static void indexCountrys(Collection<Country> countrys) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Country country : countrys) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "country_" + country.getId());
            sid.addField("country.namero_s", country.getNameRo());
            sid.addField("country.nameself_s", country.getNameSelf());
            sid.addField("country.nameen_s", country.getNameEn());
            sid.addField("country.iso3166_s", country.getIso3166());
            sid.addField("country.iso3166alpha3_s", country.getIso3166Alpha3());
            sid.addField("country.id_i", country.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("country_solrsummary_t", new StringBuilder().append(country.getNameRo()).append(" ").append(country.getNameSelf()).append(" ").append(country.getNameEn()).append(" ").append(country.getIso3166()).append(" ").append(country.getIso3166Alpha3()).append(" ").append(country.getId()));
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
    public static void deleteIndex(Country country) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("country_" + country.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexCountry(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new Country().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new Country().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countCountrys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Country o", Long.class).getSingleResult();
    }

	public static List<Country> findAllCountrys() {
        return entityManager().createQuery("SELECT o FROM Country o", Country.class).getResultList();
    }

	public static Country findCountry(Integer id) {
        if (id == null) return null;
        return entityManager().find(Country.class, id);
    }

	public static List<Country> findCountryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Country o", Country.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Country attached = Country.findCountry(this.id);
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
    public Country merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Country merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
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

	@OneToMany(mappedBy = "countryId")
    private Set<City> cities;

	@OneToMany(mappedBy = "countryId")
    private Set<Region> regions;

	@Column(name = "name_ro", columnDefinition = "text")
    private String nameRo;

	@Column(name = "name_self", columnDefinition = "text")
    private String nameSelf;

	@Column(name = "name_en", columnDefinition = "text")
    private String nameEn;

	@Column(name = "iso3166", columnDefinition = "bpchar", length = 2, unique = true)
    @NotNull
    private String iso3166;

	@Column(name = "iso3166_alpha3", columnDefinition = "bpchar", length = 3)
    private String iso3166Alpha3;

	public Set<City> getCities() {
        return cities;
    }

	public void setCities(Set<City> cities) {
        this.cities = cities;
    }

	public Set<Region> getRegions() {
        return regions;
    }

	public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

	public String getNameRo() {
        return nameRo;
    }

	public void setNameRo(String nameRo) {
        this.nameRo = nameRo;
    }

	public String getNameSelf() {
        return nameSelf;
    }

	public void setNameSelf(String nameSelf) {
        this.nameSelf = nameSelf;
    }

	public String getNameEn() {
        return nameEn;
    }

	public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

	public String getIso3166() {
        return iso3166;
    }

	public void setIso3166(String iso3166) {
        this.iso3166 = iso3166;
    }

	public String getIso3166Alpha3() {
        return iso3166Alpha3;
    }

	public void setIso3166Alpha3(String iso3166Alpha3) {
        this.iso3166Alpha3 = iso3166Alpha3;
    }
}
