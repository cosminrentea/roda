package ro.roda.service.solr;

public interface SolrService {

	public abstract String baseUrl(String language);

	public abstract boolean deleteAll();

	public abstract boolean ping();
}
