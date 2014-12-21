package ro.roda.service.importer;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.Unmarshaller;

import org.springframework.web.multipart.MultipartFile;

import ro.roda.ddi.CodeBook;

public interface DdiImporterService {

	public abstract Unmarshaller getUnmarshaller() throws Exception;

	public abstract void importDdiFiles() throws Exception;

	public abstract void afterImport(List<String[]> csvLines) throws Exception;

	public abstract Integer importDdiFile(CodeBook cb, MultipartFile multipartFileDdi, String titleParameter,
			boolean nesstarExported, boolean legacyDataRODA, boolean ddiPersistence, MultipartFile multipartFileCsv,
			List<MultipartFile> multipartSyntax) throws Exception;

	public abstract Integer importDdiTestFile(String jsonName, InputStream is);

	public abstract Integer importDdiFileFromWeb(MultipartFile ddiMultipartFile, MultipartFile csvMultipartFile,
			List<MultipartFile> otherMultipartFiles);

}
