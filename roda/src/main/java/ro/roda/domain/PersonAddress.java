package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.envers.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(schema = "public",name = "person_address")
@Configurable






public class PersonAddress {

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static PersonAddress fromJsonToPersonAddress(String json) {
        return new JSONDeserializer<PersonAddress>().use(null, PersonAddress.class).deserialize(json);
    }

	public static String toJsonArray(Collection<PersonAddress> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<PersonAddress> fromJsonArrayToPersonAddresses(String json) {
        return new JSONDeserializer<List<PersonAddress>>().use(null, ArrayList.class).use("values", PersonAddress.class).deserialize(json);
    }

	@ManyToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Address addressId;

	@ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Person personId;

	@Column(name = "date_start", columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date dateStart;

	@Column(name = "date_end", columnDefinition = "date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date dateEnd;

	public Address getAddressId() {
        return addressId;
    }

	public void setAddressId(Address addressId) {
        this.addressId = addressId;
    }

	public Person getPersonId() {
        return personId;
    }

	public void setPersonId(Person personId) {
        this.personId = personId;
    }

	public Date getDateStart() {
        return dateStart;
    }

	public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

	public Date getDateEnd() {
        return dateEnd;
    }

	public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

	@EmbeddedId
    private PersonAddressPK id;

	public PersonAddressPK getId() {
        return this.id;
    }

	public void setId(PersonAddressPK id) {
        this.id = id;
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "PersonAddress_solrsummary_t:" + queryString;
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

	public static void indexPersonAddress(PersonAddress personAddress) {
        List<PersonAddress> personaddresses = new ArrayList<PersonAddress>();
        personaddresses.add(personAddress);
        indexPersonAddresses(personaddresses);
    }

	@Async
    public static void indexPersonAddresses(Collection<PersonAddress> personaddresses) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (PersonAddress personAddress : personaddresses) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "personaddress_" + personAddress.getId());
            sid.addField("personAddress.addressid_t", personAddress.getAddressId());
            sid.addField("personAddress.personid_t", personAddress.getPersonId());
            sid.addField("personAddress.datestart_dt", personAddress.getDateStart());
            sid.addField("personAddress.dateend_dt", personAddress.getDateEnd());
            sid.addField("personAddress.id_t", personAddress.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("personaddress_solrsummary_t", new StringBuilder().append(personAddress.getAddressId()).append(" ").append(personAddress.getPersonId()).append(" ").append(personAddress.getDateStart()).append(" ").append(personAddress.getDateEnd()).append(" ").append(personAddress.getId()));
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
    public static void deleteIndex(PersonAddress personAddress) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("personaddress_" + personAddress.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexPersonAddress(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new PersonAddress().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new PersonAddress().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countPersonAddresses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PersonAddress o", Long.class).getSingleResult();
    }

	public static List<PersonAddress> findAllPersonAddresses() {
        return entityManager().createQuery("SELECT o FROM PersonAddress o", PersonAddress.class).getResultList();
    }

	public static PersonAddress findPersonAddress(PersonAddressPK id) {
        if (id == null) return null;
        return entityManager().find(PersonAddress.class, id);
    }

	public static List<PersonAddress> findPersonAddressEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PersonAddress o", PersonAddress.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            PersonAddress attached = PersonAddress.findPersonAddress(this.id);
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
    public PersonAddress merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PersonAddress merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
