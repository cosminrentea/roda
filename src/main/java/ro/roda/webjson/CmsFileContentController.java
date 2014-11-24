package ro.roda.webjson;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import ro.roda.domain.CmsFile;
import ro.roda.service.filestore.CmsFileStoreService;

@RequestMapping("/cmsfilecontent")
@Controller
public class CmsFileContentController {

	@Autowired
	CmsFileStoreService cmsFileStoreService;

	private final Log log = LogFactory.getLog(this.getClass());

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	@RequestMapping(value = "/{id}")
	public String fileContent(@PathVariable("id") Integer id, HttpServletResponse response) {
		try {
			CmsFile cmsFile = CmsFile.findCmsFile(id);
			if (cmsFile != null) {
				InputStream is = cmsFileStoreService.fileLoad(cmsFile);
				if (is != null) {
					OutputStream out = response.getOutputStream();
					response.setHeader("Content-Disposition", "inline;filename=\"" + cmsFile.getFilename() + "\"");
					response.setContentType(cmsFile.getContentType());
					IOUtils.copy(is, out);
					out.flush();
				}
			}
		} catch (IOException e) {
			log.error("Exception when returning file content", e);
		}
		return null;
	}

	@RequestMapping(value = "/alias/{alias}")
	public String fileContentByAlias(@PathVariable("alias") String alias, HttpServletResponse response) {
		try {
			CmsFile cmsFile = CmsFile.findCmsFile(alias);
			if (cmsFile != null) {
				InputStream is = cmsFileStoreService.fileLoad(cmsFile);
				if (is != null) {
					OutputStream out = response.getOutputStream();
					response.setHeader("Content-Disposition", "inline;filename=\"" + cmsFile.getFilename() + "\"");
					response.setContentType(cmsFile.getContentType());
					IOUtils.copy(is, out);
					out.flush();
				}
			}
		} catch (IOException e) {
			log.error("Exception when returning file content", e);
		}
		return null;
	}

}
