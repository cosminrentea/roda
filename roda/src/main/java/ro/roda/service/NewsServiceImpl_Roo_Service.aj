// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.News;
import ro.roda.service.NewsServiceImpl;

privileged aspect NewsServiceImpl_Roo_Service {
    
    declare @type: NewsServiceImpl: @Service;
    
    declare @type: NewsServiceImpl: @Transactional;
    
    public long NewsServiceImpl.countAllNews() {
        return News.countNews();
    }
    
    public void NewsServiceImpl.deleteNews(News news) {
        news.remove();
    }
    
    public News NewsServiceImpl.findNews(Integer id) {
        return News.findNews(id);
    }
    
    public List<News> NewsServiceImpl.findAllNews() {
        return News.findAllNews();
    }
    
    public List<News> NewsServiceImpl.findNewsEntries(int firstResult, int maxResults) {
        return News.findNewsEntries(firstResult, maxResults);
    }
    
    public void NewsServiceImpl.saveNews(News news) {
        news.persist();
    }
    
    public News NewsServiceImpl.updateNews(News news) {
        return news.merge();
    }
    
}
