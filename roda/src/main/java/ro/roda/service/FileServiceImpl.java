package ro.roda.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.File;


public class FileServiceImpl implements FileService {
	
	private final Log log = LogFactory.getLog(FileServiceImpl.class);
	
	private final static String baseFolder = "/tmp/";
	
	public void saveFile(File file, MultipartFile content) {
		log.debug("> saveFile");
		if (content != null) {
			try {
				// set object properties
				file.setName(content.getOriginalFilename());
				file.setSize(content.getSize());
				String fullPath = baseFolder
						+ content.getOriginalFilename();
				file.setFullPath(fullPath);
				
				// transfer the uploaded file to local folder/repository
				content.transferTo(new java.io.File(fullPath));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		saveFile(file);
		log.trace("Saved: " + file);
	}
}
