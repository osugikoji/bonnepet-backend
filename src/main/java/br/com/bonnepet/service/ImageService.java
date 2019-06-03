package br.com.bonnepet.service;

import br.com.bonnepet.service.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

	private static final Integer SIZE = 200;

	private static final String JPG_FORMAT = "jpg";

	private static final String JPEG_FORMAT = "jpeg";

	private static final String PNG_FORMAT = "png";

	private static final String JPG_FORMAT_UPPER_CASE = "JPG";

	private static final String JPEG_FORMAT_UPPER_CASE = "JPEG";

	private static final String PNG_FORMAT_UPPER_CASE = "PNG";

	public InputStream getProfileFormat(MultipartFile multipartFile) {
		BufferedImage jpgImage = getJpgImageFromFile(multipartFile);
		return getInputStream(jpgImage, JPG_FORMAT);

	}

	private BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
		String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
		if(!PNG_FORMAT.equals(ext) && !JPG_FORMAT.equals(ext) && !JPEG_FORMAT.equals(ext) && !JPG_FORMAT_UPPER_CASE.equals(ext) && !JPEG_FORMAT_UPPER_CASE.equals(ext) && !PNG_FORMAT_UPPER_CASE.equals(ext)) {
			throw new FileException("Somente imagens PNG e JPG são permitidas");
		}
		
		try {
			BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
			if(PNG_FORMAT.equals(ext)) {
				img = pngToJpg(img);
			}
			return img;
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	private BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}

	private InputStream getInputStream(BufferedImage img, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(img, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		} catch (IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}

	private BufferedImage cropSquare(BufferedImage sourceImg) {
		int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
		return Scalr.crop(
				sourceImg,
				(sourceImg.getWidth()/2) - (min/2),
				(sourceImg.getHeight()/2) - (min/2),
				min,
				min);
	}

	private BufferedImage resize(BufferedImage sourceImg, int size) {
		return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
	}
}
