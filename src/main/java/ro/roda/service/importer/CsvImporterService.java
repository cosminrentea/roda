package ro.roda.service.importer;

public interface CsvImporterService {

	public abstract void importCsv() throws Exception;

	public abstract void importCsvExtra() throws Exception;

	public abstract void importCsvDir(String dirname) throws Exception;

	public abstract void importCsvFile(String filename) throws Exception;

}
