package org.ppl.plug.Img;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImgHandle {
	/**
	 * 剪切图片,没有处理图片后缀名是否正确,还有gif动态图片
	 * 
	 * @param sourcePath
	 *            源路径(包含图片)
	 * @param targetPath
	 *            目标路径 null则默认为源路径
	 * @param x
	 *            起点x坐标
	 * @param y
	 *            起点y左边
	 * @param width
	 *            剪切宽度
	 * @param height
	 *            剪切高度
	 * @return 目标路径
	 * @throws IOException
	 *             if sourcePath image doesn't exist
	 */
	public static String cutImage(String sourcePath, String targetPath, int x,
			int y, int width, int height) throws IOException {
		File imageFile = new File(sourcePath);
		if (!imageFile.exists()) {
			throw new IOException("Not found the images:" + sourcePath);
		}
		if (targetPath == null || targetPath.isEmpty())
			targetPath = sourcePath;
		String format = sourcePath.substring(sourcePath.lastIndexOf(".") + 1,
				sourcePath.length());
		BufferedImage image = ImageIO.read(imageFile);
		image = image.getSubimage(x, y, width, height);
		ImageIO.write(image, format, new File(targetPath));
		return targetPath;
	}

	/**
	 * 压缩图片
	 * 
	 * @param sourcePath
	 *            源路径(包含图片)
	 * @param targetPath
	 *            目标路径 null则默认为源路径
	 * @param width
	 *            压缩后宽度
	 * @param height
	 *            压缩后高度
	 * @return 目标路径
	 * @throws IOException
	 *             if sourcePath image does not exist
	 */
	public static String zoom(String sourcePath, String targetPath, int width,
			int height) throws IOException {
		File imageFile = new File(sourcePath);
		if (!imageFile.exists()) {
			throw new IOException("Not found the images:" + sourcePath);
		}
		if (targetPath == null || targetPath.isEmpty())
			targetPath = sourcePath;
		String format = sourcePath.substring(sourcePath.lastIndexOf(".") + 1,
				sourcePath.length());
		BufferedImage image = ImageIO.read(imageFile);
		image = zoom(image, width, height);
		ImageIO.write(image, format, new File(targetPath));
		return targetPath;
	}

	/**
	 * 压缩图片
	 * 
	 * @param sourceImage
	 *            待压缩图片
	 * @param width
	 *            压缩图片高度
	 * @param heigt
	 *            压缩图片宽度
	 */
	private static BufferedImage zoom(BufferedImage sourceImage, int width,
			int height) {
		BufferedImage zoomImage = new BufferedImage(width, height,
				sourceImage.getType());
		Image image = sourceImage.getScaledInstance(width, height,
				Image.SCALE_SMOOTH);
		Graphics gc = zoomImage.getGraphics();
		gc.setColor(Color.WHITE);
		gc.drawImage(image, 0, 0, null);
		return zoomImage;
	}
}
