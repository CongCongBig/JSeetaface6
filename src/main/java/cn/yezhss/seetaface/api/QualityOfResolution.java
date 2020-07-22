package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.QualityOfResolutionNative;
import cn.yezhss.seetaface.po.QualityResult;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 非深度学习的人脸尺寸评估器。
 * @author Onion_Ye
 * @time 2020年7月9日 上午10:23:41
 */
public class QualityOfResolution implements Closeable {
	
	private final long NATIVE_ID;
	
	private boolean isClose = false;
	
	/**
	 * 人脸尺寸评估器构造函数。
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:13:55
	 */
	public QualityOfResolution() {
		NATIVE_ID = QualityOfResolutionNative.init();
	}
	
	/**
	 * 人脸尺寸评估器构造函数。 
	 * @param low 分级参数一
	 * @param high 分级参数二
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:13:55
	 */
	public QualityOfResolution(float low, float high) {
		NATIVE_ID = QualityOfResolutionNative.init(low, high);
	}

	/**
	 * 评估人脸尺寸
	 * @param nativeId QualityOfPose在C++的序列化
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param points 人脸5个特征数组
	 * @return 人脸姿态检测结果
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:07:02
	 */
	public QualityResult check(SeetaImageData image, SeetaRect face, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, face, points);
		return QualityOfResolutionNative.check(NATIVE_ID, image, face, points);
	}

	/**
	 * 释放流
	 * @author Onion_Ye
	 * @time 2020年7月20日 上午9:55:42
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		QualityOfResolutionNative.close(NATIVE_ID);
		this.isClose = true;
	}
	
}
