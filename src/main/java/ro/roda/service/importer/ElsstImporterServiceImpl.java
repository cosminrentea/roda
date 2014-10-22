package ro.roda.service.importer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Lang;
import ro.roda.domain.Topic;
import ro.roda.domain.TranslatedTopic;
import ro.roda.domain.TranslatedTopicPK;
import ro.roda.service.AdminJsonService;
import ro.roda.service.CmsFileService;
import ro.roda.service.CmsFolderService;
import ro.roda.service.filestore.CmsFileStoreService;
import au.com.bytecode.opencsv.CSVReader;

@Service
public class ElsstImporterServiceImpl implements ElsstImporterService {

	private final Log log = LogFactory.getLog(this.getClass());

	@Value("${roda.data.elsst.dir}")
	private String rodaDataElsstDir;

	private static final String elsstEnTerms = "elsst_terms.csv";

	private static final String elsstEnRelationships = "elsst_en_relationships.csv";

	@PersistenceContext
	transient EntityManager entityManager;

	@Autowired
	AdminJsonService adminJsonService;

	@Autowired
	CmsFileStoreService cmsFileStoreService;

	@Autowired
	CmsFolderService cmsFolderService;

	@Autowired
	CmsFileService cmsFileService;

	public static final EntityManager entityManager() {
		EntityManager em = new ElsstImporterServiceImpl().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importElsst() throws Exception {
		log.trace("Importing ELSST");

		CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(new ClassPathResource(
				rodaDataElsstDir + elsstEnTerms).getInputStream(), "UTF8")));

		List<String[]> csvLines = reader.readAll();
		reader.close();

		// an ad-hoc cache for the Topics
		Map<String, Topic> topicsMap = new HashMap<String, Topic>();

		// used for translations & original
		Lang roLang = Lang.findLang("ro");
		Integer roLangId = roLang.getId();
		Lang enLang = Lang.findLang("en");
		Integer enLangId = enLang.getId();

		TranslatedTopic tt;
		Set<TranslatedTopic> ttSet;

		for (String[] csvLine : csvLines) {

			// log.trace("ELSST Term: " + csvLine[0]);

			Topic t = new Topic();
			// t.setName(csvLine[0]);

			t.persist();

			// original - EN language
			if (csvLine.length >= 1 && csvLine[0] != null) {
				// log.trace("ELSST Term EN: " + csvLine[0]);
				tt = new TranslatedTopic();
				tt.setTranslation(csvLine[0].toUpperCase());
				tt.setId(new TranslatedTopicPK(enLangId, t.getId()));
				tt.persist();

				ttSet = t.getTranslations();
				if (ttSet == null) {
					ttSet = new HashSet<TranslatedTopic>();
				}
				ttSet.add(tt);
				t.setTranslations(ttSet);

				ttSet = enLang.getTranslatedTopics();
				if (ttSet == null) {
					ttSet = new HashSet<TranslatedTopic>();
				}
				ttSet.add(tt);
				enLang.setTranslatedTopics(ttSet);
			}

			// optional translation - RO language
			if (csvLine.length >= 2 && csvLine[1] != null) {
				// log.trace("ELSST Term RO: " + csvLine[1]);
				tt = new TranslatedTopic();
				tt.setTranslation(csvLine[1].toUpperCase());
				tt.setId(new TranslatedTopicPK(roLangId, t.getId()));
				tt.persist();

				ttSet = t.getTranslations();
				if (ttSet == null) {
					ttSet = new HashSet<TranslatedTopic>();
				}
				ttSet.add(tt);
				t.setTranslations(ttSet);

				ttSet = roLang.getTranslatedTopics();
				if (ttSet == null) {
					ttSet = new HashSet<TranslatedTopic>();
				}
				ttSet.add(tt);
				roLang.setTranslatedTopics(ttSet);
			}

			// put the Topic (EN) in the ad-hoc cache
			topicsMap.put(csvLine[0], t);

		}

		reader = new CSVReader(new BufferedReader(new InputStreamReader(new ClassPathResource(rodaDataElsstDir
				+ elsstEnRelationships).getInputStream(), "UTF8")));

		csvLines = reader.readAll();
		reader.close();

		for (String[] csvLine : csvLines) {
			// log.trace("ELSST Relationship: " + csvLine[0] + " " + csvLine[1]
			// + " " + csvLine[2]);

			if (csvLine.length != 3) {
				String errorMessage = "ELSST Relationship is not correctly defined CSV: number of items per line";
				log.error(errorMessage);
				throw new IllegalStateException(errorMessage);
			}

			Topic src = topicsMap.get(csvLine[0]);
			Topic dst = topicsMap.get(csvLine[2]);

			if (src == null || dst == null) {
				String errorMessage = "ELSST Relationship is not correctly defined in CSV - some Terms do not exist;"
						+ csvLine[0] + ";" + csvLine[2];
				log.error(errorMessage);

				// TODO uncomment this exception-throwing
				// only when ELSST Terms & Relationships are
				// stable and correlated !!!
				// throw new IllegalStateException(errorMessage);

				// temporary ignore this type of errors
				continue;
			}

			// set a parent - child relationship
			// (relationship type: 5 and 6)
			if (Integer.parseInt(csvLine[1]) == 5) {
				dst.setParentId(src);
				Set<Topic> topicSet = src.getTopics();
				if (topicSet == null) {
					topicSet = new HashSet<Topic>();
				}
				topicSet.add(dst);
				src.setTopics(topicSet);
				dst.merge(false);
				src.merge(false);
			}

			// set a top-term (parent => null)
			// (relationship type: 7)
			if (Integer.parseInt(csvLine[1]) == 7) {
				dst.setParentId(null);
			}

			// TODO set related terms
			// (relationship type: 8)
			if (Integer.parseInt(csvLine[1]) == 8) {
			}
		}
		log.trace("Finished Importing ELSST");
	}
}
