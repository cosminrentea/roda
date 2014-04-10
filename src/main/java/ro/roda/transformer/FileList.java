package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;
import ro.roda.filestore.CmsFileStoreService;
import flexjson.JSONSerializer;
import flexjson.transformer.IterableTransformer;
import flexjson.transformer.MapTransformer;

@Configurable
public class FileList extends JsonInfo {

	@Autowired
	CmsFileStoreService cmsFileStoreService;

	private final static String urlDownload = "/admin/cmsfilecontent/";
	
	public class NameValuePair {
		public String name;
		public String value;

		public NameValuePair(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
	}

	public static String toJsonArray(Collection<FileList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "alias", "filesize", "filetype", "folderid", "directory", "leaf");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<FileList> findAllFileLists() {
		List<FileList> result = new ArrayList<FileList>();

		List<CmsFile> files = CmsFile.findAllCmsFiles();

		if (files != null && files.size() > 0) {

			Iterator<CmsFile> filesIterator = files.iterator();
			while (filesIterator.hasNext()) {
				CmsFile file = (CmsFile) filesIterator.next();

				result.add(new FileList(file));
			}
		}

		return result;
	}

	public static FileList findFileList(Integer id) {
		CmsFile file = CmsFile.findCmsFile(id);

		if (file != null) {
			return new FileList(file);
		}
		return null;
	}

	public static String getFilePath(CmsFile file) {
		if (file.getCmsFolderId() != null) {
			return getFolderPath(file.getCmsFolderId()) + "/" + file.getFilename();
		} else {
			return file.getFilename();
		}
	}

	public static String getFolderPath(CmsFolder folder) {
		if (folder.getParentId() != null) {
			return getFolderPath(folder.getParentId()) + "/" + folder.getName();
		} else {
			return folder.getName();
		}
	}

	private Integer id;

	private String name;

	private String alias;

	private Long filesize;

	private String filetype;

	private String url;

	private Set<NameValuePair> filepropertiesset;

	private Integer folderid;

	private String directory;

	private boolean leaf = true;

	private String iconCls;

	public FileList(Integer id, String name, String alias, Long filesize, String filetype,
			Integer folderid, String directory, String iconCls) {
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.url =  urlDownload + id;
		this.directory = directory;
		this.filesize = filesize;
		this.filetype = filetype;
		this.filepropertiesset = null;
		this.folderid = folderid;
		this.iconCls = iconCls;
	}

	public FileList(CmsFile file) {
		this(file.getId(), file.getFilename(), file.getLabel(), file
				.getFilesize(), file.getContentType(), file.getCmsFolderId() == null ? null : file.getCmsFolderId()
				.getId(), getFilePath(file), "file");
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Set<NameValuePair> getFilepropertiesset() {
		Map<String, String> fp = cmsFileStoreService.getFileProperties(CmsFile.findCmsFile(id));
		filepropertiesset = new HashSet<NameValuePair>();
		for (Map.Entry<String, String> p : fp.entrySet()) {
			filepropertiesset.add(new NameValuePair(p.getKey(), p.getValue()));
		}
		return filepropertiesset;
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public Integer getFolderid() {
		return folderid;
	}

	public void setFolderid(Integer folderid) {
		this.folderid = folderid;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "alias", "filesize", "filetype", "folderid", "directory", "leaf");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	public String toJsonDetailed() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name", "alias", "url", "filesize", "filetype", "filepropertiesset", "folderid",
				"directory", "leaf");

		serializer.transform(new FieldNameTransformer("indice"), "id");
		// serializer.transform(new IterableTransformer(), "filepropertiesset");

		return "{\"data\":" + serializer.deepSerialize(this) + "}";
	}

}
