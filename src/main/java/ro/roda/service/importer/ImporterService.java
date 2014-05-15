package ro.roda.service.importer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import ro.roda.ddi.CodeBook;

public interface ImporterService {

	public abstract Unmarshaller getUnmarshaller() throws JAXBException, SAXException, IOException;

	public abstract void importAll() throws IOException, SQLException, JAXBException, SAXException;

	public abstract void importCms() throws IOException;

	public abstract void importElsst() throws FileNotFoundException, IOException;

	public abstract void importCsv() throws SQLException, IOException;

	public abstract void importCsvExtra() throws SQLException, IOException;

	public abstract void importCsvAfterDdi() throws FileNotFoundException, IOException;

	public abstract void importCsvDir(String dirname) throws SQLException, IOException;

	public abstract void importDdiFiles() throws IOException, JAXBException, SAXException;

	public abstract void importCodebook(CodeBook cb, MultipartFile multipartFile, boolean nesstarExported,
			boolean legacyDataRODA);

}
