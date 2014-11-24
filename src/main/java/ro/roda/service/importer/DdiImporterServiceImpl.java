package ro.roda.service.importer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import ro.roda.ddi.AuthEntyType;
import ro.roda.ddi.CatgryType;
import ro.roda.ddi.CitationType;
import ro.roda.ddi.CodeBook;
import ro.roda.ddi.DataCollType;
import ro.roda.ddi.DataDscrType;
import ro.roda.ddi.DocDscrType;
import ro.roda.ddi.KeywordType;
import ro.roda.ddi.ProdPlacType;
import ro.roda.ddi.ProdStmtType;
import ro.roda.ddi.ProducerType;
import ro.roda.ddi.RspStmtType;
import ro.roda.ddi.StdyDscrType;
import ro.roda.ddi.StdyInfoType;
import ro.roda.ddi.SubjectType;
import ro.roda.ddi.SumDscrType;
import ro.roda.ddi.TopcClasType;
import ro.roda.ddi.VarType;
import ro.roda.domain.*;
import ro.roda.domainjson.AdminJson;
import ro.roda.service.AdminJsonService;
import ro.roda.service.CatalogService;
import ro.roda.service.CityService;
import ro.roda.service.CmsFileService;
import ro.roda.service.CmsFolderService;
import ro.roda.service.OrgService;
import ro.roda.service.PersonService;
import ro.roda.service.StudyService;
import ro.roda.service.filestore.CmsFileStoreService;
import au.com.bytecode.opencsv.CSVReader;
import flexjson.JSONSerializer;

@Service
public class DdiImporterServiceImpl implements DdiImporterService {

	private final Log log = LogFactory.getLog(this.getClass());

	private static final String jaxbContextPath = "ro.roda.ddi";
	private static final String ddiFoldername = "ddi";
	private static final String profilesFoldername = "ddi-import-profiles";

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired
	CsvImporterService csvImporterService;

	@Autowired
	CatalogService catalogService;

	@Autowired
	StudyService studyService;

	@Autowired
	PersonService personService;

	@Autowired
	OrgService orgService;

	@Autowired
	CityService cityService;

	@Autowired
	AdminJsonService adminJsonService;

	@Autowired
	CmsFileStoreService cmsFileStoreService;

	@Autowired
	CmsFolderService cmsFolderService;

	@Autowired
	CmsFileService cmsFileService;

	@Value("${roda.data.ddi.profile}")
	private String rodaDataDdiProfile;

	@Value("${roda.data.ddi.csv}")
	private String rodaDataDdiCsv;

	@Value("${roda.data.ddi.save_json}")
	private String rodaDataDdiSaveJson;

	@Value("${roda.data.ddi.xsd}")
	private String xsdDdi122;

	@Value("${roda.data.ddi.persist}")
	private String rodaDataDdiPersist;

	@Value("${roda.data.ddi.otherstatistic}")
	private String rodaDataDdiOtherStatistic;

	private Unmarshaller unmarshaller = null;

