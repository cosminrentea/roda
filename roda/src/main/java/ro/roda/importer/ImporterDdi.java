package ro.roda.importer;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import ro.roda.ddi.CodeBook;
import ro.roda.domain.Study;
import ro.roda.domain.StudyDescr;
import ro.roda.domain.StudyDescrPK;
import ro.roda.domain.TimeMeth;
import ro.roda.domain.UnitAnalysis;
import ro.roda.domain.Users;
import ro.roda.service.CatalogServiceImpl;
import ro.roda.service.StudyServiceImpl;

@Component
public class ImporterDdi {

	private final Log log = LogFactory.getLog(this.getClass());

	private static final String jaxbContextPath = "ro.roda.ddi";

	@Autowired
	CatalogServiceImpl catalogService;

	@Autowired
	StudyServiceImpl studyService;

	@Value("${roda.data.ddi.xsd}")
	private String xsdDdi122;

	/**
	 * Populates the database using data imported from a directory with DDI
	 * files exported from Nesstar Publisher.
	 * 
	 * @param dirname
	 *            the name of the directory containing DDI XML files (having
	 *            .xml extensions)
	 */
	public void importDdiAll(String dirname) {
		try {
			Resource ddiRes = new ClassPathResource(dirname);
			File ddiDir = ddiRes.getFile();
			File[] ddiFiles = ddiDir.listFiles();

			Resource xsdDdiRes = new ClassPathResource(xsdDdi122);
			// validate using DDI 1.2.2 XML Schema
			JAXBContext jc = JAXBContext.newInstance(jaxbContextPath);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
					xsdDdiRes.getFile()));

			for (File ddiFile : ddiFiles) {
				log.debug("File = " + ddiFile.getName());
				importCodebook((CodeBook) unmarshaller.unmarshal(ddiFile), true, true);
			}

		} catch (IOException e) {
			log.error("IOException:", e);
		} catch (JAXBException e) {
			log.error("JAXBException:", e);
		} catch (SAXException e) {
			log.error("SAXException:", e);
		}
	}

	public void importCodebook(CodeBook cb, boolean nesstarExported, boolean legacyDataRODA) {

		Study s = new Study();

		String title = cb.getDocDscr().get(0).getCitation().getTitlStmt().getTitl().getContent();
		log.debug("Title = " + title);

		if (nesstarExported && legacyDataRODA) {
			Date dateStart = null, dateEnd = null;
			Integer yearStart = null, yearEnd = null;

			Pattern pattern = Pattern.compile("\\d{4}");
			Matcher matcher = pattern.matcher(title);
			if (matcher.find()) {
				yearStart = new Integer(matcher.group());
				yearEnd = yearStart;
				Calendar cal = Calendar.getInstance();
				cal.set(yearStart.intValue(), 0, 1, 0, 0, 0);
				dateStart = cal.getTime();
				cal.set(yearEnd.intValue(), 11, 31, 23, 59, 59);
				dateEnd = cal.getTime();
			}
			s.setYearStart(yearStart);
			s.setYearEnd(yearEnd);
			s.setDateStart(dateStart);
			s.setDateEnd(dateEnd);

			log.debug("yearStart = " + yearStart);
			log.debug("yearEnd = " + yearEnd);
			log.debug("dateStart = " + dateStart);
			log.debug("dateEnd = " + dateEnd);
		}
		s.setAdded(new GregorianCalendar());

		// TODO check these default values
		s.setAnonymousUsage(true);
		s.setDigitizable(true);
		s.setRawData(true);
		s.setRawMetadata(false);
		s.setInsertionStatus(0);

		// user = admin
		Users u = Users.findUsers(1);
		s.setAddedBy(u);
		Set<Study> su = u.getStudies();
		su.add(s);
		u.setStudies(su);

		TimeMeth tm = TimeMeth.findTimeMeth(1);
		s.setTimeMethId(tm);
		Set<Study> tms = tm.getStudies();
		tms.add(s);
		tm.setStudies(tms);

		UnitAnalysis ua = UnitAnalysis.findUnitAnalysis(1);
		s.setUnitAnalysisId(ua);
		Set<Study> uas = ua.getStudies();
		uas.add(s);
		ua.setStudies(uas);

		s.persist();

		StudyDescr sd = new StudyDescr();
		// TODO get Romanian language, don't use ID = 1
		StudyDescrPK sdId = new StudyDescrPK(new Integer(1), s.getId());
		sd.setOriginalTitleLanguage(true);
		sd.setId(sdId);
		sd.setTitle(title);

		sd.persist();
	}
}
