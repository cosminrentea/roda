package ro.roda.web;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import ro.roda.domain.File;
import ro.roda.service.FileService;

@RequestMapping("/files")
@Controller
@RooWebScaffold(path = "files", formBackingObject = File.class, update = false)
public class FileController {

    @Autowired
    FileService fileService;

    private final Log log = LogFactory.getLog(FileController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid File file, BindingResult bindingResult, Model uiModel, @RequestParam("content") MultipartFile content, HttpServletRequest httpServletRequest) {
        log.debug("> create");
        uiModel.asMap().clear();
        fileService.saveFile(file, content);
        return "redirect:/files/" + encodeUrlPathSegment(file.getId().toString(), httpServletRequest);
    }

    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Integer id, Model uiModel) {
        log.debug("> show");
        File file = fileService.findFile(id);
        file.setUrl("showfile/" + id);
        uiModel.addAttribute("file", fileService.findFile(id));
        uiModel.addAttribute("itemId", id);
        return "files/show";
    }

    @RequestMapping(value = "/showfile/{id}", method = RequestMethod.GET)
    public String showfile(@PathVariable("id") Integer id, HttpServletResponse response, Model model) {
        log.debug("> showfile");
        File file = fileService.findFile(id);
        try {
            response.setHeader("Content-Disposition", "inline;filename=\"" + file.getName() + "\"");
            OutputStream out = response.getOutputStream();
            response.setContentType(file.getContentType());
            IOUtils.copy(new FileInputStream(file.getFullPath()), out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
