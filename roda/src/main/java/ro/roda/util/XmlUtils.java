package ro.roda.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;

import ro.roda.domain.Catalog;
import ro.roda.service.CatalogServiceImpl;

import com.thoughtworks.xstream.XStream;

@Component
public class XmlUtils {

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired
	CatalogServiceImpl catalogService;

	@Autowired
	XStreamMarshaller xstreamMarshaller;

	public void saveXstream() {
		for (Catalog c : catalogService.findAllCatalogs()) {
			File file = new File(c.getId() + ".xml");
			log.trace("Catalog XML Filename: " + file.getAbsolutePath());
			Result result;
			try {
				result = new StreamResult(new FileWriter(file));
				xstreamMarshaller.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
				xstreamMarshaller.marshal(c, result);
			} catch (XmlMappingException e) {
				// TODO Auto-generated catch block
				log.error("XmlMappingException:", e);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("IOException:", e);
			}
		}
	}

}
