package ro.roda.importer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import ro.roda.ddi.AuthEntyType;
import ro.roda.ddi.CitationType;
import ro.roda.ddi.CodeBook;
import ro.roda.ddi.DataCollType;
import ro.roda.ddi.DataDscrType;
import ro.roda.ddi.DocDscrType;
import ro.roda.ddi.KeywordType;
import ro.roda.ddi.MethodType;
import ro.roda.ddi.ProdPlacType;
import ro.roda.ddi.ProdStmtType;
import ro.roda.ddi.RspStmtType;
import ro.roda.ddi.StdyDscrType;
import ro.roda.ddi.StdyInfoType;
import ro.roda.ddi.SubjectType;
import ro.roda.ddi.SumDscrType;
import ro.roda.ddi.TopcClasType;
import ro.roda.ddi.VarType;
import ro.roda.domain.Address;
import ro.roda.domain.CatalogStudy;
import ro.roda.domain.CatalogStudyPK;
import ro.roda.domain.City;
import ro.roda.domain.Instance;
import ro.roda.domain.InstanceVariable;
import ro.roda.domain.InstanceVariablePK;
import ro.roda.domain.Keyword;
import ro.roda.domain.Study;
import ro.roda.domain.StudyDescr;
import ro.roda.domain.StudyDescrPK;
import ro.roda.domain.StudyKeyword;
import ro.roda.domain.StudyKeywordPK;
import ro.roda.domain.TimeMeth;
import ro.roda.domain.UnitAnalysis;
import ro.roda.domain.Users;
import ro.roda.domain.Variable;
import ro.roda.service.CatalogServiceImpl;
import ro.roda.service.StudyServiceImpl;

@Component
public class ImporterDdi {

	private final Log log = LogFactory.getLog(this.getClass());

	private static final String jaxbContextPath = "ro.roda.ddi";

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired
	CatalogServiceImpl catalogService;

	@Autowired
	StudyServiceImpl studyService;

	@Value("${roda.data.ddi.xsd}")
	private String xsdDdi122;

	@Value("${roda.data.ddi.persist}")
	private String ddiPersist;

	@Value("${roda.data.ddi.files}")
	private String rodaDataDdiFiles;

	private static final String errorMessage = "Could not import DDI data";

