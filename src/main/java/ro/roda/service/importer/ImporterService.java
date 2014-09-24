package ro.roda.service.importer;

import javax.xml.bind.Unmarshaller;

import org.springframework.web.multipart.MultipartFile;

import ro.roda.ddi.CodeBook;

public interface ImporterService {

	public abstract Unmarshaller getUnmarshaller() throws Exception;

	public abstract void importElsst() throws Exception;

	public abstract void importCmsFiles() throws Exception;

	public abstract void importCmsLayouts() throws Exception;

	public abstract void importCmsSnippets() throws Exception;

	public abstract void importCmsPages() throws Exception;

	public abstract void importCsv() throws Exception;

	public abstract void importCsvExtra() throws Exception;

	public abstract void importCsvDir(String dirname) throws Exception;

	public abstract void importDdiFiles() throws Exception;

	public abstract void importDdiIntoCatalogsAndSeries() throws Exception;

	public abstract void importDdiFile(CodeBook cb, MultipartFile multipartFileDdi, boolean nesstarExported,
			boolean legacyDataRODA, boolean ddiPersistence, MultipartFile multipartFileCsv) throws Exception;

}
