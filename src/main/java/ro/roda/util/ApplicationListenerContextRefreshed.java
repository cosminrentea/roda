package ro.roda.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ro.roda.service.importer.CsvImporterService;
import ro.roda.service.importer.DdiImporterService;
import ro.roda.service.importer.ElsstImporterService;
import ro.roda.service.importer.CmsImporterService;

@Component
public class ApplicationListenerContextRefreshed implements ApplicationListener<ContextRefreshedEvent> {

	private final Log log = LogFactory.getLog(this.getClass());

	private final static String YES = "yes";

	@Value("${roda.data.csv}")
	private String rodaDataCsv;

	@Value("${roda.data.csv-extra}")
	private String rodaDataCsvExtra;

	@Value("${roda.data.cms}")
	private String rodaDataCms;

	@Value("${roda.data.elsst}")
	private String rodaDataElsst;

	@Value("${roda.data.ddi}")
	private String rodaDataDdi;

	@Autowired
	DatabaseUtils du;

	@Autowired
	XmlUtils xu;

	@Autowired
	SolrUtils su;

	@Autowired
	CmsImporterService importer;

	@Autowired
	DdiImporterService ddiImporter;

	@Autowired
	CsvImporterService csvImporter;

	@Autowired
	ElsstImporterService elsstImporter;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// root context
			log.trace("event.getApplicationContext() = " + event.getApplicationContext());

			// check if Solr is online and delete all its previous data
			// su.pingSolr();
			// su.deleteAll();

			// import all data using the Importer service
			try {
				log.trace("roda.data.elsst = " + rodaDataElsst);
				log.trace("roda.data.csv = " + rodaDataCsv);
				log.trace("roda.data.csv-extra = " + rodaDataCsvExtra);
				log.trace("roda.data.ddi = " + rodaDataDdi);
				log.trace("roda.data.cms = " + rodaDataCms);

				// to skip the initial actions,
				// change properties to another string
				// (not "yes")

				if (YES.equalsIgnoreCase(rodaDataCsv)) {
					csvImporter.importCsv();

					// CMS data depends on initial set of CSVs
					// (e.g. Language)
					if (YES.equalsIgnoreCase(rodaDataCms)) {
						importer.importCmsFiles();
						importer.importCmsLayouts();
						importer.importCmsPages();
						importer.importCmsSnippets();
					}

					// ELSST depends on the initial set of CSVs
					// (e.g. Language)
					if (YES.equalsIgnoreCase(rodaDataElsst)) {
						elsstImporter.importElsst();
					}

					if (YES.equalsIgnoreCase(rodaDataDdi)) {
						ddiImporter.importDdiFiles();
						// if (YES.equalsIgnoreCase(rodaDataCsvAfterDdi)) {
						// ddiImporter.importDdiIntoCatalogsAndSeries();
						// }
					}

					// This phase (EXTRA-CSV) should be put last to ensure that
					// the data imported here is really optional,
					// and no other phases are depending on it.
					// Otherwise, any such CSVs should be placed
					// in the initial set of CSVs, not here in the optional set.
					if (YES.equalsIgnoreCase(rodaDataCsvExtra)) {
						csvImporter.importCsvExtra();
					}
				}

			} catch (Exception e) {
				log.fatal("RODA Import Fatal Error", e);

				// if importing some data failed,
				// then abort launching the application
				throw new IllegalStateException("RODA Import Fatal Error");
			}

			// rb.rnorm(4);
			// du.setSequence("hibernate_sequence", 1000, 1);
			// xu.saveXstream();

		}
	}
}
