package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.QualityOfBrightnessNative;
import cn.yezhss.seetaface.po.QualityResult;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 非深度的人脸亮度评估器
 * @author Onion_Ye
 * @time 2020年7月3日 下午3:02:17
 */
public class QualityOfBrightness implements Closeable {

	private final long NATIVE_ID;
	
	private boolean isClose = false;
	
	/**
	 * 默认值为{level0:70, 100, 210, 230}
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:26:58
	 */
	public QualityOfBrightness() {
		NATIVE_ID = QualityOfBrightnessNative.init();
	}
	
	/**
	 * 分类依据为[0, v0) & [v3, ~) => LOW;[v0, v1) & [v2, v3) => MEDIUM;[v1, v2) => HIGH;
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:27:12
	 */
	public QualityOfBrightness(float v0, float v1, float v2, float v3) {
		NATIVE_ID = QualityOfBrightnessNative.init(v0, v1, v2, v3);
	}
	
	/**
	 * 检测人脸亮度
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param points 人脸特征点数组
	 * @return 人脸亮度检测结果
	 * @author Onion_Ye
	 * @time 2020年7月6日 上午11:41:53
	 */
	public QualityResult check(SeetaImageData image, SeetaRect face, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, face, points);
		return QualityOfBrightnessNative.check(NATIVE_ID, image, face, points);
	}
	
	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月21日 上午9:36:58
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		QualityOfBrightnessNative.close(NATIVE_ID);
		isClose = true;
	}
	
}
