package ro.roda.service.thumbnails;

import java.io.InputStream;

import org.springframework.security.access.prepost.PreAuthorize;

public interface ThumbnailsService {

	public abstract byte[] generateThumbnailByHeight(InputStream inputStream, String fileType, Integer height);

	public abstract byte[] generateThumbnailByHeightAndWidth(InputStream inputStream, String fileType, Integer height,
			Integer width);

	public abstract byte[] generateThumbnailProportionalToWidth(InputStream inputStream, String fileType, Integer width);

	public abstract byte[] generateThumbnailProportionalToHeight(InputStream inputStream, String fileType,
			Integer height);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public abstract void evictAll();

}
