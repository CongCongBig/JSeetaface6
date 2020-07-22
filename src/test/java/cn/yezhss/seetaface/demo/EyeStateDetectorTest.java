package cn.yezhss.seetaface.demo;

import cn.yezhss.seetaface.api.EyeStateDetector;
import cn.yezhss.seetaface.api.FaceDetector;
import cn.yezhss.seetaface.api.FaceLandmarker;
import cn.yezhss.seetaface.demo.base.SeetafaceTest;
import cn.yezhss.seetaface.po.EyeState;
import cn.yezhss.seetaface.po.PointWithMask;
import cn.yezhss.seetaface.po.SeetaFaceInfo;
import cn.yezhss.seetaface.po.SeetaFaceInfoArray;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetafaceUtil;

/**
 * 
 * @author Onion_Ye
 * @time 2020年7月2日 上午11:56:35
 */
public class EyeStateDetectorTest extends SeetafaceTest {

	public static void main(String[] args) {
		FaceDetector detector = new FaceDetector(CSTA_PATH + "/face_detector.csta");
		FaceLandmarker marker = new FaceLandmarker(CSTA_PATH + "/face_landmarker_pts5.csta");
		EyeStateDetector eyeStateDetector = new EyeStateDetector(CSTA_PATH + "/eye_state.csta");
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
			EyeState state = eyeStateDetector.detect(image, points);
			System.out.printf("第%s张人脸左眼状态:%s,右眼状态:%s\n", i++, state2Desc(state.left), state2Desc(state.right));
		}
		eyeStateDetector.close();
		marker.close();
		detector.close();
	}
	
	public static String state2Desc(int state) {
		switch(state) {
		case 0: return "闭眼";
		case 1: return "睁开";
		case 2: return "非眼部区域";
		}
		return "不知道";
	}
	
}
