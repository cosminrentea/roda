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
import ro.roda.domain.Skip;

privileged aspect Skip_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer Skip.solrServer;
    
    public static QueryResponse Skip.search(String queryString) {
        String searchString = "Skip_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse Skip.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void Skip.indexSkip(Skip skip) {
        List<Skip> skips = new ArrayList<Skip>();
        skips.add(skip);
        indexSkips(skips);
    }
    
    @Async
    public static void Skip.indexSkips(Collection<Skip> skips) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Skip skip : skips) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "skip_" + skip.getId());
            sid.addField("skip.id_l", skip.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("skip_solrsummary_t", new StringBuilder().append(skip.getId()));
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
    public static void Skip.deleteIndex(Skip skip) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("skip_" + skip.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void Skip.postPersistOrUpdate() {
        indexSkip(this);
    }
    
    @PreRemove
    private void Skip.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer Skip.solrServer() {
        SolrServer _solrServer = new Skip().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
