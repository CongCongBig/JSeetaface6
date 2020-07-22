package cn.yezhss.seetaface.demo;

import cn.yezhss.seetaface.api.FaceDetector;
import cn.yezhss.seetaface.api.MaskDetector;
import cn.yezhss.seetaface.demo.base.SeetafaceTest;
import cn.yezhss.seetaface.po.MaskStatus;
import cn.yezhss.seetaface.po.SeetaFaceInfo;
import cn.yezhss.seetaface.po.SeetaFaceInfoArray;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetafaceUtil;

/**
 * 口罩检测
 * <pre>
 * 如果觉得不错的话
 * 关注公众号: 时匆
 * 给个小小的支持
 * </pre>
 * @author Onion_Ye
 * @time 2020年6月28日 下午5:27:00
 */
public class MaskDetectorTest extends SeetafaceTest  {

	public static void main(String[] args) {
		FaceDetector detector = new FaceDetector(CSTA_PATH + "/face_detector.csta");
		MaskDetector maskDetector = new MaskDetector(CSTA_PATH + "/mask_detector.csta");
		SeetaImageData image = SeetafaceUtil.toSeetaImageData(TEST_PICT);
		SeetaFaceInfoArray infos = detector.detect(image);
		System.out.printf("检测到%s个人脸\n", infos.size);
		int i = 1;
		for(SeetaFaceInfo info : infos.data) {
			SeetaRect rect = info.pos;
			MaskStatus status = maskDetector.detect(image, rect);
			System.out.printf("第%s张人脸%s口罩,口罩值为:%f\n", i++, status.status ? "戴了" : "没戴", status.score);
		}
		maskDetector.close();
		detector.close();
	}
	
}
