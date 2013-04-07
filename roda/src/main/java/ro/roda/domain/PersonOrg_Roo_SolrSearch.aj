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
import ro.roda.domain.PersonOrg;

privileged aspect PersonOrg_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer PersonOrg.solrServer;
    
    public static QueryResponse PersonOrg.search(String queryString) {
        String searchString = "PersonOrg_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse PersonOrg.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void PersonOrg.indexPersonOrg(PersonOrg personOrg) {
        List<PersonOrg> personorgs = new ArrayList<PersonOrg>();
        personorgs.add(personOrg);
        indexPersonOrgs(personorgs);
    }
    
    @Async
    public static void PersonOrg.indexPersonOrgs(Collection<PersonOrg> personorgs) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (PersonOrg personOrg : personorgs) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "personorg_" + personOrg.getId());
            sid.addField("personOrg.orgid_t", personOrg.getOrgId());
            sid.addField("personOrg.personid_t", personOrg.getPersonId());
            sid.addField("personOrg.roleid_t", personOrg.getRoleId());
            sid.addField("personOrg.datestart_dt", personOrg.getDateStart());
            sid.addField("personOrg.dateend_dt", personOrg.getDateEnd());
            sid.addField("personOrg.id_t", personOrg.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("personorg_solrsummary_t", new StringBuilder().append(personOrg.getOrgId()).append(" ").append(personOrg.getPersonId()).append(" ").append(personOrg.getRoleId()).append(" ").append(personOrg.getDateStart()).append(" ").append(personOrg.getDateEnd()).append(" ").append(personOrg.getId()));
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
    public static void PersonOrg.deleteIndex(PersonOrg personOrg) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("personorg_" + personOrg.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void PersonOrg.postPersistOrUpdate() {
        indexPersonOrg(this);
    }
    
    @PreRemove
    private void PersonOrg.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer PersonOrg.solrServer() {
        SolrServer _solrServer = new PersonOrg().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
