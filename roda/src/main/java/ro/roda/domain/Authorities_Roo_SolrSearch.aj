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
import ro.roda.domain.Authorities;

privileged aspect Authorities_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer Authorities.solrServer;
    
    public static QueryResponse Authorities.search(String queryString) {
        String searchString = "Authorities_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse Authorities.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void Authorities.indexAuthorities(Authorities authorities) {
        List<Authorities> authoritieses = new ArrayList<Authorities>();
        authoritieses.add(authorities);
        indexAuthoritieses(authoritieses);
    }
    
    @Async
    public static void Authorities.indexAuthoritieses(Collection<Authorities> authoritieses) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Authorities authorities : authoritieses) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "authorities_" + authorities.getId());
            sid.addField("authorities.username_t", authorities.getUsername());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("authorities_solrsummary_t", new StringBuilder().append(authorities.getUsername()));
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
    public static void Authorities.deleteIndex(Authorities authorities) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("authorities_" + authorities.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void Authorities.postPersistOrUpdate() {
        indexAuthorities(this);
    }
    
    @PreRemove
    private void Authorities.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer Authorities.solrServer() {
        SolrServer _solrServer = new Authorities().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}