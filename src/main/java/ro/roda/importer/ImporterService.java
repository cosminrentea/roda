package ro.roda.importer;

import javax.xml.bind.Unmarshaller;

import org.springframework.web.multipart.MultipartFile;

import ro.roda.ddi.CodeBook;

public interface ImporterService {

	public abstract Unmarshaller getUnmarshaller();

	public abstract void importAll();

	public abstract void importCms();

	public abstract void importElsst();

	public abstract void importCsv();

	public abstract void importCsvExtra();

	public abstract void importCsvAfterDdi();

	public abstract void importCsvDir(String dirname);

	public abstract void importDdiFiles();

	public abstract void importCodebook(CodeBook cb,
			MultipartFile multipartFile, boolean nesstarExported,
			boolean legacyDataRODA);

}
