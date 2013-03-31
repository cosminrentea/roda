package ro.roda.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.domain.File;

public class FileServiceImpl implements FileService {

	@Autowired
	SolrServer solrServer;

	private final Log log = LogFactory.getLog(FileServiceImpl.class);

	private final static String baseFolder = "/tmp/";

	public void saveFile(File file, MultipartFile content) {
		log.debug("> saveFile");
		if (content != null) {
			try {
				log.debug("> saveFile > set properties");
				file.setName(content.getOriginalFilename());
				file.setSize(content.getSize());
				String fullPath = baseFolder + content.getOriginalFilename();
				file.setFullPath(fullPath);

				log.debug("> saveFile > transfering the uploaded file to local folder/repository");
				java.io.File f = new java.io.File(fullPath);
				content.transferTo(f);

				log.debug("> saveFile > sending to Solr for metadata indexing");
				ContentStreamUpdateRequest up = new ContentStreamUpdateRequest(
						"/update/extract");

				up.addFile(f);
				up.setParam("literal.id", content.getOriginalFilename());
				up.setParam("uprefix", "attr_");
				up.setParam("fmap.content", "attr_content");
				up.setAction(ACTION.COMMIT, true, true);

				solrServer.request(up);

//				log.debug("> saveFile > querying Solr");
//				QueryResponse rsp = solrServer.query(new SolrQuery("id:" + content.getOriginalFilename()));
//				log.trace(rsp);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.debug("> saveFile > save JPA object");
		saveFile(file);
		log.trace("> saveFile > Saved: " + file);
	}
}
