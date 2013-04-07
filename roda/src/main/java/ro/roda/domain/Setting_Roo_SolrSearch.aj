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
import ro.roda.domain.Setting;

privileged aspect Setting_Roo_SolrSearch {
    
    @Autowired
    transient SolrServer Setting.solrServer;
    
    public static QueryResponse Setting.search(String queryString) {
        String searchString = "Setting_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }
    
    public static QueryResponse Setting.search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }
    
    public static void Setting.indexSetting(Setting setting) {
        List<Setting> settings = new ArrayList<Setting>();
        settings.add(setting);
        indexSettings(settings);
    }
    
    @Async
    public static void Setting.indexSettings(Collection<Setting> settings) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (Setting setting : settings) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "setting_" + setting.getId());
            sid.addField("setting.settingvalue_t", setting.getSettingValue());
            sid.addField("setting.settinggroup_t", setting.getSettingGroup());
            sid.addField("setting.name_s", setting.getName());
            sid.addField("setting.description_s", setting.getDescription());
            sid.addField("setting.predefinedvalues_s", setting.getPredefinedValues());
            sid.addField("setting.defaultvalue_s", setting.getDefaultValue());
            sid.addField("setting.id_i", setting.getId());
            // Add summary field to allow searching documents for objects of this type
            sid.addField("setting_solrsummary_t", new StringBuilder().append(setting.getSettingValue()).append(" ").append(setting.getSettingGroup()).append(" ").append(setting.getName()).append(" ").append(setting.getDescription()).append(" ").append(setting.getPredefinedValues()).append(" ").append(setting.getDefaultValue()).append(" ").append(setting.getId()));
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
    public static void Setting.deleteIndex(Setting setting) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("setting_" + setting.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @PostUpdate
    @PostPersist
    private void Setting.postPersistOrUpdate() {
        indexSetting(this);
    }
    
    @PreRemove
    private void Setting.preRemove() {
        deleteIndex(this);
    }
    
    public static SolrServer Setting.solrServer() {
        SolrServer _solrServer = new Setting().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
    
}
