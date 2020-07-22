package cn.yezhss.seetaface.demo;

import cn.yezhss.seetaface.api.FaceAntiSpoofing;
import cn.yezhss.seetaface.api.FaceAntiSpoofing.Status;
import cn.yezhss.seetaface.api.FaceDetector;
import cn.yezhss.seetaface.api.FaceLandmarker;
import cn.yezhss.seetaface.demo.base.SeetafaceTest;
import cn.yezhss.seetaface.po.PointWithMask;
import cn.yezhss.seetaface.po.SeetaFaceInfo;
import cn.yezhss.seetaface.po.SeetaFaceInfoArray;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetafaceUtil;

/**
 * 活体检测
 * <pre>
 * 如果觉得不错的话
 * 关注公众号: 时匆
 * 给个小小的支持
 * </pre>
 * @author Onion_Ye
 * @time 2020年7月2日 上午11:56:35
 */
public class FaceAntiSpoofingTest extends SeetafaceTest {

	public static void main(String[] args) {
		FaceDetector detector = new FaceDetector(CSTA_PATH + "/face_detector.csta");
		FaceLandmarker marker = new FaceLandmarker(CSTA_PATH + "/face_landmarker_pts5.csta");
		FaceAntiSpoofing faceAntiSpoofing = new FaceAntiSpoofing(CSTA_PATH + "/fas_first.csta");
		SeetaImageData image = SeetafaceUtil.toSeetaImageData(TEST_PICT);
		SeetaFaceInfoArray infos = detector.detect(image);
		System.out.printf("检测到%s个人脸\n", infos.size);
		int i = 1;
		for(SeetaFaceInfo info : infos.data) {
			SeetaRect rect = info.pos;
			PointWithMask[] marks = marker.mark(image, rect);
			SeetaPointF[] points = new SeetaPointF[marks.length];
			for (int n = 0; n < marks.length; n++) {
				points[n] = marks[n].point;
			}
			Status status = faceAntiSpoofing.predict(image, rect, points);
			System.out.printf("第%s张脸活体检测结果为:%s\n", i++, status);
		}
		faceAntiSpoofing.close();
		marker.close();
		detector.close();
	}
	
}
