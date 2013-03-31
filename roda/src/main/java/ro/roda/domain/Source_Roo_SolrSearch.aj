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
import ro.roda.domain.Source;

privileged aspect Source_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer Source.solrServer;
    
    public static QueryResponse Source.search(String queryString) {
        String searchString = "Source_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse Source.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void Source.indexSource(Source source) {
        List<Source> sources = new ArrayList<Source>();
        sources.add(source);
        indexSources(sources);
    }
    
    @Async
    public static void Source.indexSources(Collection<Source> sources) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Source source : sources) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "source_" + source.getOrgId());
            sid.addField("source.org_t", source.getOrg());
            sid.addField("source.sourcetypeid_t", source.getSourcetypeId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("source_solrsummary_t", new StringBuilder().append(source.getOrg()).append(" ").append(source.getSourcetypeId()));
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
    public static void Source.deleteIndex(Source source) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("source_" + source.getOrgId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void Source.postPersistOrUpdate() {
        indexSource(this);
    }
    
    @PreRemove
    private void Source.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer Source.solrServer() {
        SolrServer _solrServer = new Source().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
