package cn.yezhss.seetaface.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaRect;

/**
 * 
 * @author YeZhiCong
 * @time 2020年6月18日 下午1:12:42
 */
public class SeetafaceUtil {
	
	private static final int[] BGR_TYPE = {0, 1, 2};

	/**
	 * 将BufferedImage转为SeetaImage
	 * @param bufferedImage 图片
	 * @return BGR属性
	 * @author YeZhiCong
	 * @time 2020年6月18日 下午1:14:39
	 */
	public static SeetaImageData toSeetaImageData(BufferedImage bufferedImage) {
		if (bufferedImage == null) {
			return null;
		}
		try {
			SeetaImageData imageData = new SeetaImageData();
			imageData.width = bufferedImage.getWidth();
			imageData.height = bufferedImage.getHeight();
			imageData.channels = 3;
			imageData.data = getBgr(bufferedImage);
			return imageData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 图片转bgr字节数组
	 * @author YeZhiCong
	 * @time 2020年7月9日 下午2:28:42
	 */
	private static byte[] getBgr(BufferedImage image) {
		byte[] matrixBGR;
		if (isBgr(image)) {
			matrixBGR = (byte[]) image.getData().getDataElements(0, 0, image.getWidth(), image.getHeight(), null);
		} else {
			// ARGB格式图像数据
			int intrgb[] = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			matrixBGR = new byte[image.getWidth() * image.getHeight() * 3];
			int len = intrgb.length;
			// ARGB转BGR格式
			for (int i = 0; i < len; i++) {
				matrixBGR[i * 3] = (byte) (intrgb[i] & 0xff);
				matrixBGR[i * 3 + 1] = (byte) ((intrgb[i] >> 8) & 0xff);
				matrixBGR[i * 3 + 2] = (byte) ((intrgb[i] >> 16) & 0xff);
			}
		}
		return matrixBGR;
	}

	/**
	 * 判断是否为bgr
	 * @author YeZhiCong
	 * @time 2020年7月9日 下午2:29:00
	 */
	private static boolean isBgr(BufferedImage image) {
		if (image.getType() == BufferedImage.TYPE_3BYTE_BGR && image.getData().getSampleModel() instanceof ComponentSampleModel) {
			ComponentSampleModel sampleModel = (ComponentSampleModel) image.getData().getSampleModel();
			return Arrays.equals(sampleModel.getBandOffsets(), BGR_TYPE);
		}
		return false;
	}

	/**
	 * 转为seetaImageData
	 * @param path 路径
	 * @author YeZhiCong
	 * @time 2020年6月19日 下午9:06:39
	 */
	public static SeetaImageData toSeetaImageData(String path) {
		return toSeetaImageData(new File(path));
	}
	
	/**
	 * 转为seetaImageData
	 * @author YeZhiCong
	 * @time 2020年6月19日 下午9:06:39
	 */
	public static SeetaImageData toSeetaImageData(File file) {
		return toSeetaImageData(toBufferedImage(file));
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @author YeZhiCong
	 * @time 2020年7月9日 下午3:03:42
	 */
	public static BufferedImage toBufferedImage(String path) {
		return toBufferedImage(new File(path));
	}

	/**
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 * @author YeZhiCong
	 * @time 2020年7月9日 下午3:03:42
	 */
	public static BufferedImage toBufferedImage(File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
		}
		return image;
	}
	
	/**
	 * bgr转图片
	 * @param data 数据
	 * @param width 宽
	 * @param height 高
	 * @return 图片
	 * @author YeZhiCong
	 * @time 2020年7月9日 下午2:30:02
	 */
	public static BufferedImage toBufferedImage(byte[] data, int width, int height) {
        int type = BufferedImage.TYPE_3BYTE_BGR;
        // bgr to rgb
//        byte b;
//        for (int i = 0; i < data.length; i = i + 3) {
//            b = data[i];
//            data[i] = data[i + 2];
//            data[i + 2] = b;
//        }
        BufferedImage image = new BufferedImage(width, height, type);
        image.getRaster().setDataElements(0, 0, width, height, data);
        return image;
    }
	
	public static BufferedImage toBufferedImage(SeetaImageData image) {
		return toBufferedImage(image.data, image.width, image.height);
	}
	
	/**   
     * 实现图像的等比缩放   
     * @param source   
     * @param targetW   
     * @param targetH   
     * @return   
     */    
	public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
		// targetW，targetH分别表示目标长和宽
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		// 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
		// 则将下面的if else语句注释即可
		if (sx < sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { // handmade
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else {
			target = new BufferedImage(targetW, targetH, type);
		}
		Graphics2D g = target.createGraphics();
		// smoother than exlax:
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}  
	
	public static void writeRect(BufferedImage image, SeetaRect rect) {
		Graphics gra = image.getGraphics();
		gra.setColor(Color.RED);
		gra.drawRect(rect.x, rect.y, rect.width, rect.height);
	}
	
	public static void show(String title, BufferedImage image) {
		JFrame frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(image.getWidth(), image.getHeight());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JLabel label = new JLabel();
		frame.add(label);
		frame.setVisible(true);
		ImageIcon icon = new ImageIcon(image);
		label.setIcon(icon);
	}

}
