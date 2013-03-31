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
import ro.roda.domain.UserMessage;

privileged aspect UserMessage_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer UserMessage.solrServer;
    
    public static QueryResponse UserMessage.search(String queryString) {
        String searchString = "UserMessage_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse UserMessage.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void UserMessage.indexUserMessage(UserMessage userMessage) {
        List<UserMessage> usermessages = new ArrayList<UserMessage>();
        usermessages.add(userMessage);
        indexUserMessages(usermessages);
    }
    
    @Async
    public static void UserMessage.indexUserMessages(Collection<UserMessage> usermessages) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (UserMessage userMessage : usermessages) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "usermessage_" + userMessage.getId());
            sid.addField("userMessage.fromuserid_t", userMessage.getFromUserId());
            sid.addField("userMessage.touserid_t", userMessage.getToUserId());
            sid.addField("userMessage.message_s", userMessage.getMessage());
            sid.addField("userMessage.id_i", userMessage.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("usermessage_solrsummary_t", new StringBuilder().append(userMessage.getFromUserId()).append(" ").append(userMessage.getToUserId()).append(" ").append(userMessage.getMessage()).append(" ").append(userMessage.getId()));
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
    public static void UserMessage.deleteIndex(UserMessage userMessage) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("usermessage_" + userMessage.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void UserMessage.postPersistOrUpdate() {
        indexUserMessage(this);
    }
    
    @PreRemove
    private void UserMessage.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer UserMessage.solrServer() {
        SolrServer _solrServer = new UserMessage().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