	public static final EntityManager entityManager() {
		EntityManager em = new DdiImporterServiceImpl().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public final Unmarshaller getUnmarshaller() throws JAXBException, SAXException, IOException {
		if (unmarshaller == null) {
			// validate using DDI 1.2.2 XML Schema
			Resource xsdDdiRes = new ClassPathResource(xsdDdi122);
			unmarshaller = JAXBContext.newInstance(jaxbContextPath).createUnmarshaller();
			unmarshaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
					xsdDdiRes.getFile()));
		}
		return unmarshaller;
	}

	public void importDdiFiles() throws Exception {

		// import catalogs
		csvImporterService.importCsvFile(profilesFoldername + "/" + rodaDataDdiProfile + "/catalog.csv");

		// import series
		csvImporterService.importCsvFile(profilesFoldername + "/" + rodaDataDdiProfile + "/series.csv");

		// read: catalog-study relationships + all referenced DDI filenames
		CSVReader reader = new CSVReader(new FileReader(new ClassPathResource(profilesFoldername + "/"
				+ rodaDataDdiProfile + "/catalog_study.csv").getFile()));
		List<String[]> csvLines = reader.readAll();
		reader.close();

		// keep the DDI files in the same order each time
		// so the IDs generated when importing are reproducible across runs
		Set<String> ddiFilenames = new TreeSet<String>();

		for (String[] csvLine : csvLines) {
			log.trace("Catalog: " + csvLine[0] + " -- Study Filename: " + csvLine[1]);
			ddiFilenames.add(csvLine[1]);
		}
		log.debug("DDI files listed");

		log.trace("roda.data.ddi.persist = " + rodaDataDdiPersist);
		log.trace("roda.data.ddi.csv = " + rodaDataDdiCsv);
		boolean ddiPersistance = "yes".equalsIgnoreCase(rodaDataDdiPersist);
		boolean importDdiCsv = "yes".equalsIgnoreCase(rodaDataDdiCsv);

		this.getUnmarshaller();

		PathMatchingResourcePatternResolver pmr = new PathMatchingResourcePatternResolver();
		for (String ddiFilename : ddiFilenames) {
			try {
				log.trace("Trying to import DDI file: " + ddiFilename);

				Resource resource = pmr.getResource("classpath:" + ddiFoldername + "/" + ddiFilename);
				if (resource == null) {
					log.warn("DDI file not found!");
					continue;
				}

				File ddiFile = resource.getFile();
				MockMultipartFile mockMultipartFileDdi = new MockMultipartFile(ddiFile.getName(), ddiFile.getName(),
						"text/xml", new FileInputStream(ddiFile));
				CodeBook cb = (CodeBook) unmarshaller.unmarshal(ddiFile);

				// add CSV data (if any) to imported DDIs
				MockMultipartFile mockMultipartFileCsv = null;
				if (importDdiCsv) {
					// get CSV file name (RODA naming rules)
					String csvFilename = ddiFile.getName().split("\\.|_|-")[0].concat("_T.csv");
					// TODO @Value for "ddi" folder below
					Resource csvResource = pmr.getResource("classpath:ddi/" + csvFilename);
					FileInputStream fisCsv = null;
					fisCsv = new FileInputStream(csvResource.getFile());
					mockMultipartFileCsv = new MockMultipartFile(csvFilename, csvFilename, "text/csv", fisCsv);
				}
				importDdiFile(cb, mockMultipartFileDdi, true, true, ddiPersistance, mockMultipartFileCsv);

			} catch (Exception e) {
				log.fatal("Exception thrown when importing this DDI file: " + ddiFilename, e);
				throw e;
			}
		}
		log.debug("DDI files were imported");

		afterImport(csvLines);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void afterImport(List<String[]> csvLines) {
		for (String[] csvLine : csvLines) {
			log.trace("Catalog: " + csvLine[0] + " -- Study Filename: " + csvLine[1]);
			Study study = Study.findFirstStudyWithFilename(csvLine[1]);
			if (study == null) {
				String errorMessage = "Study cannot be added to Catalog because its filename was not found: "
						+ csvLine[1];
				log.error(errorMessage);
				throw new IllegalStateException(errorMessage);
			}
			CatalogStudy cs = new CatalogStudy();
			cs.setId(new CatalogStudyPK(Integer.valueOf(csvLine[0]), study.getId()));
			cs.persist();
		}
		log.debug("DDI files put into Catalogs and Series");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importDdiFile(CodeBook cb, MultipartFile multipartFileDdi, boolean nesstarExported,
			boolean legacyDataRODA, boolean ddiPersistence, MultipartFile multipartFileCsv)
			throws FileNotFoundException, IOException {

		if (ddiPersistence) {
			if (this.entityManager == null) {
				this.entityManager = entityManager();
			}
			this.entityManager.persist(cb);
		}

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

		List<Person> persons = new ArrayList<Person>();

		if (stdyDscrType.getCitation().size() > 0) {
			citationType = stdyDscrType.getCitation().get(0);
			RspStmtType rspStmtType = citationType.getRspStmt();
			ProdStmtType prodStmtType = citationType.getProdStmt();
			if (rspStmtType != null) {
				List<AuthEntyType> authEntyTypeList = rspStmtType.getAuthEnty();
				for (AuthEntyType authEntyType : authEntyTypeList) {
					if (authEntyType.content != null) {
						Person person = new Person();

						// TODO split name
						// person.setFname(authEntyType.content.trim());
						// person.setLname(authEntyType.content.trim());
						String[] personName = StringUtils.split(authEntyType.content.trim(), " ");
						person.setFname(personName[0]);
						person.setLname(personName[1]);

						// TODO use affiliation to link Person with an Org
						// (using abbreviation)
						// person.setMname(authEntyType.getAffiliation());

						persons.add(person);

						// LV: temporarily commented
						// person.persist();
					}
				}
			}

			if (prodStmtType != null) {
				List<ProducerType> producerTypeList = prodStmtType.getProducer();
				List<ProdPlacType> prodPlacTypeList = prodStmtType.getProdPlac();
				for (ProdPlacType prodPlacType : prodPlacTypeList) {
					log.trace("prodPlacType = " + prodPlacType.content);
					// TODO parse imported address
					// TODO check if address already exists
					Address address = new Address();
					// TODO replace id = 1
					address.setCityId(cityService.findCity(1));
					address.setAddress1(prodPlacType.content);
					address.setImported(prodPlacType.content);
					address.persist();
				}

				// for (ProducerType producerType : producerTypeList) {
				// log.trace("producerType = " + producerType.content);
				// if (producerType.content != null) {
				// Org org = new Org();
				// org.setFullName(producerType.content.trim().toUpperCase());
				// org.setShortName(producerType.getAbbr());
				//
				// org.persist();
				// }
				//
				// }

			}
		}

		DataCollType dataCollType = null;
		if (stdyDscrType.getMethod().size() > 0) {
			dataCollType = stdyDscrType.getMethod().get(0).getDataColl().get(0);
		}

		// create the new Study
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
		s.setImportedFilename(multipartFileDdi.getName());

		Integer userId = 1;
		Users u = Users.findUsers(userId);
		s.setAddedBy(u);
		Set<Study> su = u.getStudies();
		if (su == null) {
			su = new HashSet<Study>();
		}
		su.add(s);
		u.setStudies(su);

		// TODO don't use directly ID = 1
		// TODO replace TimeMeth with a field in StudyDescr ?
		TimeMeth tm = TimeMeth.findTimeMeth(1);
		s.setTimeMethId(tm);
		Set<Study> tms = tm.getStudies();
		if (tms == null) {
			tms = new HashSet<Study>();
		}
		tms.add(s);
		tm.setStudies(tms);

		// TODO don't use directly ID = 1
		// TODO remove class UnitAnalysis ? (it is now a field in StudyDescr)
		UnitAnalysis ua = UnitAnalysis.findUnitAnalysis(1);
		s.setUnitAnalysisId(ua);
		Set<Study> uas = ua.getStudies();
		if (uas == null) {
			uas = new HashSet<Study>();
		}
		uas.add(s);
		ua.setStudies(uas);

		s.persist();

		StudyPersonAssoc spAssoc = new StudyPersonAssoc();
		spAssoc.setAsocName("authEntity");
		spAssoc.setAsocDescription("authoring entity");
		spAssoc.persist();

		// associate persons to the study
		if (persons != null && persons.size() > 0) {
			Set<StudyPerson> studyPersons = new HashSet<StudyPerson>();
			for (Person p : persons) {
				p.persist();
				StudyPerson sp = new StudyPerson();
				sp.setPersonId(p);
				sp.setStudyId(s);
				sp.setAssoctypeId(spAssoc);

				sp.setId(new StudyPersonPK(p.getId(), s.getId(), spAssoc.getId()));
				sp.persist();
				studyPersons.add(sp);
			}
			s.setStudypeople(studyPersons);
		}

		Lang roLang = Lang.findLang("ro");
		Integer roLangId = roLang.getId();

		StudyDescr sd = new StudyDescr();
		sd.setId(new StudyDescrPK(roLangId, s.getId()));
		sd.setStudyId(s);
		sd.setLangId(roLang);
		Set<StudyDescr> studyDescrs = roLang.getStudyDescrs();
		if (studyDescrs == null) {
			studyDescrs = new HashSet<StudyDescr>();
		}
		studyDescrs.add(sd);
		roLang.setStudyDescrs(studyDescrs);

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

				// Keywords
				List<KeywordType> keywordTypeList = subjectType.getKeyword();
				for (KeywordType keywordType : keywordTypeList) {
					String keywordContent = keywordType.content;
					if (keywordContent == null)
						continue;
					keywordContent = keywordContent.trim().toUpperCase();
					if (keywordContent.length() == 0)
						continue;

					// after the previous "keyword-cleanup",
					// attach it to the study
					log.trace("Keyword: " + keywordContent);
					Keyword keyword = Keyword.checkKeyword(null, keywordContent);
					StudyKeyword sk = new StudyKeyword();
					sk.setId(new StudyKeywordPK(s.getId(), keyword.getId(), userId));
					sk.setAddedBy(u);
					sk.setAdded(new GregorianCalendar());
					sk.persist();
				}

				// Topics / ELSST
				List<TopcClasType> topcClasTypeList = subjectType.getTopcClas();
				Set<Topic> tSet = new HashSet<Topic>();
				for (TopcClasType topcClasType : topcClasTypeList) {
					String topicContent = topcClasType.content;
					if (topicContent == null)
						continue;
					topicContent = topicContent.trim().toUpperCase();
					if (topicContent.length() == 0)
						continue;
					log.trace("Topic of imported study: " + topicContent);
					String[] topicNames = topicContent.split(";|,|\\.");
					for (String topicName : topicNames) {
						TranslatedTopic translatedTopic = TranslatedTopic.findOrCreateTranslatedTopic(topicName,
								roLangId);
						Topic topic = translatedTopic.getTopicId();

						// add the associated Study for this Topic
						tSet.add(topic);
						Set<Study> sStudy = topic.getStudies();
						if (sStudy == null) {
							sStudy = new HashSet<Study>();
						}
						sStudy.add(s);
						topic.setStudies(sStudy);
					}
				}
				s.setTopics(tSet);
			}
		}

		// create a new Instance / Dataset for this import
		Instance instance = new Instance();
		instance.setStudyId(s);
		instance.setMain(true);
		instance.setAddedBy(Users.findUsers(userId));
		instance.setAdded(new GregorianCalendar());
		instance.persist();

		// import Variables
		if (dataDscrType != null) {
			List<VarType> varTypeList = dataDscrType.getVar();

			int counterQstn = 0;
			for (VarType varType : varTypeList) {
				log.trace("Variable = " + varType.getName());
				Variable variable = new Variable();
				variable.setName(varType.getName());
				if (varType.getLabl() != null && varType.getLabl().size() > 0) {
					variable.setLabel(varType.getLabl().get(0).content);
				}

				if (varType.getQstn().size() > 0 && varType.getQstn().get(0).getQstnLitType().size() > 0) {

					Question q = null;

					// TODO never check if the question has been already
					// imported? - see if it is ok like that
					// q = Question.checkQuestion(null, variable.getName(),
					// instance.getId(), varType.getQstn().get(0)
					// .getQstnLitType().get(0).content);
					q = new Question();

					// label = the variable's label
					// (due to RODA's data files)
					q.setName(variable.getName());
					q.setStatement(varType.getQstn().get(0).getQstnLitType().get(0).content);
					q.setLangId(roLang);

					q.setOrderInMainInstance(counterQstn++);

					// update the Variable reference
					Set<Variable> qVariables = q.getVariables();
					if (qVariables == null) {
						qVariables = new HashSet<Variable>();
					}
					qVariables.add(variable);
					q.setVariables(qVariables);

					// set the containing instance for this question ("main")
					Set<Instance> instances = new HashSet<Instance>();
					instances.add(instance);
					q.setInstances(instances);
					// q.setInstanceId(instance);

					// add the question to the "main" instance
					Set<Question> iQuestions = instance.getQuestions();
					if (iQuestions == null) {
						iQuestions = new HashSet<Question>();
					}
					iQuestions.add(q);
					instance.setQuestions(iQuestions);

					q.persist();

					// set the question of this variable
					variable.setQuestionId(q);
				}

				// build a string representing the categories (if any),
				// which can be passed "as-constructed" to R later
				StringBuilder sbCategories = new StringBuilder();
				if (varType.getCatgry() != null && varType.getCatgry().size() > 0) {
					Iterator<CatgryType> iterCategories = varType.getCatgry().iterator();
					CatgryType catgryType = iterCategories.next();
					if (catgryType.getLabl() != null && catgryType.getLabl().size() > 0) {
						sbCategories.append("\"").append(catgryType.getLabl().get(0).content).append("\"=")
								.append(catgryType.getCatValu().content);
					}
					while (iterCategories.hasNext()) {
						catgryType = iterCategories.next();
						if (catgryType.getLabl() != null && catgryType.getLabl().size() > 0) {
							sbCategories.append(",\"").append(catgryType.getLabl().get(0).content).append("\"=")
									.append(catgryType.getCatValu().content);
						}
					}
				}
				variable.setCategories(sbCategories.toString());

				// TODO check semantics
				variable.setVariableType((short) 0);

				variable.setOrderInQuestion(1);

				// TODO Check the order of variable in question
				// variable.setOrderInQuestion(q.getVariables().size() + 1);

				variable.persist();

				// Add categories names + their frequencies to
				// "other_statistics" table
				if (rodaDataDdiOtherStatistic.equalsIgnoreCase("yes") && (varType.getCatgry() != null)) {
					for (CatgryType catgryType : varType.getCatgry()) {
						if (catgryType.getCatStat() != null
								&& catgryType.getCatStat().size() > 0
								&& (catgryType.getLabl() != null && catgryType.getLabl().size() > 0 || catgryType
										.getCatValu() != null)) {
							OtherStatistic os = new OtherStatistic();
							os.setVariableId(variable);
							os.setValue(new Float(catgryType.getCatStat().get(0).content));
							if (catgryType.getLabl() != null && catgryType.getLabl().size() > 0) {
								os.setName(catgryType.getLabl().get(0).content);
							} else {
								os.setName(catgryType.getCatValu().content);
							}
							os.persist();
						}
					}
				}
			}
			// instance.merge();
		}

		// Add the imported DDI File to current Study's Files
		// (in CMS-File-Store)
		AdminJson adminJsonImportedFolder = adminJsonService.folderSave(CmsFolder.importedCmsFolderName, null,
				"Folder for imported DDI files and related files");

		// create sub-folder for each imported study
		AdminJson adminJsonStudyFolder = adminJsonService.folderSave(s.getId().toString(),
				adminJsonImportedFolder.getId(), null);

		// save the DDI XML file in the folder
		AdminJson adminJsonDdi = adminJsonService.fileSave(adminJsonStudyFolder.getId(), multipartFileDdi, null, null,
				null);

		// prepare attachement of File(s)
		Set<CmsFile> studyFiles = s.getCmsFiles();
		if (studyFiles == null) {
			studyFiles = new HashSet<CmsFile>();
		}

		// 4. associate the DDI File to the Study
		CmsFile cmsFile = CmsFile.findCmsFile(adminJsonDdi.getId());
		Set<Study> currentStudySet = new HashSet<Study>();
		currentStudySet.add(s);
		cmsFile.setStudies(currentStudySet);
		studyFiles.add(cmsFile);

		// save the data/CSV file, if any
		// if (multipartFileCsv != null) {
		// adminJsonService.fileSave(retDdi.getId(), multipartFileCsv, null,
		// null, null);
		//
		// List<String[]> csvLines;
		// CSVReader reader = new CSVReader(new BufferedReader(new
		// InputStreamReader(
		// multipartFileCsv.getInputStream(), "UTF8")), '\t');
		// csvLines = reader.readAll();
		// reader.close();
		//
		// String[] headerLine;
		// List<Variable> variableList = new ArrayList<Variable>();
		// Iterator<String[]> iter = csvLines.iterator();
		// if (iter.hasNext()) {
		// headerLine = iter.next();
		// for (int i = 0; i < headerLine.length; i++) {
		// headerLine[i] = headerLine[i].split("\"")[0];
		//
		// for (Question question : instance.getQuestions()) {
		// for (Variable qVar : question.getVariables()) {
		// if (qVar.getName().equalsIgnoreCase(headerLine[i])) {
		// variableList.add(qVar);
		// break;
		// }
		// }
		// }
		// }
		// }
		// for (; iter.hasNext();) {
		// String[] csvLine = iter.next();
		// log.trace("Line items: " + csvLine.length);
		// Form form = new Form();
		// form.persist();
		// for (int i = 0; i < csvLine.length; i++) {
		// FormEditedTextVar fetv = new FormEditedTextVar();
		//
		// fetv.setFormId(form);
		// Set<FormEditedTextVar> sfetv = form.getFormEditedTextVars();
		// if (sfetv == null) {
		// sfetv = new HashSet<FormEditedTextVar>();
		// }
		// sfetv.add(fetv);
		// form.setFormEditedTextVars(sfetv);
		//
		// fetv.setVariableId(variableList.get(i));
		// sfetv = variableList.get(i).getFormEditedTextVars();
		// if (sfetv == null) {
		// sfetv = new HashSet<FormEditedTextVar>();
		// }
		// sfetv.add(fetv);
		// variableList.get(i).setFormEditedTextVars(sfetv);
		// variableList.get(i).merge();
		//
		// fetv.setText(csvLine[i]);
		//
		// fetv.setId(new FormEditedTextVarPK(variableList.get(i).getId(),
		// form.getId()));
		//
		// fetv.persist();
		// }
		// }
		//
		// }

		if (multipartFileCsv != null) {

			// save & attach the DDI CSV file
			AdminJson adminJsonCsv = adminJsonService.fileSave(adminJsonStudyFolder.getId(), multipartFileCsv, null,
					null, null);
			cmsFile = CmsFile.findCmsFile(adminJsonCsv.getId());
			cmsFile.setStudies(currentStudySet);
			studyFiles.add(cmsFile);

			List<String[]> csvLines;
			CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(
					multipartFileCsv.getInputStream(), "UTF8")), '|');
			csvLines = reader.readAll();
			reader.close();

			for (Iterator<String[]> iter = csvLines.iterator(); iter.hasNext();) {
				String[] csvLine = iter.next();
				// log.trace("Line items: " + csvLine.length);
				if (csvLine.length > 1) {
					StringBuilder varValues = new StringBuilder();
					varValues.append(csvLine[1]);
					for (int i = 2; i < csvLine.length; i++) {
						varValues.append(',').append(csvLine[i]);
					}
					if (instance.getQuestions() != null) {
						for (Question question : instance.getQuestions()) {
							for (Variable var : question.getVariables()) {
								if (csvLine[0].equalsIgnoreCase(var.getName())) {
									var.setValues(varValues.toString());
									// var.merge();
									break;
								}
							}
						}
					} else {
						log.error("instance.getQuestions() == null");
					}
				}
			}
		}

		// serialization of DDI XML as JSON - only if requested
		if ("yes".equalsIgnoreCase(rodaDataDdiSaveJson)) {
			String ddiJson = new JSONSerializer().exclude("*.class").prettyPrint(true).deepSerialize(cb);
			File fileJson = File.createTempFile("roda-json-", null);
			FileWriter fw = new FileWriter(fileJson);
			fw.write(ddiJson);
			fw.flush();
			fw.close();
			FileInputStream fisJson = new FileInputStream(fileJson);
			MockMultipartFile mmfJson = new MockMultipartFile(multipartFileDdi.getName().concat(".json"),
					multipartFileDdi.getName().concat(".json"), "application/json", fisJson);

			// save & attach the JSON in FileStore, in the study folder
			AdminJson adminJsonJson = adminJsonService
					.fileSave(adminJsonStudyFolder.getId(), mmfJson, null, null, null);
			cmsFile = CmsFile.findCmsFile(adminJsonJson.getId());
			cmsFile.setStudies(currentStudySet);
			studyFiles.add(cmsFile);

			fisJson.close();
		}

		// all possible files were already attached to the study
		// (DDI / CSV / JSON) -> now "set" these attached files
		s.setCmsFiles(studyFiles);

		// Merge & Flush the study (+related data)
		s.merge();
		s.flush();
	}
}
