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

import ro.roda.domain.CmsFile;
import ro.roda.filestore.CmsFileStoreService;

@Service
@Transactional
public class ThumbnailsServiceImpl implements ThumbnailsService {

	private final Log log = LogFactory.getLog(this.getClass());

	private CmsFileStoreService cmsFileStoreService;
	
	@CacheEvict(value = "thumbnails", allEntries = true)
	public void evictAll() {
	
	}

	public byte[] generateThumbnailByHeight(String alias, Integer height) {
		return generateThumbnailByHeightAndWidth( alias, height, height);
	}

	@Cacheable(value = "thumbnails", key = "{#root.methodName, #url, #alias, #height, #width}")
	public byte[] generateThumbnailByHeightAndWidth(String alias, Integer height, Integer width) {
		byte[] thumbnailBytes = null;

		try {
			if (width <= 0 || height <= 0) {
				throw new Exception("Invalid parameters for image width and height");
			}
			CmsFile imageFile = CmsFile.findCmsFile(alias);
			//String fileUrlString = url + imageFile.getUrl();

			InputStream inputStream = null;

			try {
				//inputUrl = new URL(fileUrlString).openStream();
			
				inputStream = cmsFileStoreService.fileLoad(imageFile);
				
								
			} catch (Exception e) {
				e.printStackTrace();
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

				String fileType = "";
				if (imageFile != null) {
					// TODO use the content_type field
					String fileName = imageFile.getFilename();
					fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
				}
				fileType = fileType.toLowerCase();

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

	public byte[] generateThumbnailProportionalToWidth(String alias, Integer width) {
		return generateThumbnailProportional(alias, null, width);
	}

	public byte[] generateThumbnailProportionalToHeight(String alias, Integer height) {
		return generateThumbnailProportional(alias, height, null);
	}

	@Cacheable(value = "thumbnails", key = "{#root.methodName, #url, #alias, #height, #width}")
	private byte[] generateThumbnailProportional(String alias, Integer height, Integer width) {
		byte[] thumbnailBytes = null;
		try {
			CmsFile imageFile = CmsFile.findCmsFile(alias);
			//String fileUrlString = url + imageFile.getUrl();

			InputStream inputStream = null;

			try {
				inputStream = cmsFileStoreService.fileLoad(imageFile);;
			} catch (Exception e) {
				e.printStackTrace();
			}

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
