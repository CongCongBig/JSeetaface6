package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.QualityOfPoseNative;
import cn.yezhss.seetaface.po.QualityResult;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 非深度学习的人脸姿态评估器
 * @author Onion_Ye
 * @time 2020年7月9日 上午10:23:41
 */
public class QualityOfPose implements Closeable {
	
	private final long NATIVE_ID;
	
	private boolean isClose = false;

	/**
	 * 非深度学习的人脸姿态评估器
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:12:03
	 */
	public QualityOfPose() {
		NATIVE_ID = QualityOfPoseNative.init();
	}
	
	/**
	 * 检测人脸姿态
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param points 人脸5个特征数组
	 * @return 人脸姿态检测结果
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:07:02
	 */
	public QualityResult check(SeetaImageData image, SeetaRect face, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, face, points);
		return QualityOfPoseNative.check(NATIVE_ID, image, face, points);
	}

	/**
	 * 
	 * @param
	 * @author Onion_Ye
	 * @time 2020年7月20日 下午5:57:49
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		QualityOfPoseNative.close(NATIVE_ID);
		this.isClose = true;
	}
	
}
