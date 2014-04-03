package ro.roda.thumbnails;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imgscalr.Scalr;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.CmsFile;

@Service
@Transactional
public class ThumbnailsServiceImpl implements ThumbnailsService {

	private final Log log = LogFactory.getLog(this.getClass());

	public byte[] generateThumbnailByHeight(String url, String alias, Integer height) {
		return generateThumbnailByHeightAndWidth(url, alias, height, height);
	}

	@Cacheable(value = "thumbnails", key = "{#root.methodName, #url, #alias, #height, #width}")
	public byte[] generateThumbnailByHeightAndWidth(String url, String alias, Integer height, Integer width) {
		byte[] thumbnailBytes = null;
		try {
			CmsFile imageFile = CmsFile.findCmsFile(alias);
			String fileUrlString = url + imageFile.getUrl();

			InputStream inputUrl = null;

			try {
				inputUrl = new URL(fileUrlString).openStream();
			} catch (Exception e) {
				e.printStackTrace();
			}

			BufferedImage bufferedImage = ImageIO.read(inputUrl);
			if (bufferedImage != null) {
				BufferedImage croppedImage = Scalr.crop(bufferedImage, bufferedImage.getWidth() > width ? width
						: bufferedImage.getWidth(),
						bufferedImage.getHeight() > height ? height : bufferedImage.getHeight(),
						(BufferedImageOp[]) null);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				String fileType = "";
				if (imageFile != null) {
					// TODO use the content_type field
					String fileName = imageFile.getFilename();
					fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
				}
				fileType = fileType.toLowerCase();

				ImageIO.write(croppedImage, fileType, baos);
				baos.flush();
				thumbnailBytes = baos.toByteArray();
				baos.close();
			}
		} catch (Exception e) {
			log.error("Exception when generating cropped thumbnail: " + e.getMessage());
		}

		return thumbnailBytes;

	}

	public byte[] generateThumbnailProportionalToWidth(String url, String alias, Integer width) {
		return generateThumbnailProportional(url, alias, null, width);
	}

	public byte[] generateThumbnailProportionalToHeight(String url, String alias, Integer height) {
		return generateThumbnailProportional(url, alias, height, null);
	}

	@Cacheable(value = "thumbnails", key = "{#root.methodName, #url, #alias, #height, #width}")
	private byte[] generateThumbnailProportional(String url, String alias, Integer height, Integer width) {
		byte[] thumbnailBytes = null;
		try {
			CmsFile imageFile = CmsFile.findCmsFile(alias);
			String fileUrlString = url + imageFile.getUrl();

			InputStream inputUrl = null;

			try {
				inputUrl = new URL(fileUrlString).openStream();
			} catch (Exception e) {
				e.printStackTrace();
			}

			BufferedImage bufferedImage = ImageIO.read(inputUrl);
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

				String fileType = "";
				if (imageFile != null) {
					// TODO use the content_type field
					String fileName = imageFile.getFilename();
					fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
				}
				fileType = fileType.toLowerCase();

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
