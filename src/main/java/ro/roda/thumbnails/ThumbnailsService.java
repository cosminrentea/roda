package ro.roda.thumbnails;

public interface ThumbnailsService {

	public abstract byte[] generateThumbnailByHeight(String url, String alias, Integer height);

	public abstract byte[] generateThumbnailByHeightAndWidth(String url, String alias, Integer height, Integer width);

	public abstract byte[] generateThumbnailProportionalToWidth(String url, String alias, Integer width);

	public abstract byte[] generateThumbnailProportionalToHeight(String url, String alias, Integer height);

	public abstract void evictAll();

}
