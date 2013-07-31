package ro.roda.service;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
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

import ro.roda.domain.File;

@Service
@Transactional
public class FileServiceImpl implements FileService {

	@Value("${R.filestore.dir}")
	private final static String filestoreDir = "/tmp";

	private final Log log = LogFactory.getLog(this.getClass());

	@Autowired(required = false)
	SolrServer solrServer;

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

	public void saveFile(File file, MultipartFile content) {
		log.debug("> saveFile");
		if (content != null) {
			try {
				log.debug("> saveFile > set properties");
				file.setName(content.getOriginalFilename());
				file.setSize(content.getSize());
				String fullPath = filestoreDir + "/" + content.getOriginalFilename();
				file.setFullPath(fullPath);

				log.debug("> saveFile > transfering the uploaded file to local folder/repository");
				java.io.File f = new java.io.File(fullPath);
				content.transferTo(f);
				updateSolrFile(f, content);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.debug("> saveFile > save JPA object");
		saveFile(file);
		log.trace("> saveFile > saved: " + file);
	}

	public File saveFile(java.io.File srcFile) {
		log.debug("> saveFile");
		if (srcFile != null) {
			try {
				File file = new File();
				log.debug("> saveFile > set properties");
				file.setName(srcFile.getName());
				file.setSize(srcFile.length());
				String fullPath = filestoreDir + "/" + srcFile.getName();
				file.setFullPath(fullPath);

				log.debug("> saveFile > transfering the uploaded file to local folder/repository");
				java.io.File destFile = new java.io.File(fullPath);
				FileUtils.copyFile(srcFile, destFile);

				// TODO upload to Solr ?
				// updateSolrFile(f, content);

				log.debug("> saveFile > save JPA object");
				saveFile(file);
				log.trace("> saveFile > saved: " + file);
				return file;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
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

				// SOLRJ 3.6.1 API
				// up.addFile(f);

				// SOLRJ 4.3.1 API
				up.addFile(f, content.getContentType());

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
				e.printStackTrace();
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
