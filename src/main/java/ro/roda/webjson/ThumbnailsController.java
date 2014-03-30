package ro.roda.webjson;

import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import ro.roda.domain.CmsFile;
import ro.roda.service.CmsFileService;
import ro.roda.thumbnails.ThumbnailsService;

@RequestMapping("/admin/thumbnail/alias")
@Controller
public class ThumbnailsController {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	ThumbnailsService thumbnailsService;

	@Autowired
	CmsFileService cmsFileService;

	@RequestMapping(value = "/{alias}/h/{h}")
	@ResponseBody
	public ResponseEntity<byte[]> showImageByHeight(HttpServletRequest request, @PathVariable("alias") String alias,
			@PathVariable("h") Integer height) {

		return showImageByHeightAndWidth(request, alias, height, height);
	}

	@RequestMapping(value = "/{alias}/h/{h}/w/{w}")
	@ResponseBody
	public ResponseEntity<byte[]> showImageByHeightAndWidth(HttpServletRequest request,
			@PathVariable("alias") String alias, @PathVariable("h") Integer height, @PathVariable("w") Integer width) {

		HttpHeaders headers = getHttpHeaders(alias);
		try {
			byte[] bytes = thumbnailsService.generateThumbnailByHeightAndWidth(getBaseUrl(request), alias, height,
					width);
			if (bytes == null) {
				return new ResponseEntity<byte[]>(headers, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<byte[]>(headers, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{alias}/x/{x}")
	@ResponseBody
	public ResponseEntity<byte[]> showImageProportionalToWidth(HttpServletRequest request,
			@PathVariable("alias") String alias, @PathVariable("x") Integer width) {

		HttpHeaders headers = getHttpHeaders(alias);
		try {
			byte[] bytes = thumbnailsService.generateThumbnailProportionalToWidth(getBaseUrl(request), alias, width);
			if (bytes == null) {
				return new ResponseEntity<byte[]>(headers, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<byte[]>(headers, HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{alias}/y/{y}")
	@ResponseBody
	public ResponseEntity<byte[]> showImageProportionalToHeight(HttpServletRequest request,
			@PathVariable("alias") String alias, @PathVariable("y") Integer height) {

		HttpHeaders headers = getHttpHeaders(alias);
		try {
			byte[] bytes = thumbnailsService.generateThumbnailProportionalToHeight(getBaseUrl(request), alias, height);
			if (bytes == null) {
				return new ResponseEntity<byte[]>(headers, HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<byte[]>(headers, HttpStatus.NOT_FOUND);
		}
	}

	private String getBaseUrl(HttpServletRequest request) {
		String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

		StringBuilder relativePath = new StringBuilder();
		if (url != null) {
			for (int i = 0; i < StringUtils.countMatches(url.substring(1), "/"); i++) {
				// the URL starts with a "/"; this first "/" has to be ignored
				relativePath.append("../");
			}
		}

		URL baseUrl = null;
		try {
			// the base URL to whom the image file URL is relative to (ex:
			// http://localhost:8080/roda/)
			baseUrl = new URL(new URL(request.getRequestURL().toString()), relativePath.toString());
			return baseUrl.toString();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
		return null;
	}

	private HttpHeaders getHttpHeaders(String alias) {
		CmsFile imageFile = cmsFileService.findCmsFile(alias);

		String fileType = "";
		if (imageFile != null) {
			// TODO use the content_type field
			String fileName = imageFile.getFilename();
			fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		}

		HttpHeaders headers = new HttpHeaders();

		switch (fileType.toLowerCase()) {
		case "png":
			headers.setContentType(MediaType.IMAGE_PNG);
			break;
		case "jpg":
			headers.setContentType(MediaType.IMAGE_JPEG);
			break;
		}
		return headers;
	}
}
