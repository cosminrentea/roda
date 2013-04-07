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
import ro.roda.domain.AuthData;

privileged aspect AuthData_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer AuthData.solrServer;
    
    public static QueryResponse AuthData.search(String queryString) {
        String searchString = "AuthData_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse AuthData.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void AuthData.indexAuthData(AuthData authData) {
        List<AuthData> authdatas = new ArrayList<AuthData>();
        authdatas.add(authData);
        indexAuthDatas(authdatas);
    }
    
    @Async
    public static void AuthData.indexAuthDatas(Collection<AuthData> authdatas) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (AuthData authData : authdatas) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "authdata_" + authData.getUserId());
            sid.addField("authData.users_t", authData.getUsers());
            sid.addField("authData.credentialprovider_s", authData.getCredentialProvider());
            sid.addField("authData.fieldname_s", authData.getFieldName());
            sid.addField("authData.fieldvalue_s", authData.getFieldValue());
            sid.addField("authData.userid_i", authData.getUserId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("authdata_solrsummary_t", new StringBuilder().append(authData.getUsers()).append(" ").append(authData.getCredentialProvider()).append(" ").append(authData.getFieldName()).append(" ").append(authData.getFieldValue()).append(" ").append(authData.getUserId()));
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
    public static void AuthData.deleteIndex(AuthData authData) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("authdata_" + authData.getUserId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void AuthData.postPersistOrUpdate() {
        indexAuthData(this);
    }
    
    @PreRemove
    private void AuthData.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer AuthData.solrServer() {
        SolrServer _solrServer = new AuthData().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
