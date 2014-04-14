package ro.roda.thumbnails;

public interface ThumbnailsService {

	public abstract byte[] generateThumbnailByHeight( String alias, Integer height);

	public abstract byte[] generateThumbnailByHeightAndWidth(String alias, Integer height, Integer width);

	public abstract byte[] generateThumbnailProportionalToWidth(String alias, Integer width);

	public abstract byte[] generateThumbnailProportionalToHeight(String alias, Integer height);

	public abstract void evictAll();

}
