package ro.roda.service.importer;

public interface ImporterService {

	public abstract void importElsst() throws Exception;

	public abstract void importCmsFiles() throws Exception;

	public abstract void importCmsLayouts() throws Exception;

	public abstract void importCmsSnippets() throws Exception;

	public abstract void importCmsPages() throws Exception;

}
