package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.QualityOfClarityNative;
import cn.yezhss.seetaface.po.QualityResult;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 非深度学习的人脸清晰度评估器
 * @author Onion_Ye
 * @time 2020年7月6日 上午11:53:09
 */
public class QualityOfClarity implements Closeable {

	private final long NATIVE_ID;
	
	private boolean isClose = false;
	
	/**
	 * 默认值为low=0.1 high=0.2 {@link this#init(float, float)}
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:28:59
	 */
	public QualityOfClarity() {
		NATIVE_ID = QualityOfClarityNative.init();
	}
	
	/**
	 * <pre>
	 * [0, low)=> LOW 0
	 * [low, high)=> MEDIUM 1
	 * [high, ~)=> HIGH 2
	 *</pre>
	 * @param low 分级参数一
	 * @param high 分级参数二
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:28:59
	 */
	public QualityOfClarity(float low, float high) {
		NATIVE_ID = QualityOfClarityNative.init(low, high);
	}
	
	/**
	 * 检测人脸清晰度
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param points 人脸特征点数组
	 * @return 人脸清晰度检测结果
	 * @author Onion_Ye
	 * @time 2020年7月6日 下午6:26:19
	 */
	public QualityResult check(SeetaImageData image, SeetaRect face, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, face, points);
		return QualityOfClarityNative.check(NATIVE_ID, image, face, points);
	}
	
	public void close() {
		SeetaAssert.validate(isClose);
		QualityOfClarityNative.close(NATIVE_ID);
		isClose = true;
	}
	
}
