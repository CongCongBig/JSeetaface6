package cn.yezhss.seetaface.demo;

import java.util.Arrays;

import cn.yezhss.seetaface.api.FaceDetector;
import cn.yezhss.seetaface.api.FaceLandmarker;
import cn.yezhss.seetaface.api.FaceRecognizer;
import cn.yezhss.seetaface.demo.base.SeetafaceTest;
import cn.yezhss.seetaface.po.PointWithMask;
import cn.yezhss.seetaface.po.SeetaFaceInfo;
import cn.yezhss.seetaface.po.SeetaFaceInfoArray;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.util.SeetafaceUtil;

/**
 * 人脸识别demo
 * <pre>
 * 如果觉得不错的话
 * 关注公众号: 时匆
 * 给个小小的支持
 * </pre>
 * @author Onion_Ye
 * @time 2020年7月15日 上午11:58:14
 */
public class FaceRecognizerTest extends SeetafaceTest {

	private static FaceRecognizer recognizer;
	private static FaceDetector detector;
	private static FaceLandmarker marker;

	public static void main(String[] args) {
		String fileName = "D:/test/人脸库/陈赫/1.jpg";
		String fileName2 = "D:/test/人脸库/陈赫/2.jpeg";
		// 初始化人脸识别器
		recognizer = new FaceRecognizer(CSTA_PATH + "/face_recognizer.csta");
		
		detector = new FaceDetector(CSTA_PATH + "/face_detector.csta");
		marker = new FaceLandmarker(CSTA_PATH + "/face_landmarker_pts5.csta");
		
		float[] features1 = extract(fileName);
		float[] features2 = extract(fileName2);
		
		if (features1 != null && features2 != null) {
			float calculateSimilarity = recognizer.calculateSimilarity(features1, features2);
			System.out.printf("相似度:%f\n", calculateSimilarity);
		}
	}
	
	/**
	 * 获取特征数组
	 * @author Onion_Ye
	 * @time 2020年7月15日 下午12:10:56
	 */
	private static float[] extract(String fileName) {
		SeetaImageData image = SeetafaceUtil.toSeetaImageData(fileName);
		SeetaFaceInfoArray infos = detector.detect(image);
		for(SeetaFaceInfo info : infos.data) {
			PointWithMask[] marks = marker.mark(image, info.pos);
			SeetaPointF[] points = new SeetaPointF[marks.length];
			for (int n = 0; n < marks.length; n++) {
				points[n] = marks[n].point;
			}
			return recognizer.extract(image, points);
		}
		return null;
	}
	
}
