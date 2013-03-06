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
import ro.roda.domain.OrgRelations;

privileged aspect OrgRelations_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer OrgRelations.solrServer;
    
    public static QueryResponse OrgRelations.search(String queryString) {
        String searchString = "OrgRelations_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse OrgRelations.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void OrgRelations.indexOrgRelations(OrgRelations orgRelations) {
        List<OrgRelations> orgrelationses = new ArrayList<OrgRelations>();
        orgrelationses.add(orgRelations);
        indexOrgRelationses(orgrelationses);
    }
    
    @Async
    public static void OrgRelations.indexOrgRelationses(Collection<OrgRelations> orgrelationses) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (OrgRelations orgRelations : orgrelationses) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "orgrelations_" + orgRelations.getId());
            sid.addField("orgRelations.org2id_t", orgRelations.getOrg2Id());
            sid.addField("orgRelations.org1id_t", orgRelations.getOrg1Id());
            sid.addField("orgRelations.orgrelationtypeid_t", orgRelations.getOrgRelationTypeId());
            sid.addField("orgRelations.datestart_dt", orgRelations.getDatestart().getTime());
            sid.addField("orgRelations.dateend_dt", orgRelations.getDateend().getTime());
            sid.addField("orgRelations.details_s", orgRelations.getDetails());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("orgrelations_solrsummary_t", new StringBuilder().append(orgRelations.getOrg2Id()).append(" ").append(orgRelations.getOrg1Id()).append(" ").append(orgRelations.getOrgRelationTypeId()).append(" ").append(orgRelations.getDatestart().getTime()).append(" ").append(orgRelations.getDateend().getTime()).append(" ").append(orgRelations.getDetails()));
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
    public static void OrgRelations.deleteIndex(OrgRelations orgRelations) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("orgrelations_" + orgRelations.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void OrgRelations.postPersistOrUpdate() {
        indexOrgRelations(this);
    }
    
    @PreRemove
    private void OrgRelations.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer OrgRelations.solrServer() {
        SolrServer _solrServer = new OrgRelations().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
