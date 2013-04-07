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
import ro.roda.domain.CmsPageType;

privileged aspect CmsPageType_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer CmsPageType.solrServer;
    
    public static QueryResponse CmsPageType.search(String queryString) {
        String searchString = "CmsPageType_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse CmsPageType.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void CmsPageType.indexCmsPageType(CmsPageType cmsPageType) {
        List<CmsPageType> cmspagetypes = new ArrayList<CmsPageType>();
        cmspagetypes.add(cmsPageType);
        indexCmsPageTypes(cmspagetypes);
    }
    
    @Async
    public static void CmsPageType.indexCmsPageTypes(Collection<CmsPageType> cmspagetypes) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (CmsPageType cmsPageType : cmspagetypes) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "cmspagetype_" + cmsPageType.getId());
            sid.addField("cmsPageType.name_s", cmsPageType.getName());
            sid.addField("cmsPageType.description_s", cmsPageType.getDescription());
            sid.addField("cmsPageType.id_i", cmsPageType.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("cmspagetype_solrsummary_t", new StringBuilder().append(cmsPageType.getName()).append(" ").append(cmsPageType.getDescription()).append(" ").append(cmsPageType.getId()));
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
    public static void CmsPageType.deleteIndex(CmsPageType cmsPageType) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("cmspagetype_" + cmsPageType.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void CmsPageType.postPersistOrUpdate() {
        indexCmsPageType(this);
    }
    
    @PreRemove
    private void CmsPageType.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer CmsPageType.solrServer() {
        SolrServer _solrServer = new CmsPageType().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
