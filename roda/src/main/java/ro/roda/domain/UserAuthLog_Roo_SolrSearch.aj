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
import ro.roda.domain.UserAuthLog;

privileged aspect UserAuthLog_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer UserAuthLog.solrServer;
    
    public static QueryResponse UserAuthLog.search(String queryString) {
        String searchString = "UserAuthLog_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse UserAuthLog.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void UserAuthLog.indexUserAuthLog(UserAuthLog userAuthLog) {
        List<UserAuthLog> userauthlogs = new ArrayList<UserAuthLog>();
        userauthlogs.add(userAuthLog);
        indexUserAuthLogs(userauthlogs);
    }
    
    @Async
    public static void UserAuthLog.indexUserAuthLogs(Collection<UserAuthLog> userauthlogs) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (UserAuthLog userAuthLog : userauthlogs) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "userauthlog_" + userAuthLog.getId());
            sid.addField("userAuthLog.userid_t", userAuthLog.getUserId());
            sid.addField("userAuthLog.authattemptedat_dt", userAuthLog.getAuthAttemptedAt().getTime());
            sid.addField("userAuthLog.action_s", userAuthLog.getAction());
            sid.addField("userAuthLog.credentialprovider_s", userAuthLog.getCredentialProvider());
            sid.addField("userAuthLog.credentialidentifier_s", userAuthLog.getCredentialIdentifier());
            sid.addField("userAuthLog.errormessage_s", userAuthLog.getErrorMessage());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("userauthlog_solrsummary_t", new StringBuilder().append(userAuthLog.getUserId()).append(" ").append(userAuthLog.getAuthAttemptedAt().getTime()).append(" ").append(userAuthLog.getAction()).append(" ").append(userAuthLog.getCredentialProvider()).append(" ").append(userAuthLog.getCredentialIdentifier()).append(" ").append(userAuthLog.getErrorMessage()));
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
    public static void UserAuthLog.deleteIndex(UserAuthLog userAuthLog) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("userauthlog_" + userAuthLog.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void UserAuthLog.postPersistOrUpdate() {
        indexUserAuthLog(this);
    }
    
    @PreRemove
    private void UserAuthLog.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer UserAuthLog.solrServer() {
        SolrServer _solrServer = new UserAuthLog().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}