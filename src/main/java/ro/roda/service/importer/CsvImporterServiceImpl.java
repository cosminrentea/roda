package ro.roda.service.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CsvImporterServiceImpl implements CsvImporterService {

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

	@PersistenceContext
	transient EntityManager entityManager;

	// @Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importCsv() throws Exception {
		importCsvDir(rodaDataCsvDir);
	}

	// @Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importCsvExtra() throws Exception {
		importCsvDir(rodaDataCsvExtraDir);
	}

	/**
	 * Populates the database using data imported from a directory with CSV
	 * files (which are sorted by filename).
	 */
	public void importCsvDir(String dirname) throws Exception {
		log.trace("Importing CSV from directory: " + dirname);
		Connection con = null;
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

			// TODO de facut importul sa mearga cu UTF8 -
			// SET CLIENT ENCODING UTF8 ...

			// bulk COPY the remaining lines (CSV data)
			String copyQuery = "COPY " + tableName + "(" + tableFields + ") FROM stdin DELIMITERS ',' CSV";
			log.trace("Query:" + copyQuery);

			cm.copyIn(copyQuery, br);
		}
		con.close();
	}

	/**
	 * Populates a database table using data imported from a CSV file.
	 */
	public void importCsvFile(String filename) throws Exception {

		// get CSV file
		Resource csvRes = new ClassPathResource(filename);
		File f = csvRes.getFile();
		log.trace("Importing CSV file: " + f.getAbsolutePath());

		BufferedReader br = new BufferedReader(new FileReader(f));

		// read the first line, containing the enumeration of fields
		String tableFields = br.readLine();

		// obtain the table name from the file name (excluding ".CSV" ending)
		String tableName = f.getName().substring(0, f.getName().length() - 4);

		// bulk COPY the remaining lines (CSV data)
		String copyQuery = "COPY " + tableName + "(" + tableFields + ") FROM stdin DELIMITERS ',' CSV";
		log.trace("Query: " + copyQuery);

		// get connection + copymanager
		Connection con = null;
		Properties conProps = new Properties();
		conProps.put("user", this.dbUsername);
		conProps.put("password", this.dbPassword);
		con = DriverManager.getConnection(this.dbUrl, conProps);

		// execute query to copy/import all CSV data
		((BaseConnection) con).getCopyAPI().copyIn(copyQuery, br);

		con.close();
	}
}
