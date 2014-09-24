package ro.roda.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ro.roda.service.importer.ImporterService;

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

	@Value("${roda.data.csv-after-ddi}")
	private String rodaDataCsvAfterDdi;

	@Autowired
	DatabaseUtils du;

	@Autowired
	XmlUtils xu;

	@Autowired
	SolrUtils su;

	@Autowired
	ImporterService importer;

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
				log.trace("roda.data.csv-after-ddi = " + rodaDataCsvAfterDdi);
				log.trace("roda.data.cms = " + rodaDataCms);

				// to skip the initial actions,
				// change properties to another string
				// (not "yes")

				if (YES.equalsIgnoreCase(rodaDataCsv)) {
					importer.importCsv();

					// CMS data depends on initial set of CSVs
					// (e.g. Language)
					if (YES.equalsIgnoreCase(rodaDataCms)) {
						importer.importCmsFiles();
						importer.importCmsLayouts();
						importer.importCmsPages();
						importer.importCmsSnippets();
					}

					// ELSST depends or will depend on initial set of CSVs
					// (e.g. Language)
					if (YES.equalsIgnoreCase(rodaDataElsst)) {
						importer.importElsst();
					}

					// This phase (EXTRA-CSV) should be put last to ensure that
					// the data
					// imported here is really optional, and no other phases are
					// depending on it.
					// Otherwise, the CSVs should be placed in the first set of
					// CSVs, not here.

					// Now, the last phase has to be
					// "DDI-INTO-CATALOGS-AND-SERIES"
					// and this CSV-EXTRA phase imports first
					// the necessary CSV for CATALOGS !!!
					if (YES.equalsIgnoreCase(rodaDataCsvExtra)) {
						importer.importCsvExtra();
					}

					if (YES.equalsIgnoreCase(rodaDataDdi)) {
						importer.importDdiFiles();
						if (YES.equalsIgnoreCase(rodaDataCsvAfterDdi)) {
							importer.importDdiIntoCatalogsAndSeries();
						}
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