	public static final EntityManager entityManager() {
		EntityManager em = new ImporterDdi().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	/**
	 * Populates the database using data imported from a directory with DDI
	 * files exported from Nesstar Publisher.
	 */
	public void importDdiFiles() {
		try {
			log.trace("roda.data.ddi.files = " + rodaDataDdiFiles);

			// validate using DDI 1.2.2 XML Schema
			Resource xsdDdiRes = new ClassPathResource(xsdDdi122);
			JAXBContext jc = JAXBContext.newInstance(jaxbContextPath);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
					xsdDdiRes.getFile()));

			PathMatchingResourcePatternResolver pmr = new PathMatchingResourcePatternResolver();
			Resource[] resources = pmr.getResources("classpath*:" + rodaDataDdiFiles);
			if (resources.length == 0) {
				log.debug("No DDI files found for importing");
			}

			for (Resource ddiResource : resources) {
				File ddiFile = ddiResource.getFile();
				log.debug("Importing DDI file: " + ddiFile.getName());
				CodeBook cb = (CodeBook) unmarshaller.unmarshal(ddiFile);
				importCodebook(cb, true, true);
				if ("yes".equalsIgnoreCase(ddiPersist)) {
					if (this.entityManager == null) {
						this.entityManager = entityManager();
					}
					this.entityManager.persist(cb);
				}
			}
		} catch (IOException e) {
			log.error("IOException:", e);
			throw new IllegalStateException(errorMessage);
		} catch (JAXBException e) {
			log.error("JAXBException:", e);
			throw new IllegalStateException(errorMessage);
		} catch (SAXException e) {
			log.error("SAXException:", e);
			throw new IllegalStateException(errorMessage);
		}
	}

	public void importCodebook(CodeBook cb, boolean nesstarExported, boolean legacyDataRODA) {

		DocDscrType docDscrType = null;
		if (cb.getDocDscr().size() > 0) {
			docDscrType = cb.getDocDscr().get(0);
		}

		DataDscrType dataDscrType = null;
		if (cb.getDataDscr().size() > 0) {
			dataDscrType = cb.getDataDscr().get(0);
		}

		StdyDscrType stdyDscrType = cb.getStdyDscr().get(0);
		StdyInfoType stdyInfoType = null;
		SumDscrType sumDscrType = null;
		CitationType citationType = null;
		if (stdyDscrType.getStdyInfo().size() > 0) {
			stdyInfoType = stdyDscrType.getStdyInfo().get(0);
			if (stdyInfoType.getSumDscr().size() > 0) {
				sumDscrType = stdyInfoType.getSumDscr().get(0);
			}
		}
		if (stdyDscrType.getCitation().size() > 0) {
			citationType = stdyDscrType.getCitation().get(0);
			RspStmtType rspStmtType = citationType.getRspStmt();
			ProdStmtType prodStmtType = citationType.getProdStmt();
			if (rspStmtType != null) {
				List<AuthEntyType> authEntyTypeList = rspStmtType.getAuthEnty();
				// TODO use the list
			}
			if (prodStmtType != null) {
				List<ProdPlacType> prodPlacTypeList = prodStmtType.getProdPlac();
				for (ProdPlacType prodPlacType : prodPlacTypeList) {
					log.trace("prodPlacType = " + prodPlacType.content);
					// TODO parse imported address
					// TODO check if address already exists
					Address address = new Address();
					// TODO replace id = 1
					address.setCityId(City.findCity(1));
					address.setAddress1(prodPlacType.content);
					address.setImported(prodPlacType.content);
					address.persist();
				}
			}
		}

		DataCollType dataCollType = null;
		if (stdyDscrType.getMethod().size() > 0) {
			dataCollType = stdyDscrType.getMethod().get(0).getDataColl().get(0);
		}

		Study s = new Study();

		String title = cb.getStdyDscr().get(0).getCitation().get(0).getTitlStmt().getTitl().getContent();
		log.trace("Title = " + title);

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

			log.trace("yearStart = " + yearStart);
			log.trace("yearEnd = " + yearEnd);
			log.trace("dateStart = " + dateStart);
			log.trace("dateEnd = " + dateEnd);
		}
		s.setAdded(new GregorianCalendar());

		// TODO check these default values
		s.setAnonymousUsage(true);
		s.setDigitizable(true);
		s.setRawData(true);
		s.setRawMetadata(false);
		s.setInsertionStatus(0);

		// TODO replace user = admin ?
		Users u = Users.findUsers(1);
		s.setAddedBy(u);
		Set<Study> su = u.getStudies();
		su.add(s);
		u.setStudies(su);

		// TODO don't use directly ID = 1
		// TODO replace TimeMeth with a field in StudyDescr ?
		TimeMeth tm = TimeMeth.findTimeMeth(1);
		s.setTimeMethId(tm);
		Set<Study> tms = tm.getStudies();
		tms.add(s);
		tm.setStudies(tms);

		// TODO don't use directly ID = 1
		// TODO remove class UnitAnalysis ? (it is now a field in StudyDescr)
		UnitAnalysis ua = UnitAnalysis.findUnitAnalysis(1);
		s.setUnitAnalysisId(ua);
		Set<Study> uas = ua.getStudies();
		uas.add(s);
		ua.setStudies(uas);

		s.persist();

		StudyDescr sd = new StudyDescr();
		// TODO obtain Romanian language, don't use directly ID = 1
		sd.setId(new StudyDescrPK(new Integer(1), s.getId()));
		sd.setTitle(title);
		sd.setOriginalTitleLanguage(true);

		if (stdyInfoType != null && stdyInfoType.getAbstract().size() > 0) {
			sd.setAbstract1(stdyInfoType.getAbstract().get(0).content);
		}
		if (sumDscrType != null && sumDscrType.getGeogCover().size() > 0) {
			sd.setGeographicCoverage(sumDscrType.getGeogCover().get(0).content);
		}
		if (sumDscrType != null && sumDscrType.getGeogUnit().size() > 0) {
			sd.setGeographicUnit(sumDscrType.getGeogUnit().get(0).content);
		}
		if (sumDscrType != null && sumDscrType.getUniverse().size() > 0) {
			sd.setUniverse(sumDscrType.getUniverse().get(0).content);
		}
		if (sumDscrType != null && sumDscrType.getAnlyUnit().size() > 0) {
			sd.setAnalysisUnit(sumDscrType.getAnlyUnit().get(0).content);
		}
		if (dataCollType != null && dataCollType.getWeight().size() > 0) {
			sd.setWeighting(dataCollType.getWeight().get(0).content);
		}
		if (dataCollType != null && dataCollType.getResInstru().size() > 0) {
			sd.setResearchInstrument(dataCollType.getResInstru().get(0).content);
		}

		sd.persist();

		if (stdyInfoType != null) {
			SubjectType subjectType = stdyInfoType.getSubject();
			if (subjectType != null) {
				List<KeywordType> keywordTypeList = subjectType.getKeyword();
				Set<StudyKeyword> skSet = new HashSet<StudyKeyword>();
				for (KeywordType keywordType : keywordTypeList) {
					log.trace("keyword = " + keywordType.content);
					Keyword keyword = Keyword.checkKeyword(null, keywordType.content);
					StudyKeyword sk = new StudyKeyword();
					// TODO replace user id = 1
					sk.setId(new StudyKeywordPK(s.getId(), keyword.getId(), 1));
					// sk.setKeywordId(keyword);
					// sk.setStudyId(s);

					// TODO replace user id = 1
					sk.setAddedBy(Users.findUsers(1));
					sk.setAdded(new GregorianCalendar());
					sk.persist();
					skSet.add(sk);
				}
				List<TopcClasType> topcClasTypeList = subjectType.getTopcClas();
				for (TopcClasType topcClasType : topcClasTypeList) {
					log.trace("topic = " + topcClasType.content);
					// TODO persist topics
				}
			}
		}

		// Add Study to an existing Catalog

		// TODO don't use directly ID='1' for Catalog
		CatalogStudy cs = new CatalogStudy();
		CatalogStudyPK csid = new CatalogStudyPK(1, s.getId());
		cs.setId(csid);
		cs.setAdded(new GregorianCalendar());
		cs.persist();

		// create a new Instance / Dataset -> for this import
		Instance instance = new Instance();
		instance.setStudyId(s);
		instance.setMain(true);
		// TODO replace user id = 1
		instance.setAddedBy(Users.findUsers(1));
		instance.setAdded(new GregorianCalendar());
		instance.persist();

		// import Variables
		Set<InstanceVariable> instanceVariableSet = new HashSet<InstanceVariable>();
		if (dataDscrType != null) {
			List<VarType> varTypeList = dataDscrType.getVar();
			int counter = 0;
			for (VarType varType : varTypeList) {
				log.trace("variable = " + varType.getName());
				Variable variable = new Variable();
				variable.setName(varType.getName());
				if (varType.getLabl().size() > 0) {
					variable.setLabel(varType.getLabl().get(0).content);
				}
				// TODO check
				variable.setVariableType((short) 0);
				// TODO check
				variable.setType((short) 0);
				variable.persist();

				InstanceVariable iv = new InstanceVariable();
				iv.setId(new InstanceVariablePK(instance.getId(), variable.getId()));
				iv.setOrderVariableInInstance(counter);
				counter++;
				iv.persist();
				instanceVariableSet.add(iv);
			}
		}
		instance.setInstanceVariables(instanceVariableSet);
		instance.merge();
	}
}
