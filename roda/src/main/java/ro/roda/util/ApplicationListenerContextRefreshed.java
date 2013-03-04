package ro.roda.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ApplicationListenerContextRefreshed implements
		ApplicationListener<ContextRefreshedEvent> {

	private final Log log = LogFactory
			.getLog(ApplicationListenerContextRefreshed.class);

	@Autowired
	BasicDataSource dataSource;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			// root context
			log.info("event.getApplicationContext() = "
					+ event.getApplicationContext());
			String runMode = "server";
			try {
				// check if we are in "test mode"
				// (and the properties file has set a property)
				Resource resource = new ClassPathResource("/env.properties");
				Properties props = PropertiesLoaderUtils
						.loadProperties(resource);
				runMode = props.getProperty("run.mode");
			} catch (IOException ignored) {
			}
			log.info("run.mode = " + runMode);
			if ("server".equals(runMode)) {
				log.error(dataSource.getUsername() + ":"
						+ dataSource.getPassword() + ":" + dataSource.getUrl());
				DatabaseUtils du = new DatabaseUtils(dataSource.getUsername(), dataSource.getPassword(),
						dataSource.getUrl());
				
//				DatabaseUtils du = new DatabaseUtils("roda", "roda",
//						"jdbc:postgresql://localhost:5432/roda");
				du.truncate();
				du.initData();
			}
		}
	}
}
