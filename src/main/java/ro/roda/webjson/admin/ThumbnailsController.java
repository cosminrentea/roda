package ro.roda.webjson.admin;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

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

import ro.roda.domain.CmsFile;
import ro.roda.service.CmsFileService;
import ro.roda.service.filestore.CmsFileStoreService;
import ro.roda.service.thumbnails.ThumbnailsService;

@RequestMapping("/admin/thumbnail/alias")
@Controller
public class ThumbnailsController {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	ThumbnailsService thumbnailsService;

	@Autowired
	CmsFileService cmsFileService;

	@Autowired
	CmsFileStoreService cmsFileStoreService;

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
		byte[] bytes = null;
		try {

			CmsFile imageFile = CmsFile.findCmsFile(alias);

			String fileType = "";
			InputStream inputStream = null;
			if (imageFile != null) {
				String contentType = imageFile.getContentType();
				fileType = contentType.substring(contentType.lastIndexOf("/") + 1);
				inputStream = cmsFileStoreService.fileLoad(imageFile);

				bytes = thumbnailsService.generateThumbnailByHeightAndWidth(inputStream, fileType, height, width);
			}
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
		byte[] bytes = null;
		try {

			CmsFile imageFile = CmsFile.findCmsFile(alias);

			String fileType = "";
			InputStream inputStream = null;
			if (imageFile != null) {
				String contentType = imageFile.getContentType();
				fileType = contentType.substring(contentType.lastIndexOf("/") + 1);
				inputStream = cmsFileStoreService.fileLoad(imageFile);

				bytes = thumbnailsService.generateThumbnailProportionalToWidth(inputStream, fileType, width);
			}
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
		byte[] bytes = null;
		try {

			CmsFile imageFile = CmsFile.findCmsFile(alias);

			String fileType = "";
			InputStream inputStream = null;
			if (imageFile != null) {
				String contentType = imageFile.getContentType();
				fileType = contentType.substring(contentType.lastIndexOf("/") + 1);
				inputStream = cmsFileStoreService.fileLoad(imageFile);

				bytes = thumbnailsService.generateThumbnailProportionalToHeight(inputStream, fileType, height);
				if (bytes == null) {
					return new ResponseEntity<byte[]>(headers, HttpStatus.NOT_FOUND);
				}
			}
			return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<byte[]>(headers, HttpStatus.NOT_FOUND);
		}
	}

	private HttpHeaders getHttpHeaders(String alias) {
		CmsFile imageFile = cmsFileService.findCmsFile(alias);

		String fileType = "";
		if (imageFile != null) {
			String contentType = imageFile.getContentType();
			fileType = contentType.substring(contentType.lastIndexOf("/") + 1);
		}

		HttpHeaders headers = new HttpHeaders();

		switch (fileType.toLowerCase()) {
		case "png":
			headers.setContentType(MediaType.IMAGE_PNG);
			break;
		case "jpg":
		case "jpeg":
			headers.setContentType(MediaType.IMAGE_JPEG);
			break;
		}
		return headers;
	}
}
