package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.QualityResult;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;

/**
 * 非深度学习的人脸姿态评估器
 * @author Onion_Ye
 * @time 2020年7月9日 上午10:23:41
 */
public class QualityOfPoseNative {

	/**
	 * 
	 * @return QualityOfPose在C++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:09:40
	 */
	public static native long init();
	
	/**
	 * 检测人脸姿态
	 * @param nativeId QualityOfPose在C++的序列化
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param points 人脸5个特征数组
	 * @return 人脸姿态检测结果
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:07:02
	 */
	public static native QualityResult check(long nativeId, SeetaImageData image, SeetaRect face, SeetaPointF[] points);

	/**
	 * 释放资源
	 * @param nativeId QualityOfPose在C++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月20日 下午6:04:10
	 */
	public static native void close(long nativeId);
	
}
