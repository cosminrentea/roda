package ro.roda.plugin;

public interface RodaPageService {

	public abstract String[] generatePage(String url);

	public abstract void evict(String url);
	
	public abstract void evictAll();

}
