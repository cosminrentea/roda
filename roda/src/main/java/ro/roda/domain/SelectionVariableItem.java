package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

@Entity
@Table(schema = "public",name = "selection_variable_item")
@Configurable






public class SelectionVariableItem {

	@EmbeddedId
    private SelectionVariableItemPK id;

	public SelectionVariableItemPK getId() {
        return this.id;
    }

	public void setId(SelectionVariableItemPK id) {
        this.id = id;
    }

	@OneToMany(mappedBy = "selectionVariableItem")
    private Set<FormSelectionVar> formSelectionVars;

	@ManyToOne
    @JoinColumn(name = "response_card_file_id", referencedColumnName = "id")
    private File responseCardFileId;

	@ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Item itemId;

	@ManyToOne
    @JoinColumn(name = "variable_id", referencedColumnName = "variable_id", nullable = false, insertable = false, updatable = false)
    private SelectionVariable variableId;

	@Column(name = "order_of_item_in_variable", columnDefinition = "int4", unique = true)
    @NotNull
    private Integer orderOfItemInVariable;

	@Column(name = "frequency_value", columnDefinition = "float4", precision = 8, scale = 8)
    private Float frequencyValue;

	public Set<FormSelectionVar> getFormSelectionVars() {
        return formSelectionVars;
    }

	public void setFormSelectionVars(Set<FormSelectionVar> formSelectionVars) {
        this.formSelectionVars = formSelectionVars;
    }

	public File getResponseCardFileId() {
        return responseCardFileId;
    }

	public void setResponseCardFileId(File responseCardFileId) {
        this.responseCardFileId = responseCardFileId;
    }

	public Item getItemId() {
        return itemId;
    }

	public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

	public SelectionVariable getVariableId() {
        return variableId;
    }

	public void setVariableId(SelectionVariable variableId) {
        this.variableId = variableId;
    }

	public Integer getOrderOfItemInVariable() {
        return orderOfItemInVariable;
    }

	public void setOrderOfItemInVariable(Integer orderOfItemInVariable) {
        this.orderOfItemInVariable = orderOfItemInVariable;
    }

	public Float getFrequencyValue() {
        return frequencyValue;
    }

	public void setFrequencyValue(Float frequencyValue) {
        this.frequencyValue = frequencyValue;
    }

	@Autowired
    transient SolrServer solrServer;

	public static QueryResponse search(String queryString) {
        String searchString = "SelectionVariableItem_solrsummary_t:" + queryString;
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

	public static void indexSelectionVariableItem(SelectionVariableItem selectionVariableItem) {
        List<SelectionVariableItem> selectionvariableitems = new ArrayList<SelectionVariableItem>();
        selectionvariableitems.add(selectionVariableItem);
        indexSelectionVariableItems(selectionvariableitems);
    }

	@Async
    public static void indexSelectionVariableItems(Collection<SelectionVariableItem> selectionvariableitems) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (SelectionVariableItem selectionVariableItem : selectionvariableitems) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "selectionvariableitem_" + selectionVariableItem.getId());
            sid.addField("selectionVariableItem.responsecardfileid_t", selectionVariableItem.getResponseCardFileId());
            sid.addField("selectionVariableItem.itemid_t", selectionVariableItem.getItemId());
            sid.addField("selectionVariableItem.variableid_t", selectionVariableItem.getVariableId());
            sid.addField("selectionVariableItem.orderofiteminvariable_i", selectionVariableItem.getOrderOfItemInVariable());
            sid.addField("selectionVariableItem.frequencyvalue_f", selectionVariableItem.getFrequencyValue());
            sid.addField("selectionVariableItem.id_t", selectionVariableItem.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("selectionvariableitem_solrsummary_t", new StringBuilder().append(selectionVariableItem.getResponseCardFileId()).append(" ").append(selectionVariableItem.getItemId()).append(" ").append(selectionVariableItem.getVariableId()).append(" ").append(selectionVariableItem.getOrderOfItemInVariable()).append(" ").append(selectionVariableItem.getFrequencyValue()).append(" ").append(selectionVariableItem.getId()));
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
    public static void deleteIndex(SelectionVariableItem selectionVariableItem) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("selectionvariableitem_" + selectionVariableItem.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexSelectionVariableItem(this);
    }

	@PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

	public static SolrServer solrServer() {
        SolrServer _solrServer = new SelectionVariableItem().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static SelectionVariableItem fromJsonToSelectionVariableItem(String json) {
        return new JSONDeserializer<SelectionVariableItem>().use(null, SelectionVariableItem.class).deserialize(json);
    }

	public static String toJsonArray(Collection<SelectionVariableItem> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<SelectionVariableItem> fromJsonArrayToSelectionVariableItems(String json) {
        return new JSONDeserializer<List<SelectionVariableItem>>().use(null, ArrayList.class).use("values", SelectionVariableItem.class).deserialize(json);
    }

	@PersistenceContext
    transient EntityManager entityManager;

	public static final EntityManager entityManager() {
        EntityManager em = new SelectionVariableItem().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	public static long countSelectionVariableItems() {
        return entityManager().createQuery("SELECT COUNT(o) FROM SelectionVariableItem o", Long.class).getSingleResult();
    }

	public static List<SelectionVariableItem> findAllSelectionVariableItems() {
        return entityManager().createQuery("SELECT o FROM SelectionVariableItem o", SelectionVariableItem.class).getResultList();
    }

	public static SelectionVariableItem findSelectionVariableItem(SelectionVariableItemPK id) {
        if (id == null) return null;
        return entityManager().find(SelectionVariableItem.class, id);
    }

	public static List<SelectionVariableItem> findSelectionVariableItemEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM SelectionVariableItem o", SelectionVariableItem.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            SelectionVariableItem attached = SelectionVariableItem.findSelectionVariableItem(this.id);
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
    public SelectionVariableItem merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SelectionVariableItem merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
