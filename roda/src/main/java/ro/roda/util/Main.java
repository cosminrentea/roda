package ro.roda.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import ro.roda.domain.Catalog;

public class Main {

	// TODO move to external properties file
	private static final String xsdDdi25 = "xsd/Catalog.xsd";
	private static final String xsdDdi122 = "xsd/ddi122.xsd";
	private static final String rodaNesstar = "data/roda-nesstar/";
	private static final String rodaNesstarDbOutput = "./";
	private static final String jaxbContextPath = "ro.roda.domain";
	private static final String jpaPersistenceUnitName = "persistenceUnit";

	private boolean executionTimeMeasurement;
	private long startTime;
	private long endTime;

	public Main(boolean executionTimeMeasurement) {
		super();
		this.executionTimeMeasurement = executionTimeMeasurement;
	}

	public static void main(String[] args) throws SAXException, IOException {

		// TODO better validation
		if (args.length == 0) {
			usage();
			System.exit(1);
		}

		Main rt = new Main(true);

		if (rt.executionTimeMeasurement) {
			rt.startTime = System.nanoTime();
		}

		// TODO use Apache Commons CLI, http://commons.apache.org/cli/

		try {
			if ("-i1".compareTo(args[0]) == 0) {
				rt.importOneToDb(args[1], args[2], args[3], args[4], false);
			} else if ("-i".compareTo(args[0]) == 0) {
				rt.importAllToDb(args[1], args[2], args[3]);
			} else if ("-e".compareTo(args[0]) == 0) {
				rt.exportAllFromDb();
			} else if ("-g".compareTo(args[0]) == 0) {
				rt.generateSchema(new Ddi122SchemaOutputResolver());
			} else if ("-v1".compareTo(args[0]) == 0) {
				for (String xmlFilename : getAllFilenames(args[1], ".XML")) {
					rt.validateDdi122(xmlFilename);
				}
			} else if ("-v2".compareTo(args[0]) == 0) {
				for (String xmlFilename : getAllFilenames(args[1], ".XML")) {
					rt.validateDdi25(xmlFilename);
				}
			} else if ("-c".compareTo(args[0]) == 0) {
				String[] filenames = getAllFilenames(args[1], ".XML");
				int i;
				for (i = 0; i < filenames.length; i++) {
					rt.convertNesstarToDdi25(args[1] + "/" + filenames[i], args[2] + "/" + filenames[i]);
				}
			} else if ("-s".compareTo(args[0]) == 0) {
				// String[] filenames = getAllFilenames(args[1], ".SAV");
				// int i;
				// for (i = 0; i < filenames.length; i++) {
				// rt.convertSpssToAsciiDelimited(
				// args[1] + "/" + filenames[i], args[2] + "/"
				// + filenames[i]);
				// }
			} else {
				usage();
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		if (rt.executionTimeMeasurement) {
			rt.endTime = System.nanoTime();
			System.err.println("Duration: " + ((rt.endTime - rt.startTime) / 1000000000) + "."
					+ ((rt.endTime - rt.startTime) % 1000000000) / 1000000 + " s ");
		}
	}

	private static void usage() {
		System.out.println("Usage: ");
		System.out.println("-i1\timport one file to DB");
		System.out.println("-i\timport all to DB");
		System.out.println("-e\texport all from DB");
		System.out.println("-g\tGENERATE SCHEMA from JAXB Java classes");
		System.out.println("-v1\tVALIDATE DDI 1.2.2\tparameter 1 : [XML file] | [XML dir]");
		System.out.println("-v2\tVALIDATE DDI 2.5\tparameter 1 : [XML file] | [XML dir]");
		System.out.println("-s\tSPSS\t parameter 1 : [SPSS .SAV file] | [SPSS dir]");
		System.out.println("-c\tCONVERT\tparameter 1: [source XML file] | [source XML dir]");
		System.out.println("\t\t parameter 2: [converted XML file] | [converted XML dir]");
	}

	private static String[] getAllFilenames(String filename, String extension) {
		File file = new File(filename);
		String[] filenames = file.list(new RodaFilenameFilter(extension));
		if (filenames == null) {
			// filename is not a folder, but a regular file
			filenames = new String[1];
			filenames[0] = filename;
		}
		return filenames;
	}

	private void validateDdi25(String filename) throws SAXException, IOException {

		Validator validatorDdi25 = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
				.newSchema(new File(xsdDdi25)).newValidator();

		File file = new File(filename);
		Source source = new StreamSource(file);
		try {
			validatorDdi25.validate(source);
			System.out.println(file.getAbsolutePath() + " : VALID");
		} catch (SAXException e) {
			System.err.println(file.getAbsolutePath() + " : NOT VALID because");
			System.err.println(e.getMessage());
		}
	}

	private void validateDdi122(String filename) throws SAXException, IOException {

		Validator validatorDdi122 = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
				.newSchema(new File(xsdDdi122)).newValidator();

		File file = new File(filename);
		Source source = new StreamSource(file);
		try {
			validatorDdi122.validate(source);
			System.out.println(file.getAbsolutePath() + " : VALID");
		} catch (SAXException e) {
			System.err.println(file.getAbsolutePath() + " : NOT VALID because");
			System.err.println(e.getMessage());
		}
	}

	private void convertNesstarToDdi25(String filenameInput, String filenameOutput) throws SAXException, IOException {
		BufferedReader br;
		FileWriter fw = new FileWriter(filenameOutput);
		String line;
		boolean insideTag = false, afterTag = false;

		br = new BufferedReader(new InputStreamReader(new FileInputStream(filenameInput), Charset.forName("UTF-8")));
		while ((line = br.readLine()) != null) {
			if (afterTag) {
				fw.write(line + "\n");
			}
			if (line.matches(".*<Catalog .*")) {
				insideTag = true;
			}
			if (insideTag && line.endsWith(">")) {
				insideTag = false;
				afterTag = true;
				fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
				fw.write("<Catalog xmlns=\"ddi:Catalog:2_5\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"ddi:Catalog:2_5 Catalog.xsd\" version=\"2.5\">\n");
			}
		}
		br.close();
		fw.close();
		// validate(filenameOutput);
	}

	private void exportAllFromDb() throws JAXBException, IOException, SAXException {

		// override DDL generation
		// make sure tables are _not_ created / dropped
		// we just want to use the available data
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("eclipselink.ddl-generation", "none");
		// TODO Cosmin: add logging level control

		properties.put("javax.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/roda");

		properties.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/roda");

		properties.put("hibernate.connection.username", "roda");

		properties.put("hibernate.connection.password", "roda");

		EntityManagerFactory emf = Persistence.createEntityManagerFactory(jpaPersistenceUnitName, properties);

		EntityManager em = emf.createEntityManager();

		JAXBContext jc = JAXBContext.newInstance(jaxbContextPath);

		Marshaller marshaller = jc.createMarshaller();
		// validate using DDI 1.2.2 XML Schema
		// marshaller.setSchema(SchemaFactory.newInstance(
		// XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
		// new File(xsdDdi122)));

		// clean XML formatting
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		em.getTransaction().begin();

		// get the Catalog which was saved in database
		Query query = em.createQuery("select c from Catalog c");

		em.getTransaction().commit();

		// iterate over results
		for (Catalog cb : (List<Catalog>) query.getResultList()) {

			String filename = cb.getId() + ".xml";
			System.err.println(filename);
			// save the Catalog as XML
			marshaller.marshal(cb, new File(rodaNesstarDbOutput + filename));

			// optional gentle suggestion :)
			System.gc();
		}

		em.close();
		emf.close();
	}

	/**
	 * @throws JAXBException
	 * @throws IOException
	 * @throws SAXException
	 */
	private void importAllToDb(String jdbcUrl, String username, String userpassword) throws JAXBException, IOException,
			SAXException {
		EntityManagerFactory emf;
		EntityManager em;

		JAXBContext jc = JAXBContext.newInstance(jaxbContextPath);

		Unmarshaller unmarshaller = jc.createUnmarshaller();

		// validate using DDI 1.2.2 XML Schema
		unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
				new File(xsdDdi122)));

		File file = new File(rodaNesstar);
		String[] filenames = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File f, String s) {
				if (s.toUpperCase().endsWith(".XML"))
					return true;
				return false;
			}
		});

		if (filenames != null) {

			// override DDL generation type
			HashMap<String, Object> properties = new HashMap<String, Object>();
			properties.put("eclipselink.ddl-generation", "drop-and-create-tables");
			properties.put("hibernate.hbm2ddl.auto", "create");
			// TODO Cosmin: add logging level control

			emf = Persistence.createEntityManagerFactory(jpaPersistenceUnitName, properties);

			em = emf.createEntityManager();

			for (String filename : filenames) {
				System.err.println(filename);

				Catalog cb = (Catalog) unmarshaller.unmarshal(new File(rodaNesstar + filename));

				em.getTransaction().begin();
				em.persist(cb);
				em.getTransaction().commit();

				// optional
				System.gc();

				// // get the Catalog which was saved in database
				// Query query = em.createQuery("select c from Catalog c");
				// Catalog result = (Catalog) query.getResultList().get(0);
				//
				// // save the Catalog as XML
				// marshaller.marshal(result, new File(rodaNesstarDb+filename));
			}
			em.close();
			emf.close();
		}

	}

	private void importOneToDb(String jdbcUrl, String username, String userpassword, String fname, boolean importSpss)
			throws JAXBException, IOException, SAXException {

		EntityManagerFactory emf;
		EntityManager em;

		JAXBContext jc = JAXBContext.newInstance(jaxbContextPath);

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		// validate using DDI 1.2.2 XML Schema
		unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
				new File(xsdDdi122)));

		String filename = fname;
		File file = new File(filename);

		// properties for JPA
		HashMap<String, Object> properties = new HashMap<String, Object>();

		properties.put("javax.persistence.jdbc.url", jdbcUrl);

		properties.put("hibernate.connection.url", jdbcUrl);

		properties.put("hibernate.connection.username", username);

		properties.put("hibernate.connection.password", userpassword);

		// override DDL generation type

		// eclipselink
		properties.put("eclipselink.ddl-generation", "none");

		// hibernate
		properties.put("hibernate.hbm2ddl.auto", "update");

		// TODO Cosmin: add logging level control

		emf = Persistence.createEntityManagerFactory(jpaPersistenceUnitName, properties);

		em = emf.createEntityManager();

		System.err.println(filename);

		Catalog cb = (Catalog) unmarshaller.unmarshal(file);

		em.getTransaction().begin();
		em.persist(cb);
		em.getTransaction().commit();

		// optional
		System.gc();

		if (importSpss) {

			// em.getTransaction().begin();
			//
			// Query query = em.createQuery("select f from FileTxtType f");
			//
			// em.getTransaction().commit();
			//
			// // iterate over results
			// for (FileTxtType f : (List<FileTxtType>) query.getResultList()) {
			//
			// String datafilename = f.getFileName().getContent();
			// System.err.println(datafilename);
			//
			// if (datafilename != null && datafilename.endsWith(".sav")) {
			// try {
			//
			// // TODO eliminate hard-coding
			// SPSSFile sf = new SPSSFile("data/roda-nesstar/"
			// + datafilename);
			// sf.loadMetadata();
			// sf.loadData();
			//
			// // verbose logging
			// // sf.dumpMetadata();
			//
			// sf.dumpData();
			//
			// f.setSpssfile(sf);
			//
			// em.getTransaction().begin();
			// em.persist(f);
			// em.getTransaction().commit();
			//
			// } catch (FileNotFoundException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (SPSSFileException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// }
			//
			// // optional gentle suggestion :)
			// System.gc();
			// }
		}

		em.close();
		emf.close();

	}

	private void generateSchema(SchemaOutputResolver sor) throws JAXBException, IOException, SAXException {
		JAXBContext jc = JAXBContext.newInstance(jaxbContextPath);
		jc.generateSchema(sor);
	}
}
