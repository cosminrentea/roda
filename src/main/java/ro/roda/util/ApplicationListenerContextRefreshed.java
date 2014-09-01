package ro.roda.util;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.service.importer.ImporterService;

@Component
public class ApplicationListenerContextRefreshed implements ApplicationListener<ContextRefreshedEvent> {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	DatabaseUtils du;

	@Autowired
	XmlUtils xu;

	@Autowired
	SolrUtils su;

	@Autowired
	ImporterService importer;

	@Override
	// @Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// root context
			log.trace("event.getApplicationContext() = " + event.getApplicationContext());

			// check if Solr is online and delete all its previous data
			// su.pingSolr();
			// su.deleteAll();

			// import all data using the Importer service
			try {
				importer.importAll();
			} catch (Exception e) {
				log.error("Import Error", e);

				// if importing some data failed,
				// then abort launching the application
				throw new IllegalStateException("Could not import data");
			}

			// rb.rnorm(4);
			// du.setSequence("hibernate_sequence", 1000, 1);
			// xu.saveXstream();

		}
	}

}
