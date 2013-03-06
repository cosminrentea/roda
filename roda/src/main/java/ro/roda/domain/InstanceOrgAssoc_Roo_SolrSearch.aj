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
import ro.roda.domain.InstanceOrgAssoc;

privileged aspect InstanceOrgAssoc_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer InstanceOrgAssoc.solrServer;
    
    public static QueryResponse InstanceOrgAssoc.search(String queryString) {
        String searchString = "InstanceOrgAssoc_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse InstanceOrgAssoc.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void InstanceOrgAssoc.indexInstanceOrgAssoc(InstanceOrgAssoc instanceOrgAssoc) {
        List<InstanceOrgAssoc> instanceorgassocs = new ArrayList<InstanceOrgAssoc>();
        instanceorgassocs.add(instanceOrgAssoc);
        indexInstanceOrgAssocs(instanceorgassocs);
    }
    
    @Async
    public static void InstanceOrgAssoc.indexInstanceOrgAssocs(Collection<InstanceOrgAssoc> instanceorgassocs) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (InstanceOrgAssoc instanceOrgAssoc : instanceorgassocs) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "instanceorgassoc_" + instanceOrgAssoc.getId());
            sid.addField("instanceOrgAssoc.assocname_s", instanceOrgAssoc.getAssocName());
            sid.addField("instanceOrgAssoc.assocdescription_s", instanceOrgAssoc.getAssocDescription());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("instanceorgassoc_solrsummary_t", new StringBuilder().append(instanceOrgAssoc.getAssocName()).append(" ").append(instanceOrgAssoc.getAssocDescription()));
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
    public static void InstanceOrgAssoc.deleteIndex(InstanceOrgAssoc instanceOrgAssoc) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("instanceorgassoc_" + instanceOrgAssoc.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void InstanceOrgAssoc.postPersistOrUpdate() {
        indexInstanceOrgAssoc(this);
    }
    
    @PreRemove
    private void InstanceOrgAssoc.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer InstanceOrgAssoc.solrServer() {
        SolrServer _solrServer = new InstanceOrgAssoc().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
