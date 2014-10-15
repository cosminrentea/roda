package ro.roda.service.importer;

import javax.xml.bind.Unmarshaller;

import org.springframework.web.multipart.MultipartFile;

import ro.roda.ddi.CodeBook;

public interface DdiImporterService {

	public abstract Unmarshaller getUnmarshaller() throws Exception;

	public abstract void importDdiFiles() throws Exception;

	public abstract void importDdiIntoCatalogsAndSeries() throws Exception;

	public abstract void importDdiFile(CodeBook cb, MultipartFile multipartFileDdi, boolean nesstarExported,
			boolean legacyDataRODA, boolean ddiPersistence, MultipartFile multipartFileCsv) throws Exception;

}
