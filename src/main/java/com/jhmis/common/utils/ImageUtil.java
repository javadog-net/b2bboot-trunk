package com.jhmis.common.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.springframework.util.StreamUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * 图片处理辅助类
 * 
 */
public final class ImageUtil {
	public static final int BUFFER_SIZE = StreamUtils.BUFFER_SIZE;
	private ImageUtil() {
	}

	/**
	 * * 根据宽高缩放
	 * 
	 * @param img 图片文件
	 * @param width 图片宽
	 * @param height 图片高
	 */
	public static final void scale(File img, int width, int height) {
		try {
			Thumbnails.of(img).size(width, height).keepAspectRatio(false).toFile(img);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("图片转换出错！", e);
		}
	}

	/**
	 * * 根据宽高缩放
	 *
	 * @param img 图片文件
	 * @param width 图片宽
	 * @param height 图片高
	 */
	public static final void scale(File img, int width, int height, String targetFile) {
		try {
			Thumbnails.of(img).size(width, height).keepAspectRatio(false).toFile(targetFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("图片转换出错！", e);
		}
	}

	/**
	 * * 根据宽高缩放
	 *
	 * @param inputStream 图片文件流
	 * @param width 图片宽
	 * @param height 图片高
	 */
	public static final void scale(InputStream inputStream, int width, int height, String targetFile) {
		try {
			Thumbnails.of(inputStream).size(width, height).keepAspectRatio(false).toFile(targetFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("图片转换出错！", e);
		}
	}

	/**
	 * * 根据宽高缩放
	 *
	 * @param content 图片字节
	 * @param width 图片宽
	 * @param height 图片高
	 */
	public static final void scale(byte[] content, int width, int height, String targetFile) {
		try {
			InputStream inputStream = new ByteArrayInputStream(content);
			Thumbnails.of(inputStream).size(width, height).keepAspectRatio(false).toFile(targetFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("图片转换出错！", e);
		}
	}

	/**
	 * * 根据宽高缩放
	 *
	 * @param bufferedImg 图片文件
	 * @param width 图片宽
	 * @param height 图片高
	 */
	public static final byte[] scale(BufferedImage bufferedImg, int width, int height) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		try {
			Thumbnails.of(bufferedImg).size(width, height).keepAspectRatio(false).toOutputStream(out);
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("图片转换出错！", e);
		} finally {
			out.close();
		}
	}

	/**
	 * * 根据宽高缩放
	 *
	 * @param content 图片字节
	 * @param width 图片宽
	 * @param height 图片高
	 */
	public static final byte[] scale(byte[] content, int width, int height) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		try {
			InputStream inputStream = new ByteArrayInputStream(content);
			Thumbnails.of(inputStream).size(width, height).keepAspectRatio(false).toOutputStream(out);
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("图片转换出错！", e);
		} finally {
			out.close();
		}
	}

	/**
	 * * 根据宽高缩放
	 *
	 * @param inputStream 图片流
	 * @param width 图片宽
	 * @param height 图片高
	 */
	public static final byte[] scale(InputStream inputStream, int width, int height) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		try {
			Thumbnails.of(inputStream).size(width, height).keepAspectRatio(false).toOutputStream(out);
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("图片转换出错！", e);
		} finally {
			out.close();
		}
	}
	/**
	 * 根据比例缩放图片
	 * 
	 * @param orgImg 源图片路径
	 * @param scale 比例
	 * @param targetFile 缩放后的图片存放路径
	 * @throws IOException
	 */
	public static final void scale(BufferedImage orgImg, double scale, String targetFile) throws IOException {
		Thumbnails.of(orgImg).scale(scale).toFile(targetFile);
	}

	public static final void scale(String orgImgFile, double scale, String targetFile) throws IOException {
		Thumbnails.of(orgImgFile).scale(scale).toFile(targetFile);
	}

