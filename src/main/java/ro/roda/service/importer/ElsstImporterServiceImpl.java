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

	private static final String elsstEnTerms = "elsst/elsst_terms.csv";

	private static final String elsstEnRelationships = "elsst/elsst_en_relationships.csv";

	private static final String elsstPreferredTerms = "elsst/elsst_ro_pt.csv";

	private static final String elsstUsedFor = "elsst/elsst_ro_uf.csv";

	private static final String elsstScopeNotes = "elsst/elsst_ro_sn.csv";

	private static final String elsstRels = "elsst/elsst_ro_rels.csv";

	@PersistenceContext
	transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new ElsstImporterServiceImpl().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importElsst() throws Exception {
		log.trace("Importing ELSST - NEW");

		CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(new ClassPathResource(
				elsstPreferredTerms).getInputStream(), "UTF8")));

		List<String[]> csvLines = reader.readAll();
		reader.close();

		// an ad-hoc cache for the Topics
		Map<String, Topic> roTopicsMap = new HashMap<String, Topic>();

		// used for translations & original
		Lang roLang = Lang.findLang("ro");
		Integer roLangId = roLang.getId();
		Lang enLang = Lang.findLang("en");
		Integer enLangId = enLang.getId();

		Topic t;
		TranslatedTopic tt;
		Set<TranslatedTopic> ttSet;
		String errorMessage;
		for (String[] csvLine : csvLines) {

			t = new Topic();
			t.setParentId(null);
			t.persist();

			// ignore invalid CSV lines
			if (csvLine.length != 2 || csvLine[0] == null || csvLine[1] == null) {
				errorMessage = "ELSST Term is not correctly defined in CSV: number of items per line";
				log.error(errorMessage);
				continue;
			}

			// RO language
			// log.trace("ELSST Term RO: " + csvLine[1]);
			tt = new TranslatedTopic();
			tt.setTranslation(csvLine[0].toUpperCase());
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

			// EN language
			// log.trace("ELSST Term EN: " + csvLine[0]);
			tt = new TranslatedTopic();
			tt.setTranslation(csvLine[1].toUpperCase());
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

			// put the current Topic (RO !) in the ad-hoc cache
			roTopicsMap.put(csvLine[0], t);

		}

		// TODO set multiple UFs for a single PreferredTerm
		reader = new CSVReader(new BufferedReader(new InputStreamReader(
				new ClassPathResource(elsstUsedFor).getInputStream(), "UTF8")));
		csvLines = reader.readAll();
		reader.close();

		for (String[] csvLine : csvLines) {

			if (csvLine.length != 2) {
				errorMessage = "ELSST U.F. is not correctly defined in CSV: number of items per line";
				log.error(errorMessage);
				continue;
			}

			t = roTopicsMap.get(csvLine[0]);
			if (t != null) {
				tt = TranslatedTopic.findTranslatedTopic(new TranslatedTopicPK(roLangId, t.getId()));
				tt.setScopeNotes(csvLine[1]);
				tt.setUsedFor(csvLine[1]);
				tt.merge();
			}
		}

		reader = new CSVReader(new BufferedReader(new InputStreamReader(
				new ClassPathResource(elsstScopeNotes).getInputStream(), "UTF8")));
		csvLines = reader.readAll();
		reader.close();

		for (String[] csvLine : csvLines) {

			if (csvLine.length != 2) {
				errorMessage = "ELSST Scope Notes is not correctly defined in CSV: number of items per line";
				log.error(errorMessage);
				continue;
			}

			t = roTopicsMap.get(csvLine[0]);
			if (t != null) {
				tt = TranslatedTopic.findTranslatedTopic(new TranslatedTopicPK(roLangId, t.getId()));
				tt.setScopeNotes(csvLine[1]);
				tt.merge();
			}
		}

		reader = new CSVReader(new BufferedReader(new InputStreamReader(
				new ClassPathResource(elsstRels).getInputStream(), "UTF8")));

		csvLines = reader.readAll();
		reader.close();

		for (String[] csvLine : csvLines) {

			if (csvLine.length != 5) {
				errorMessage = "ELSST Relationship is not correctly defined in CSV: number of items per line";
				log.error(errorMessage);
				continue;
			}

			Topic src = roTopicsMap.get(csvLine[1]);
			Topic dst = roTopicsMap.get(csvLine[4]);

			if (src == null || dst == null) {
				errorMessage = "ELSST Relationship is not correctly defined in CSV: some Terms do not exist: "
						+ csvLine[0] + " ; " + csvLine[1] + " ; " + csvLine[2] + " ; " + csvLine[3] + " ; "
						+ csvLine[4];
				log.error(errorMessage);

				// TODO uncomment this exception-throwing
				// only when ELSST Terms & Relationships are
				// stable and correlated !!!
				// throw new IllegalStateException(errorMessage);

				// temporary ignore this type of errors
				continue;
			}

			// set a parent - child relationship
			// (type: NT = Narrower Term)
			if ("NT".equals(csvLine[2].trim())) {
				// log.trace("ELSST Relationship: " + csvLine[1] + " -- " +
				// csvLine[4]);
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

			// TODO RT = Related Terms
			// TODO BT = Broader Terms
		}
		log.trace("Finished Importing ELSST");
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void importElsstOld() throws Exception {

		log.trace("Importing ELSST - OLD");

		CSVReader reader = new CSVReader(new BufferedReader(new InputStreamReader(
				new ClassPathResource(elsstEnTerms).getInputStream(), "UTF8")));

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

		reader = new CSVReader(new BufferedReader(new InputStreamReader(
				new ClassPathResource(elsstEnRelationships).getInputStream(), "UTF8")));

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
