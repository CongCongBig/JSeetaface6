package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.QualityOfIntegrityNative;
import cn.yezhss.seetaface.po.QualityResult;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 非深度学习的人脸完整度评估器，评估人脸靠近图像边缘的程度。
 * @author Onion_Ye
 * @time 2020年7月9日 上午10:23:41
 */
public class QualityOfIntegrity implements Closeable {

	private final long NATIVE_ID;
	
	private boolean isClose = false;
	
	/**
	 * 人脸完整评估器构造函数。
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:37:12
	 */
	public QualityOfIntegrity() {
		NATIVE_ID = QualityOfIntegrityNative.init();
	}
	
	/**
	 * 人脸完整评估器构造函数。 low和high主要来控制人脸位置靠近图像边缘的接受程度。
	 * @param low 分级参数一
	 * @param high 分级参数二
	 * @return QualityOfPose在C++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:37:46
	 */
	public QualityOfIntegrity(float low, float high) {
		NATIVE_ID = QualityOfIntegrityNative.init(low, high);
	}
	
	/**
	 * 评估人脸完整度。
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param points 人脸5个特征数组
	 * @return 人脸姿态检测结果
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:07:02
	 */
	public QualityResult check(SeetaImageData image, SeetaRect face, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, face, points);
		return QualityOfIntegrityNative.check(NATIVE_ID, image, face, points);
	}
	
	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月21日 上午9:32:13
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		QualityOfIntegrityNative.close(NATIVE_ID);
		isClose = true;
	}
	
}
