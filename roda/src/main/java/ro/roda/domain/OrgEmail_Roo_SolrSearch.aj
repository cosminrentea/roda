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
import ro.roda.domain.OrgEmail;

privileged aspect OrgEmail_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer OrgEmail.solrServer;
    
    public static QueryResponse OrgEmail.search(String queryString) {
        String searchString = "OrgEmail_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse OrgEmail.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void OrgEmail.indexOrgEmail(OrgEmail orgEmail) {
        List<OrgEmail> orgemails = new ArrayList<OrgEmail>();
        orgemails.add(orgEmail);
        indexOrgEmails(orgemails);
    }
    
    @Async
    public static void OrgEmail.indexOrgEmails(Collection<OrgEmail> orgemails) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (OrgEmail orgEmail : orgemails) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "orgemail_" + orgEmail.getId());
            sid.addField("orgEmail.emailid_t", orgEmail.getEmailId());
            sid.addField("orgEmail.orgid_t", orgEmail.getOrgId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("orgemail_solrsummary_t", new StringBuilder().append(orgEmail.getEmailId()).append(" ").append(orgEmail.getOrgId()));
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
    public static void OrgEmail.deleteIndex(OrgEmail orgEmail) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("orgemail_" + orgEmail.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void OrgEmail.postPersistOrUpdate() {
        indexOrgEmail(this);
    }
    
    @PreRemove
    private void OrgEmail.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer OrgEmail.solrServer() {
        SolrServer _solrServer = new OrgEmail().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}