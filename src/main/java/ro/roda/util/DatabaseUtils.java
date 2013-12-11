package ro.roda.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUtils {

	private final Log log = LogFactory.getLog(this.getClass());

	@Value("${database.username}")
	private String dbUsername;

	@Value("${database.password}")
	private String dbPassword;

	@Value("${database.url}")
	private String dbUrl;

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
						.executeQuery("SELECT 'TRUNCATE TABLE ' || schemaname || '.' || tablename || ' RESTART IDENTITY CASCADE;' FROM pg_tables WHERE schemaname = 'public' OR schemaname = 'audit' OR schemaname = 'ddi'");
				while (rs.next()) {
					String sqlCommand = rs.getString(1);
					log.trace(sqlCommand);

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

	public void executeUpdate(String sqlCommand) {
		Connection con = null;
		try {
			Properties conProps = new Properties();
			conProps.put("user", this.dbUsername);
			conProps.put("password", this.dbPassword);
			con = DriverManager.getConnection(this.dbUrl, conProps);
			Statement stmt = null;
			try {
				stmt = con.createStatement();
				stmt.executeUpdate(sqlCommand);
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
	 * increments a sequence (managed by Hibernate) by a given value, and set a
	 * new increment. The code is Postgresql-specific.
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
				ResultSet rs = stmt.executeQuery("SELECT setval('" + sequence + "'," + value + ")");
				while (rs.next()) {
					int newValue = rs.getInt(1);
					log.trace("sequence new value: " + sequence + " = " + newValue);
				}

				// set the new increment
				stmt.executeUpdate("ALTER SEQUENCE " + sequence + " INCREMENT BY " + increment);
				log.trace("sequence new increment: " + sequence + " += " + increment);
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
