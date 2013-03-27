package ro.roda.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class DatabaseUtils {

	private final Log log = LogFactory.getLog(DatabaseUtils.class);

	private String dbUsername;

	private String dbPassword;

	private String dbUrl;

	public DatabaseUtils(String dbUsername, String dbPassword, String dbUrl) {
		super();
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
		this.dbUrl = dbUrl;
	}

	/**
	 * Truncates the existing data in all the database tables, and restarts the
	 * associated sequences.
	 */
	public void truncate() {
		Connection con = null;
		try {
			Properties conProps = new Properties();
			conProps.put("user", this.dbUsername);
			conProps.put("password", this.dbPassword);
			con = DriverManager.getConnection(this.dbUrl, conProps);

			Statement stmt = null;
			try {
				stmt = con.createStatement();
				ResultSet rs = stmt
						.executeQuery("SELECT 'TRUNCATE TABLE ' || schemaname || '.' || tablename || ' RESTART IDENTITY CASCADE;' FROM pg_tables WHERE schemaname = 'public' OR schemaname = 'audit'");
				while (rs.next()) {
					String sqlCommand = rs.getString(1);
					log.info(sqlCommand);

					Statement stTruncate = con.createStatement();
					stTruncate.execute(sqlCommand);
					stTruncate.close();
				}
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
		} catch (SQLException e) {
			log.error("SQLException:", e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error("SQLException:", e);
				}
			}
		}
	}

	/**
	 * Populates the data in the database (data is taken from a directory of CSV
	 * files)
	 */
	public void initData(String csvDirname) {
		Connection con = null;
		try {
			Properties conProps = new Properties();
			conProps.put("user", this.dbUsername);
			conProps.put("password", this.dbPassword);
			con = DriverManager.getConnection(this.dbUrl, conProps);

			Resource csvRes = new ClassPathResource(csvDirname);
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
				log.info(f.getAbsolutePath());

				// Postgresql requires a Reader for the COPY commands
				BufferedReader br = new BufferedReader(new FileReader(f));

				// read the first line, containing the enumeration of fields
				String tableFields = br.readLine();

				// obtain the table name from the file name
				String tableName = f.getName().substring(2,
						f.getName().length() - 4);

				// bulk COPY the remaining lines (CSV data)
				String copyQuery = "COPY " + tableName + "(" + tableFields
						+ ") FROM stdin DELIMITERS ',' CSV";
				log.info(copyQuery);
				cm.copyIn(copyQuery, br);
//				br.close();
			}
		} catch (SQLException e) {
			log.error("SQLException:", e);
		} catch (IOException e) {
			log.error("IOException:", e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error("SQLException:", e);
				}
			}
		}
	}

	/**
	 * increments a sequence (managed by Hibernate) by a given value, and set a
	 * new increment. Code is Postgresql-specific.
	 * 
	 * @param sequence
	 *            the sequence name
	 * @param value
	 *            the amount by which the sequence is incremented (one time
	 *            only)
	 * @param increment
	 *            the final "increment" setting of the sequence
	 */
	public void setSequence(String sequence, int value, int increment) {
		Connection con = null;
		try {
			Properties conProps = new Properties();
			conProps.put("user", this.dbUsername);
			conProps.put("password", this.dbPassword);
			con = DriverManager.getConnection(this.dbUrl, conProps);

			Statement stmt = null;
			try {
				stmt = con.createStatement();
				
				// set the current/next value
				ResultSet rs = stmt.executeQuery("SELECT setval('" + sequence
						+ "',"+ value + ")");
				while (rs.next()) {
					int newValue = rs.getInt(1);
					log.info("sequence new value: " + sequence + " = "
							+ newValue);
				}

				// set the new increment
				stmt.executeUpdate("ALTER SEQUENCE " + sequence
						+ " INCREMENT BY " + increment);
				log.info("sequence new increment: " + sequence + " += "
						+ increment);
			} finally {
				if (stmt != null) {
					stmt.close();
				}
			}
		} catch (SQLException e) {
			log.error("SQLException:", e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					log.error("SQLException:", e);
				}
			}
		}
	}
}
