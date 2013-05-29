package ro.roda.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import ro.roda.domain.Catalog;
import ro.roda.domain.CatalogStudy;
import ro.roda.domain.CatalogStudyPK;
import ro.roda.domain.Person;
import ro.roda.domain.Prefix;
import ro.roda.domain.Study;
import ro.roda.domain.Suffix;
import ro.roda.domain.Users;
import ro.roda.service.CatalogServiceImpl;

import ro.roda.ddi.CodeBook;

import com.thoughtworks.xstream.XStream;

@Component
public class DatabaseUtils {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	CatalogServiceImpl catalogService;

	@Autowired
	XStreamMarshaller xstreamMarshaller;

	@Value("${database.username}")
	private String dbUsername;

	@Value("${database.password}")
	private String dbPassword;

	@Value("${database.url}")
	private String dbUrl;

	@Value("${roda.data.ddi.xsd}")
	private String xsdDdi122;

	private static final String jaxbContextPath = "ro.roda.ddi";

	/**
	 * Populates the database using data imported from a directory with CSV
	 * files (which are ordered by name).
	 */
	public void importCsv(String dirname) {
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
				String tableName = f.getName().substring(2,
						f.getName().length() - 4);

				// bulk COPY the remaining lines (CSV data)
				String copyQuery = "COPY " + tableName + "(" + tableFields
						+ ") FROM stdin DELIMITERS ',' CSV";
				log.trace(copyQuery);
				cm.copyIn(copyQuery, br);
				// br.close();
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
	 * Populates the database using data imported from a directory with DDI
	 * files exported from Nesstar Publisher.
	 * 
	 * @param dirname
	 *            the name of the directory containing DDI XML files (having
	 *            .xml extensions)
	 */
	public void importDdi(String dirname) {
		log.debug("> importDdi");
		Connection con = null;
		try {
			Properties conProps = new Properties();
			conProps.put("user", this.dbUsername);
			conProps.put("password", this.dbPassword);
			con = DriverManager.getConnection(this.dbUrl, conProps);

			Resource ddiRes = new ClassPathResource(dirname);
			File ddiDir = ddiRes.getFile();
			File[] ddiFiles = ddiDir.listFiles();

			Resource xsdDdiRes = new ClassPathResource(xsdDdi122);
			// validate using DDI 1.2.2 XML Schema
			JAXBContext jc = JAXBContext.newInstance(jaxbContextPath);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setSchema(SchemaFactory.newInstance(
					XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
					xsdDdiRes.getFile()));

			for (File ddiFile : ddiFiles) {
				log.trace("File = " + ddiFile.getName());
				CodeBook cb = (CodeBook) unmarshaller.unmarshal(ddiFile);
				log.trace("Title = "
						+ cb.getDocDscr().get(0).getCitation().getTitlStmt()
								.getTitl().getContent());
			}

		} catch (SQLException e) {
			log.error("SQLException:", e);
		} catch (IOException e) {
			log.error("IOException:", e);
		} catch (JAXBException e) {
			log.error("JAXBException:", e);
		} catch (SAXException e) {
			log.error("SAXException:", e);
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
	 * Truncates the existing data in all the database tables, and restarts the
	 * associated sequences.
	 */
	public void truncate() {
		log.debug("> truncate");
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
		log.debug("> executeUpdate");
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
		log.debug(">saveXstream");
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

// public void populateJAXB () {
// JAXBContext jc;
// try {
// jc = JAXBContext.newInstance("ro.roda.domain");
// Marshaller marshaller = jc.createMarshaller();
// // validate using DDI 1.2.2 XML Schema
// // marshaller.setSchema(SchemaFactory.newInstance(
// // XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
// // new File(xsdDdi122)));
//
// // clean XML formatting
// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
// for (Catalog u:catalogService.findAllCatalogs()) {
//
// String filename = u.getId() + ".xml";
// File file = new File(filename);
// log.error("Catalog XML Filename: " + file.getAbsolutePath());
// // save the Catalog as XML
// marshaller.marshal(u, file);
// }
// } catch (JAXBException e) {
// log.error("SQLException:", e);
// }
// }
