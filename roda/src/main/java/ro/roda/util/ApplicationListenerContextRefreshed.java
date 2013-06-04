package ro.roda.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.importer.ImporterCsv;
import ro.roda.importer.ImporterDdi;

@Component
public class ApplicationListenerContextRefreshed implements
		ApplicationListener<ContextRefreshedEvent> {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	RBean rb;

	@Autowired
	DatabaseUtils du;

	@Value("${roda.data.csv}")
	private String rodaDataCsv;

	@Value("${roda.data.csv.dir}")
	private String rodaDataCsvDir;

	@Value("${roda.data.csv-extra}")
	private String rodaDataCsvExtra;

	@Value("${roda.data.csv-extra.dir}")
	private String rodaDataCsvExtraDir;

	@Value("${roda.data.ddi}")
	private String rodaDataDdi;

	@Value("${roda.data.ddi.dir}")
	private String rodaDataDdiDir;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.debug("> onApplicationEvent");
		if (event.getApplicationContext().getParent() == null) {
			// root context
			log.trace("event.getApplicationContext() = "
					+ event.getApplicationContext());

			// check if we are in "test mode"
			// (and the properties file has set a property)
			try {
				Resource resource = new ClassPathResource("roda.properties");
				Properties props = PropertiesLoaderUtils
						.loadProperties(resource);
				rodaDataCsv = props.getProperty("roda.data.csv");
				rodaDataCsvExtra = props.getProperty("roda.data.csv-extra");
				rodaDataDdi = props.getProperty("roda.data.ddi");
			} catch (IOException ignored) {
			}

			log.trace("roda.data.csv = " + rodaDataCsv);
			log.trace("roda.data.csv-extra = " + rodaDataCsvExtra);
			log.trace("roda.data.ddi = " + rodaDataDdi);

			// TODO make sure the following schemas are created BEFORE
			// Hibernate uses the DB
			du.executeUpdate("CREATE SCHEMA audit");
			du.executeUpdate("CREATE SCHEMA ddi");

			// du.truncate();

			rb.rnorm(4);

			// to skip the initial actions,
			// change properties to another string
			// (not "yes")
			if ("yes".equals(rodaDataCsv)) {
				ImporterCsv icsv = new ImporterCsv();
				icsv.importCsvAll(rodaDataCsvDir);
				if ("yes".equals(rodaDataCsvExtra)) {
					icsv.importCsvAll(rodaDataCsvExtraDir);
				}
			}

			if ("yes".equals(rodaDataDdi)) {
				ImporterDdi iddi = new ImporterDdi();
				iddi.importAllDdi(rodaDataDdiDir);
			}

			du.setSequence("hibernate_sequence", 1000, 1);
			// du.changeDataDemo();
			// du.saveXstream();

		}
	}

}
