package ro.roda.thumbnails;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imgscalr.Scalr;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ThumbnailsServiceImpl implements ThumbnailsService {

	private final Log log = LogFactory.getLog(this.getClass());

	@CacheEvict(value = "thumbnails", allEntries = true)
	public void evictAll() {

	}

	public byte[] generateThumbnailByHeight(InputStream inputStream, String fileType, Integer height) {
		return generateThumbnailByHeightAndWidth(inputStream, fileType, height, height);
	}

	@Cacheable(value = "thumbnails", key = "{#root.methodName, #inputStream, #fileType, #height, #width}")
	public byte[] generateThumbnailByHeightAndWidth(InputStream inputStream, String fileType, Integer height,
			Integer width) {
		byte[] thumbnailBytes = null;

		try {
			if (width <= 0 || height <= 0) {
				throw new Exception("Invalid parameters for image width and height");
			}

			BufferedImage bufferedImage = ImageIO.read(inputStream);
			if (bufferedImage != null) {

				float heightRatio = (float) bufferedImage.getHeight() / (float) height;
				float widthRatio = (float) bufferedImage.getWidth() / (float) width;

				boolean ratioH = false;

				if (heightRatio > widthRatio) {
					ratioH = true;
				}

				float fitHeight, fitWidth, x, y;
				if (ratioH) {
					fitHeight = height * widthRatio;
					fitWidth = bufferedImage.getWidth();
					x = 0;
					y = (bufferedImage.getHeight() - fitHeight) / 2;
				} else {
					fitHeight = bufferedImage.getHeight();
					fitWidth = width * heightRatio;
					x = (bufferedImage.getWidth() - fitWidth) / 2;
					y = 0;
				}

				BufferedImage resultImage = Scalr.crop(bufferedImage, (int) x, (int) y, (int) fitWidth,
						(int) fitHeight, (BufferedImageOp[]) null);

				resultImage = Scalr.resize(resultImage, width, height, (BufferedImageOp[]) null);

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				ImageIO.write(resultImage, fileType, baos);
				baos.flush();
				thumbnailBytes = baos.toByteArray();
				baos.close();
			}
		} catch (Exception e) {
			log.error("Exception when generating cropped thumbnail: " + e.getMessage());
		}

		return thumbnailBytes;

	}

	public byte[] generateThumbnailProportionalToWidth(InputStream inputStream, String fileType, Integer width) {
		return generateThumbnailProportional(inputStream, fileType, null, width);
	}

	public byte[] generateThumbnailProportionalToHeight(InputStream inputStream, String fileType, Integer height) {
		return generateThumbnailProportional(inputStream, fileType, height, null);
	}

	@Cacheable(value = "thumbnails", key = "{#root.methodName, #inputStream, #fileType, #height, #width}")
	private byte[] generateThumbnailProportional(InputStream inputStream, String fileType, Integer height, Integer width) {
		byte[] thumbnailBytes = null;
		try {

			BufferedImage bufferedImage = ImageIO.read(inputStream);
			if (bufferedImage != null) {
				int w, h;
				if (height == null) {
					w = width;
					h = (int) ((double) bufferedImage.getHeight() * (double) w / (double) bufferedImage.getWidth());
				} else {
					h = height;
					w = (int) ((double) bufferedImage.getWidth() * (double) h / (double) bufferedImage.getHeight());
				}
				BufferedImage scaledImage = Scalr.resize(bufferedImage, w, h, (BufferedImageOp[]) null);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				ImageIO.write(scaledImage, fileType, baos);
				baos.flush();
				thumbnailBytes = baos.toByteArray();
				baos.close();
			}
		} catch (Exception e) {
			log.error("Exception when generating scaled thumbnail: " + e.getMessage());
		}

		return thumbnailBytes;

	}

}
