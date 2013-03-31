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
import ro.roda.domain.Value;

privileged aspect Value_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer Value.solrServer;
    
    public static QueryResponse Value.search(String queryString) {
        String searchString = "Value_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse Value.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void Value.indexValue(Value value) {
        List<Value> values = new ArrayList<Value>();
        values.add(value);
        indexValues(values);
    }
    
    @Async
    public static void Value.indexValues(Collection<Value> values) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Value value : values) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "value_" + value.getItemId());
            sid.addField("value.item_t", value.getItem());
            sid.addField("value.value_i", value.getValue());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("value_solrsummary_t", new StringBuilder().append(value.getItem()).append(" ").append(value.getValue()));
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
    public static void Value.deleteIndex(Value value) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("value_" + value.getItemId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void Value.postPersistOrUpdate() {
        indexValue(this);
    }
    
    @PreRemove
    private void Value.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer Value.solrServer() {
        SolrServer _solrServer = new Value().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
