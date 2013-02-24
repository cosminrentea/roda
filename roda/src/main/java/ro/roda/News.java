package ro.roda;

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
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.solr.RooSolrSearchable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.scheduling.annotation.Async;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "news", schema = "public")
@RooDbManaged(automaticallyDelete = true)
@RooSolrSearchable
public class News {

    @Autowired
    transient SolrServer solrServer;

    public static QueryResponse search(String queryString) {
        String searchString = "News_solrsummary_t:" + queryString;
        return search(new SolrQuery(searchString.toLowerCase()));
    }

    public static QueryResponse search(SolrQuery query) {
        try {
            return solrServer().query(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new QueryResponse();
    }

    public static void indexNews(ro.roda.News news) {
        List<News> newsList = new ArrayList<News>();
        newsList.add(news);
        indexNews(newsList);
    }

    @Async
    public static void indexNews(Collection<ro.roda.News> newsCollection) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (News news : newsCollection) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "news_" + news.getId());
            sid.addField("addedby_t", news.getAddedBy());
            sid.addField("added_dt", news.getAdded());
            sid.addField("title_s", news.getTitle());
            sid.addField("content_s", news.getContent());
            sid.addField("id_i", news.getId());
            sid.addField("news_solrsummary_t", new StringBuilder().append(news.getAddedBy()).append(" ").append(news.getAdded()).append(" ").append(news.getTitle()).append(" ").append(news.getContent()).append(" ").append(news.getId()));
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
    public static void deleteIndex(ro.roda.News news) {
        SolrServer solrServer = solrServer();
        try {
            solrServer.deleteById("news_" + news.getId());
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostUpdate
    @PostPersist
    private void postPersistOrUpdate() {
        indexNews(this);
    }

    @PreRemove
    private void preRemove() {
        deleteIndex(this);
    }

    public static SolrServer solrServer() {
        SolrServer _solrServer = new News().solrServer;
        if (_solrServer == null) throw new IllegalStateException("Solr server has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return _solrServer;
    }
}
