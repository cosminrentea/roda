package ro.roda.service.importer;

public interface CmsImporterService {

	public abstract void importCmsFiles() throws Exception;

	public abstract void importCmsLayouts() throws Exception;

	public abstract void importCmsSnippets() throws Exception;

	public abstract void importCmsPages() throws Exception;

}
