package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.QualityResult;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;

/**
 * 深度学习的人脸姿态评估器。
 * @author Onion_Ye
 * @time 2020年7月9日 上午11:11:16
 */
public class QualityOfPoseExNative {

	/**
	 * 人脸姿态评估器构造函数。
	 * @param setting 构造评估器需要传入的结构体参数
	 * @return QualityOfPoseEx在c++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:15:03
	 */
	public static native long init(SeetaModelSetting setting);
	
	/**
	 * 检测人脸姿态
	 * @param nativeId QualityOfPoseEx在c++的序列化
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param points 人脸5个特征点数组
	 * @return 人脸姿态检测结果
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:13:12
	 */
	public static native QualityResult check(long nativeId, SeetaImageData image, SeetaRect face, SeetaPointF[] points);
	
	/**
	 * 设置相关属性
	 * @param nativeId QualityOfPoseEx在c++的序列化
	 * @param property 属性 参考 QualityOfPose.Property.getValue()
	 * @param value 值
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:14:28
	 */
	public static native void set(long nativeId, int property, double value);
	
	/**
	 * 获取相关属性值
	 * @param nativeId QualityOfPoseEx在c++的序列化
	 * @param property 属性 参考 QualityOfPose.Property.getValue()
	 * @return 值
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:16:27
	 */
	public static native double get(long nativeId, int property);

	/**
	 * 释放资源
	 * @param nativeId QualityOfPoseEx在c++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月20日 下午5:55:27
	 */
	public static native void close(long nativeId);
}
