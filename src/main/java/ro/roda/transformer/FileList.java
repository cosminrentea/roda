package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.CmsFile;
import ro.roda.domain.CmsFolder;
import flexjson.JSONSerializer;

@Configurable
public class FileList extends JsonInfo {

	public static String toJsonArray(Collection<FileList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
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

	private Integer folderid;

	private String directory;

	private boolean leaf = true;

	public FileList(Integer id, String name, String alias, Long filesize, String filetype, Integer folderid,
			String directory) {
		this.id = id;
		this.name = name;
		this.alias = alias;
		this.directory = directory;
		this.filesize = filesize;
		this.filetype = filetype;
		this.folderid = folderid;
	}

	public FileList(CmsFile file) {
		this(file.getId(), file.getFilename(), file.getLabel(), file.getFilesize(), file.getFilename().substring(
				file.getFilename().lastIndexOf(".")), file.getCmsFolderId() == null ? null : file.getCmsFolderId()
				.getId(), getFilePath(file));
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

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
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

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class");
		serializer.include("id", "name", "alias", "filesize", "filetype", "folderid", "directory", "leaf");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
