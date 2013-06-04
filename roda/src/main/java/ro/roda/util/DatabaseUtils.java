package ro.roda.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import ro.roda.domain.Catalog;
import ro.roda.domain.CatalogStudy;
import ro.roda.domain.CatalogStudyPK;
import ro.roda.domain.Person;
import ro.roda.domain.Prefix;
import ro.roda.domain.Study;
import ro.roda.domain.Suffix;
import ro.roda.domain.Users;
import ro.roda.service.CatalogServiceImpl;
import ro.roda.service.StudyServiceImpl;

import com.thoughtworks.xstream.XStream;

@Component
public class DatabaseUtils {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	CatalogServiceImpl catalogService;

	@Autowired
	StudyServiceImpl studyService;

	@Autowired
	XStreamMarshaller xstreamMarshaller;

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
				ResultSet rs = stmt.executeQuery("SELECT setval('" + sequence
						+ "'," + value + ")");
				while (rs.next()) {
					int newValue = rs.getInt(1);
					log.trace("sequence new value: " + sequence + " = "
							+ newValue);
				}

				// set the new increment
				stmt.executeUpdate("ALTER SEQUENCE " + sequence
						+ " INCREMENT BY " + increment);
				log.trace("sequence new increment: " + sequence + " += "
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

	public void changeDataDemo() {

		Catalog oldCatalog = catalogService.findCatalog(new Integer(2));

		// add a new catalog
		Catalog newCatalog = new Catalog();
		newCatalog.setParentId(null);
		newCatalog.setName("root 2");
		newCatalog.setOwner(Users.findUsers(1));
		newCatalog.setAdded(new GregorianCalendar());
		newCatalog.persist();

		// move the old/existing catalog in the new folder
		oldCatalog.setParentId(newCatalog);
		HashSet<Catalog> children = new HashSet<Catalog>();
		children.add(oldCatalog);
		newCatalog.setCatalogs(children);

		oldCatalog.merge();

		// add all existing studies to the new catalog
		List<Study> ls = Study.findAllStudys();
		for (Study study : ls) {
			CatalogStudy cs = new CatalogStudy();
			CatalogStudyPK csid = new CatalogStudyPK(newCatalog.getId(),
					study.getId());
			cs.setId(csid);
			cs.setAdded(new GregorianCalendar());
			cs.persist();
		}

		// add the same Person entity: when there are similar fields -> only one
		// insertion

		Person p1 = new Person();
		p1.setFname("Ion");
		p1.setLname("VASILE");
		p1.setPrefixId(Prefix.findPrefix(1));
		p1.setSuffixId(Suffix.findSuffix(1));
		p1.persist();

		Person p2 = new Person();
		p2.setFname("Ionel");
		p2.setLname("Vasile");
		p2.setPrefixId(Prefix.findPrefix(1));
		p2.setSuffixId(Suffix.findSuffix(1));
		p2.persist();

		Person p3 = new Person();
		p3.setFname("Ion");
		p3.setLname("Vasile");
		p3.setPrefixId(Prefix.findPrefix(1));
		p3.setSuffixId(Suffix.findSuffix(1));

		Person resultPerson = null;
		for (Person p : Person.findAllPeople()) {
			if (p.equals(p3)) {
				resultPerson = p;
			}
		}

		if (resultPerson == null) {
			p3.persist();
		}
	}

	public void saveXstream() {
		for (Catalog c : catalogService.findAllCatalogs()) {
			File file = new File(c.getId() + ".xml");
			log.trace("Catalog XML Filename: " + file.getAbsolutePath());
			Result result;
			try {
				result = new StreamResult(new FileWriter(file));
				xstreamMarshaller.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
				xstreamMarshaller.marshal(c, result);
			} catch (XmlMappingException e) {
				// TODO Auto-generated catch block
				log.error("XmlMappingException:", e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("IOException:", e);
			}
		}
	}

}
