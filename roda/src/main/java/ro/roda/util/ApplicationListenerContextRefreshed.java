package ro.roda.util;

import java.io.File;
import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.thoughtworks.xstream.XStream;

import ro.roda.domain.Catalog;
import ro.roda.domain.CatalogStudy;
import ro.roda.domain.CatalogStudyPK;
import ro.roda.domain.Study;
import ro.roda.domain.Users;
import ro.roda.service.CatalogService;
import ro.roda.service.CatalogServiceImpl;
import ro.roda.service.UsersServiceImpl;

@Component
public class ApplicationListenerContextRefreshed implements
		ApplicationListener<ContextRefreshedEvent> {

	private final Log log = LogFactory
			.getLog(ApplicationListenerContextRefreshed.class);

	@Autowired
	BasicDataSource dataSource;

	@Autowired
	CatalogServiceImpl catalogService;

	@Autowired
	XStreamMarshaller xstreamMarshaller;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// root context
			log.info("event.getApplicationContext() = "
					+ event.getApplicationContext());
			String runMode = "server";
			try {
				// check if we are in "test mode"
				// (and the properties file has set a property)
				Resource resource = new ClassPathResource("/env.properties");
				Properties props = PropertiesLoaderUtils
						.loadProperties(resource);
				runMode = props.getProperty("run.mode");
			} catch (IOException ignored) {
			}
			log.info("run.mode = " + runMode);
			if ("server".equals(runMode)) {
				// log.error(dataSource.getUsername() + ":"
				// + dataSource.getPassword() + ":" + dataSource.getUrl());

				DatabaseUtils du = new DatabaseUtils(dataSource.getUsername(),
						dataSource.getPassword(), dataSource.getUrl());

				// DatabaseUtils du = new DatabaseUtils("roda", "roda",
				// "jdbc:postgresql://localhost:5432/roda");
				du.truncate();
				du.initData("csv/");
				du.setSequence("hibernate_sequence", 1000, 1);

				changeData();
				
				saveXstream();
			}
		}
	}
	
	@Transactional
	public void changeData() {

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
			CatalogStudyPK csid = new CatalogStudyPK(newCatalog.getId(),study.getId());
			cs.setId(csid);
			cs.setAdded(new GregorianCalendar());
			cs.persist();
		}
	
	}

	public void saveXstream() {
		for (Catalog c : catalogService.findAllCatalogs()) {
			File file = new File(c.getId() + ".xml");
			log.error("Catalog XML Filename: " + file.getAbsolutePath());
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
