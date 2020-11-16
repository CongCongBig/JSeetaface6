package cn.yezhss.seetaface.demo;

import java.awt.image.BufferedImage;

import cn.yezhss.seetaface.api.FaceDetector;
import cn.yezhss.seetaface.demo.base.SeetafaceTest;
import cn.yezhss.seetaface.po.SeetaFaceInfo;
import cn.yezhss.seetaface.po.SeetaFaceInfoArray;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetafaceUtil;

/**
 * 人脸检测DEMO
 * <pre>
 * 如果觉得不错的话
 * 关注公众号: 时匆
 * 给个小小的支持
 * </pre>
 * @author Onion_Ye
 * @time 2020年6月23日 下午4:12:56
 */
public class FaceDetectorTest extends SeetafaceTest {

	public static void main(String[] args) {
		FaceDetector detector = new FaceDetector(CSTA_PATH + "/face_detector.csta");
//		detector.set(FaceDetector.Property.PROPERTY_MIN_FACE_SIZE, 40);
//		double d = detector.get(FaceDetector.Property.PROPERTY_MIN_FACE_SIZE);
//		System.out.println(d);
		long start = System.currentTimeMillis();
		BufferedImage image = SeetafaceUtil.toBufferedImage(TEST_PICT);
		image = SeetafaceUtil.resize(image, 480, 320);
		SeetaImageData imageData = SeetafaceUtil.toSeetaImageData(image);
		SeetaFaceInfoArray infos = detector.detect(imageData);
		System.out.println("人脸检测用时:"+ (System.currentTimeMillis()-start));
		System.out.printf("人脸数: %s\n", infos.size);
		int i = 1;
		for (SeetaFaceInfo info : infos.data) {
			System.out.printf("第%s张人脸置信分数: %s\n", i, info.score);
			SeetaRect rect = info.pos;
			System.out.printf("第%s张人脸 x: %s, y: %s, width: %s, height: %s\n", i++, rect.x, rect.y, rect.width, rect.height);
			image = SeetafaceUtil.writeRect(image, rect);
		}
		SeetafaceUtil.show("人脸检测", image);
		
		detector.close();
		
	}
	
}
