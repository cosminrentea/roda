package ro.roda.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.ContentStreamUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ro.roda.ddi.CodeBook;
import ro.roda.domain.File;
import ro.roda.service.importer.DdiImporterService;

@Service
@Transactional
public class FileServiceImpl implements FileService {

	@Value("${filestore.dir}")
	private final String filestoreDir = "/tmp";

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	SolrServer solrServer;

	@Autowired
	DdiImporterService ddiImporter;

	public long countAllFiles() {
		return File.countFiles();
	}

	public void deleteFile(File file) {
		file.remove();
	}

	public List<File> findAllFiles() {
		return File.findAllFiles();
	}

	public File findFile(Integer id) {
		return File.findFile(id);
	}

	public List<File> findFileEntries(int firstResult, int maxResults) {
		return File.findFileEntries(firstResult, maxResults);
	}

	public void saveFile(File file) {
		file.persist();
	}

	public void saveFile(File file, MultipartFile multipartFile, boolean importDdi) {
		if (multipartFile != null) {
			try {
				String fullPath = filestoreDir + "/" + multipartFile.getOriginalFilename();
				if (importDdi) {
					log.debug("> saveFile > move the file to the local Repository");
					java.io.File f = new java.io.File(fullPath);
					multipartFile.transferTo(f);

					updateSolrFile(f, multipartFile);
					if (multipartFile.getOriginalFilename().endsWith(".ddi")) {
						ddiImporter.importDdiFile((CodeBook) ddiImporter.getUnmarshaller().unmarshal(f), multipartFile,
								true, true, false, null);
					}
				} else {
					log.debug("> saveFile > set properties");
					file.setName(multipartFile.getOriginalFilename());
					file.setSize(multipartFile.getSize());
					file.setFullPath(fullPath);

					log.debug("> saveFile > save JPA object");
					saveFile(file);
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	public File updateFile(File file) {
		return file.merge();
	}

	@Async
	private void updateSolrFile(java.io.File f, MultipartFile content) {
		log.debug("> updateSolrFile");
		if (solrServer != null) {
			ContentStreamUpdateRequest up = new ContentStreamUpdateRequest("/update/extract");
			try {

				// SOLRJ 4.x API
				up.addFile(f, content.getContentType());
				log.trace("content.getContentType() = " + content.getContentType());

				up.setParam("literal.id", content.getOriginalFilename());
				up.setParam("uprefix", "attr_");
				up.setParam("fmap.content", "attr_content");
				up.setAction(ACTION.COMMIT, true, true);

				log.debug("> updateSolrFile > sending file to Solr for metadata indexing");

				solrServer.request(up);

				log.debug("> updateSolrFile > sent to Solr for metadata indexing");

				// log.debug("> saveFile > querying Solr");
				// QueryResponse rsp = solrServer.query(new SolrQuery("id:" +
				// content.getOriginalFilename()));
				// log.trace(rsp);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
		}
	}
}
