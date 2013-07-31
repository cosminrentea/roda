package ro.roda.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import ro.roda.domain.Catalog;
import ro.roda.domain.CatalogStudy;
import ro.roda.domain.CatalogStudyPK;
import ro.roda.domain.Study;

import au.com.bytecode.opencsv.CSVReader;

@Component
public class ImporterCsv {

	private final Log log = LogFactory.getLog(this.getClass());

	@Value("${database.username}")
	private String dbUsername;

	@Value("${database.password}")
	private String dbPassword;

	@Value("${database.url}")
	private String dbUrl;

	@Value("${roda.data.csv.dir}")
	private String rodaDataCsvDir;

	@Value("${roda.data.csv-extra.dir}")
	private String rodaDataCsvExtraDir;

	@Value("${roda.data.csv-after-ddi.catalog_study}")
	private String rodaDataCsvAfterDdiCatalogStudy;

	@Value("${roda.data.csv-after-ddi.series_study}")
	private String rodaDataCsvAfterDdiSeriesStudy;

	private static final String errorMessage = "Could not import CSV data";

	public void importCsv() {
		importCsvDir(rodaDataCsvDir);
	}

	public void importCsvExtra() {
		importCsvDir(rodaDataCsvExtraDir);
	}

	public void importCsvAfterDdi() {
		CSVReader reader;
		try {
			// add Studies to Catalogs
			reader = new CSVReader(new FileReader(new ClassPathResource(rodaDataCsvAfterDdiCatalogStudy).getFile()));
			List<String[]> csvLines;
			csvLines = reader.readAll();
			for (String[] csvLine : csvLines) {
				log.trace("Catalog " + csvLine[0] + " -> Study " + csvLine[1]);
				CatalogStudy cs = new CatalogStudy();
				cs.setId(new CatalogStudyPK(Integer.valueOf(csvLine[0]), Integer.valueOf(csvLine[1])));
				cs.persist();
			}

			// TODO finish code for adding Studies to Series
			reader = new CSVReader(new FileReader(new ClassPathResource(rodaDataCsvAfterDdiSeriesStudy).getFile()));
			csvLines = reader.readAll();
			for (String[] csvLine : csvLines) {
				log.trace("Series " + csvLine[0] + " -> Study " + csvLine[1]);
				CatalogStudy cs = new CatalogStudy();
				cs.setId(new CatalogStudyPK(Integer.valueOf(csvLine[0]), Integer.valueOf(csvLine[1])));
				cs.persist();
			}

		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException:", e);
		} catch (IOException e) {
			log.error("IOException:", e);
		}
	}

	/**
	 * Populates the database using data imported from a directory with CSV
	 * files (which are ordered by name).
	 */
	public void importCsvDir(String dirname) {
		log.trace("Importing CSV from directory: " + dirname);
		Connection con = null;
		try {
			Properties conProps = new Properties();
			conProps.put("user", this.dbUsername);
			conProps.put("password", this.dbPassword);
			con = DriverManager.getConnection(this.dbUrl, conProps);

			Resource csvRes = new ClassPathResource(dirname);
			File csvDir = csvRes.getFile();
			File[] csvFiles = csvDir.listFiles();

			// sort file list by file name, ascending
			Arrays.sort(csvFiles, new Comparator<File>() {
				public int compare(File f1, File f2) {
					return f1.getName().compareTo(f2.getName());
				}
			});

			CopyManager cm = ((BaseConnection) con).getCopyAPI();
			for (File f : csvFiles) {
				log.trace("File: " + f.getAbsolutePath());

				// Postgresql requires a Reader for the COPY commands
				BufferedReader br = new BufferedReader(new FileReader(f));

				// read the first line, containing the enumeration of fields
				String tableFields = br.readLine();

				// obtain the table name from the file name
				String tableName = f.getName().substring(2, f.getName().length() - 4);

				// bulk COPY the remaining lines (CSV data)
				String copyQuery = "COPY " + tableName + "(" + tableFields + ") FROM stdin DELIMITERS ',' CSV";
				log.trace(copyQuery);
				cm.copyIn(copyQuery, br);
				// br.close();
			}
		} catch (SQLException e) {
			log.error("SQLException:", e);
			throw new IllegalStateException(errorMessage);
		} catch (IOException e) {
			log.error("IOException:", e);
			throw new IllegalStateException(errorMessage);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error("SQLException:", e);
					throw new IllegalStateException(errorMessage);
				}
			}
		}
	}

}
