package ro.roda.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.importer.ImporterCsv;
import ro.roda.importer.ImporterDdi;

@Component
public class ApplicationListenerContextRefreshed implements ApplicationListener<ContextRefreshedEvent> {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	RBean rb;

	@Autowired
	DatabaseUtils du;

	@Autowired
	XmlUtils xu;

	@Autowired
	ImporterCsv icsv;

	@Autowired
	ImporterDdi iddi;

	@Value("${roda.data.csv}")
	private String rodaDataCsv;

	@Value("${roda.data.csv-extra}")
	private String rodaDataCsvExtra;

	@Value("${roda.data.ddi}")
	private String rodaDataDdi;

	@Value("${roda.data.csv-after-ddi}")
	private String rodaDataCsvAfterDdi;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// root context
			log.trace("event.getApplicationContext() = " + event.getApplicationContext());
			log.trace("roda.data.csv = " + rodaDataCsv);
			log.trace("roda.data.csv-extra = " + rodaDataCsvExtra);
			log.trace("roda.data.ddi = " + rodaDataDdi);
			log.trace("roda.data.csv-after-ddi = " + rodaDataCsvAfterDdi);

			// to skip the initial actions,
			// change properties to another string
			// (not "yes")
			if ("yes".equalsIgnoreCase(rodaDataCsv)) {
				icsv.importCsv();
				if ("yes".equalsIgnoreCase(rodaDataCsvExtra)) {
					icsv.importCsvExtra();
				}
			}

			if ("yes".equalsIgnoreCase(rodaDataDdi)) {
				iddi.importDdiFiles();
				if ("yes".equalsIgnoreCase(rodaDataCsvAfterDdi)) {
					icsv.importCsvAfterDdi();
				}
			}

			// rb.rnorm(4);

			// du.setSequence("hibernate_sequence", 1000, 1);
			// du.changeDataDemo();
			// xu.saveXstream();

		}
	}

}
