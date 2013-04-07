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
import ro.roda.domain.Study;

privileged aspect Study_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer Study.solrServer;
    
    public static QueryResponse Study.search(String queryString) {
        String searchString = "Study_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse Study.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void Study.indexStudy(Study study) {
        List<Study> studys = new ArrayList<Study>();
        studys.add(study);
        indexStudys(studys);
    }
    
    @Async
    public static void Study.indexStudys(Collection<Study> studys) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Study study : studys) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "study_" + study.getId());
            sid.addField("study.addedby_t", study.getAddedBy());
            sid.addField("study.datestart_dt", study.getDateStart());
            sid.addField("study.dateend_dt", study.getDateEnd());
            sid.addField("study.insertionstatus_i", study.getInsertionStatus());
            sid.addField("study.added_dt", study.getAdded().getTime());
            sid.addField("study.id_i", study.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("study_solrsummary_t", new StringBuilder().append(study.getAddedBy()).append(" ").append(study.getDateStart()).append(" ").append(study.getDateEnd()).append(" ").append(study.getInsertionStatus()).append(" ").append(study.getAdded().getTime()).append(" ").append(study.getId()));
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
    public static void Study.deleteIndex(Study study) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("study_" + study.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void Study.postPersistOrUpdate() {
        indexStudy(this);
    }
    
    @PreRemove
    private void Study.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer Study.solrServer() {
        SolrServer _solrServer = new Study().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
