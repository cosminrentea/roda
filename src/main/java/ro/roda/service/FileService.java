package ro.roda.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.File;

public interface FileService {

	public abstract long countAllFiles();

	public abstract void deleteFile(File file);

	public abstract File findFile(Integer id);

	public abstract List<File> findAllFiles();

	public abstract List<File> findFileEntries(int firstResult, int maxResults);

	public abstract void saveFile(File file);

	public abstract void saveFile(File file, MultipartFile mFile, boolean importDdi);

	public abstract File updateFile(File file);

}
