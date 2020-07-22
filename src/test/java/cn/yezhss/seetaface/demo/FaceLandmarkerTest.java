package cn.yezhss.seetaface.demo;

import cn.yezhss.seetaface.api.FaceDetector;
import cn.yezhss.seetaface.api.FaceLandmarker;
import cn.yezhss.seetaface.demo.base.SeetafaceTest;
import cn.yezhss.seetaface.po.PointWithMask;
import cn.yezhss.seetaface.po.SeetaFaceInfo;
import cn.yezhss.seetaface.po.SeetaFaceInfoArray;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetafaceUtil;

/**
 * 人脸关键点DEMO
 * <pre>
 * 如果觉得不错的话
 * 关注公众号: 时匆
 * 给个小小的支持
 * </pre>
 * @author Onion_Ye
 * @time 2020年6月24日 上午11:56:03
 */
public class FaceLandmarkerTest extends SeetafaceTest {

	/**
	 * 主要展示FaceLandmarker的mark方法, 该方法中需要传入已经识别的人脸区域, 该信息可通过FaceDetector获取
	 * @param args
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午12:03:16
	 */
	public static void main(String[] args) {
		// ---------------------FaceLandmarker---------------------
		FaceLandmarker marker = new FaceLandmarker(CSTA_PATH + "/face_landmarker_pts5.csta");
		SeetaImageData image = SeetafaceUtil.toSeetaImageData(TEST_PICT);
		// ---------------------FaceLandmarker---------------------
		
		FaceDetector detector = new FaceDetector(CSTA_PATH + "/face_detector.csta");
		SeetaFaceInfoArray infos = detector.detect(SeetafaceUtil.toSeetaImageData(TEST_PICT));
		System.out.printf("检测到%s个人脸\n", infos.size);
		int i = 0;
		for (SeetaFaceInfo info : infos.data) {
			SeetaRect face = info.pos;
			
			// ---------------------FaceLandmarker---------------------
			PointWithMask[] marks = marker.mark(image, face);
			// ---------------------FaceLandmarker---------------------
			
			System.out.printf("---第%s张人脸的关键点为---\n", ++i);
			int n = 0;
			for (PointWithMask mask : marks) {
				System.out.printf("第%s个关键点 是否被遮挡: %s, x: %s, y: %s\n", ++n, mask.mask, mask.point.x, mask.point.y);
			}
		}
		
		marker.close();
		detector.close();
	}
	
}
