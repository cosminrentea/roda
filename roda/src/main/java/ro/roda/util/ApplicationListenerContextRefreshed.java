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

@Component
public class ApplicationListenerContextRefreshed implements ApplicationListener<ContextRefreshedEvent> {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	RBean rb;

	@Autowired
	DatabaseUtils du;

	@Value("${roda.mode}")
	private String rodaMode = "server-data";

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// root context
			log.info("event.getApplicationContext() = " + event.getApplicationContext());

			// check if we are in "test mode"
			// (and the properties file has set a property)
			try {
				Resource resource = new ClassPathResource("roda.properties");
				Properties props = PropertiesLoaderUtils.loadProperties(resource);
				rodaMode = props.getProperty("roda.mode");
			} catch (IOException ignored) {
			}

			log.info("roda.mode = " + rodaMode);

			du.executeUpdate("CREATE SCHEMA audit");
			du.truncate();

			rb.rnorm(4);

			// to skip the initial actions,
			// change "run.mode" property to another string
			// (not "server-data")
			if ("server-data".equals(rodaMode)) {
				// log.error(dataSource.getUsername() + ":"
				// + dataSource.getPassword() + ":" + dataSource.getUrl());

				du.initData("csv/");
				du.setSequence("hibernate_sequence", 1000, 1);
				// du.changeData();
				// du.saveXstream();
			}
		}
	}

}
