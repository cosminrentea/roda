// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import ro.roda.domain.AuditLogAction;

privileged aspect AuditLogAction_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer AuditLogAction.solrServer;
    
    public static QueryResponse AuditLogAction.search(String queryString) {
        String searchString = "AuditLogAction_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse AuditLogAction.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void AuditLogAction.indexAuditLogAction(AuditLogAction auditLogAction) {
        List<AuditLogAction> auditlogactions = new ArrayList<AuditLogAction>();
        auditlogactions.add(auditLogAction);
        indexAuditLogActions(auditlogactions);
    }
    
    @Async
    public static void AuditLogAction.indexAuditLogActions(Collection<AuditLogAction> auditlogactions) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (AuditLogAction auditLogAction : auditlogactions) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "auditlogaction_" + auditLogAction.getId());
            sid.addField("auditLogAction.changeset_t", auditLogAction.getChangeset());
            sid.addField("auditLogAction.auditedtable_t", auditLogAction.getAuditedTable());
            sid.addField("auditLogAction.auditedrow_s", auditLogAction.getAuditedRow());
            sid.addField("auditLogAction.type_s", auditLogAction.getType());
            sid.addField("auditLogAction.id_i", auditLogAction.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("auditlogaction_solrsummary_t", new StringBuilder().append(auditLogAction.getChangeset()).append(" ").append(auditLogAction.getAuditedTable()).append(" ").append(auditLogAction.getAuditedRow()).append(" ").append(auditLogAction.getType()).append(" ").append(auditLogAction.getId()));
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
    public static void AuditLogAction.deleteIndex(AuditLogAction auditLogAction) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("auditlogaction_" + auditLogAction.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void AuditLogAction.postPersistOrUpdate() {
        indexAuditLogAction(this);
    }
    
    @PreRemove
    private void AuditLogAction.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer AuditLogAction.solrServer() {
        SolrServer _solrServer = new AuditLogAction().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}