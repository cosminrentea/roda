package ro.roda.service;

import org.springframework.roo.addon.layers.service.RooService;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.File;

@RooService(domainTypes = { ro.roda.domain.File.class })
public interface FileService {
	
	public abstract void saveFile(File file, MultipartFile mFile);
	
}