	/**
	 * 图片格式转换
	 * 
	 * @param orgImgFile
	 * @param width
	 * @param height
	 * @param suffixName
	 * @param targetFile
	 * @throws IOException
	 */
	public static final void format(String orgImgFile, int width, int height, String suffixName, String targetFile)
			throws IOException {
		Thumbnails.of(orgImgFile).size(width, height).outputFormat(suffixName).toFile(targetFile);
	}

	/**
	 * 根据宽度同比缩放
	 * 
	 * @param orgImg 源图片
	 * @param targetWidth 缩放后的宽度
	 * @param targetFile 缩放后的图片存放路径
	 * @throws IOException
	 */
	public static final double scaleWidth(BufferedImage orgImg, int targetWidth, String targetFile) throws IOException {
		int orgWidth = orgImg.getWidth();
		// 计算宽度的缩放比例
		double scale = targetWidth * 1.00 / orgWidth;
		// 裁剪
		scale(orgImg, scale, targetFile);

		return scale;
	}
	

	public static final void scaleWidth(String orgImgFile, int targetWidth, String targetFile) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new File(orgImgFile));
		scaleWidth(bufferedImage, targetWidth, targetFile);
	}

	/**
	 * 根据高度同比缩放
	 * 
	 * @param orgImg 源图片
	 * @param targetHeight 缩放后的高度
	 * @param targetFile 缩放后的图片存放地址
	 * @throws IOException
	 */
	public static final double scaleHeight(BufferedImage orgImg, int targetHeight, String targetFile) throws IOException {
		int orgHeight = orgImg.getHeight();
		double scale = targetHeight * 1.00 / orgHeight;
		scale(orgImg, scale, targetFile);
		return scale;
	}

	public static final void scaleHeight(String orgImgFile, int targetHeight, String targetFile) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new File(orgImgFile));
		// int height = bufferedImage.getHeight();
		scaleHeight(bufferedImage, targetHeight, targetFile);
	}

	// 原始比例缩放
	public static final void scaleWidth(File file, Integer width) throws IOException {
		String fileName = file.getName();
		String filePath = file.getAbsolutePath();
		String postFix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
		// 缩放
		BufferedImage bufferedImg = ImageIO.read(file);
		String targetFile = filePath + "_s" + postFix;
		scaleWidth(bufferedImg, width, targetFile);
		String targetFile2 = filePath + "@" + width;
		new File(targetFile).renameTo(new File(targetFile2));
	}
	//图片原始比例缩放
	public static final void scaleHeight(File file, Integer Height) throws IOException {
		String fileName = file.getName();
		String filePath = file.getAbsolutePath();
		String postFix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
		// 缩放
		BufferedImage bufferedImg = ImageIO.read(file);
		String targetFile = filePath + "_s" + postFix;
		scaleHeight(bufferedImg, Height, targetFile);
		String targetFile2 = filePath + "@" + Height;
		new File(targetFile).renameTo(new File(targetFile2));
	}
	// 原始比例缩放
		public static final String scaleWidthS(File file, Integer width,String urlPath) throws IOException {
			String fileName = file.getName();
			String filePath = file.getPath();
			// 缩放
			BufferedImage bufferedImg = ImageIO.read(file);
			String targetFile = filePath + "_" +fileName;
			scaleWidth(bufferedImg, width,targetFile);
			Thumbnails.of(targetFile).sourceRegion(Positions.CENTER, 120, 120).size(120, 120).keepAspectRatio(false).toFile(filePath);
			new File(targetFile).delete();
			return filePath;
		}
		//图片原始比例缩放
		public static final String scaleHeightS(File file, Integer Height,String urlPath) throws IOException {
			String fileName = file.getName();
			String filePath = file.getPath();
			System.out.println("filePath="+filePath+",fileName="+fileName);
			// 缩放
			BufferedImage bufferedImg = ImageIO.read(file);
			String targetFile = filePath + "_s"+fileName;
			scaleHeight(bufferedImg, Height, targetFile);	
			Thumbnails.of(targetFile).sourceRegion(Positions.CENTER, 120, 120).size(120, 120).keepAspectRatio(false).toFile(filePath);
			new File(targetFile).delete();
			return filePath;
		}
}
