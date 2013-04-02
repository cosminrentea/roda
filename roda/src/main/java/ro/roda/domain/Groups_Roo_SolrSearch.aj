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
import ro.roda.domain.Groups;

privileged aspect Groups_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer Groups.solrServer;
    
    public static QueryResponse Groups.search(String queryString) {
        String searchString = "Groups_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse Groups.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void Groups.indexGroups(Groups groups) {
        List<Groups> groupses = new ArrayList<Groups>();
        groupses.add(groups);
        indexGroupses(groupses);
    }
    
    @Async
    public static void Groups.indexGroupses(Collection<Groups> groupses) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Groups groups : groupses) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "groups_" + groups.getId());
            sid.addField("groups.groupname_s", groups.getGroupName());
            sid.addField("groups.id_l", groups.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("groups_solrsummary_t", new StringBuilder().append(groups.getGroupName()).append(" ").append(groups.getId()));
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
    public static void Groups.deleteIndex(Groups groups) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("groups_" + groups.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void Groups.postPersistOrUpdate() {
        indexGroups(this);
    }
    
    @PreRemove
    private void Groups.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer Groups.solrServer() {
        SolrServer _solrServer = new Groups().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}