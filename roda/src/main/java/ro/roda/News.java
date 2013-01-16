package ro.roda;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.solr.client.solrj.SolrServer;
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

    public static void indexNews(ro.roda.News news) {
        List<News> newsList = new ArrayList<News>();
        newsList.add(news);
        indexNews(newsList);
    }

    @Async
    public static void indexNews(Collection<ro.roda.News> newsColl) {
        List<SolrInputDocument> documents = new ArrayList<SolrInputDocument>();
        for (News news : newsColl) {
            SolrInputDocument sid = new SolrInputDocument();
            sid.addField("id", "news_" + news.getId());
            sid.addField("news.id_i", news.getId());
            sid.addField("news_solrsummary_t", new StringBuilder().append(news.getId()));
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
}
