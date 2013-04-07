// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ro.roda.domain.News;
import ro.roda.service.NewsService;
import ro.roda.service.UsersService;
import ro.roda.web.NewsController;

privileged aspect NewsController_Roo_Controller {
    
    @Autowired
    NewsService NewsController.newsService;
    
    @Autowired
    UsersService NewsController.usersService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String NewsController.create(@Valid News news, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, news);
            return "newspieces/create";
        }
        uiModel.asMap().clear();
        newsService.saveNews(news);
        return "redirect:/newspieces/" + encodeUrlPathSegment(news.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String NewsController.createForm(Model uiModel) {
        populateEditForm(uiModel, new News());
        return "newspieces/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String NewsController.show(@PathVariable("id") Integer id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("news", newsService.findNews(id));
        uiModel.addAttribute("itemId", id);
        return "newspieces/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String NewsController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("newspieces", newsService.findNewsEntries(firstResult, sizeNo));
            float nrOfPages = (float) newsService.countAllNewspieces() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("newspieces", newsService.findAllNewspieces());
        }
        addDateTimeFormatPatterns(uiModel);
        return "newspieces/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String NewsController.update(@Valid News news, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, news);
            return "newspieces/update";
        }
        uiModel.asMap().clear();
        newsService.updateNews(news);
        return "redirect:/newspieces/" + encodeUrlPathSegment(news.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String NewsController.updateForm(@PathVariable("id") Integer id, Model uiModel) {
        populateEditForm(uiModel, newsService.findNews(id));
        return "newspieces/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String NewsController.delete(@PathVariable("id") Integer id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        News news = newsService.findNews(id);
        newsService.deleteNews(news);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/newspieces";
    }
    
    void NewsController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("news_added_date_format", DateTimeFormat.patternForStyle("MM", LocaleContextHolder.getLocale()));
    }
    
    void NewsController.populateEditForm(Model uiModel, News news) {
        uiModel.addAttribute("news", news);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("userses", usersService.findAllUserses());
    }
    
    String NewsController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
