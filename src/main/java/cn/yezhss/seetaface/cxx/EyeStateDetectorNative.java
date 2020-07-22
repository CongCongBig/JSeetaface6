package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.EyeState;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;

/**
 * 眼睛状态检测器
 * @author Onion_Ye
 * @time 2020年6月22日 下午6:01:57
 */
public class EyeStateDetectorNative {

	/**
	 * 初始化一个EyeStateDetector
	 * @param setting 配置
	 * @return FaceDetector在c++持久化的序列号
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午12:56:59
	 */
	public static native long init(SeetaModelSetting setting);
	
	/**
	 * 输入原始图像数据和人脸特征点，返回左眼和右眼的状态。
	 * @param nativeId 持久化ID
	 * @param image 原始图像数据
	 * @param points 人脸特征点数组
	 * @return 眼睛状态
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午6:13:19
	 */
	public static native EyeState detect(long nativeId, SeetaImageData image, SeetaPointF[] points);
	
	/**
	 * 设置相关属性值
	 * @param nativeId 持久化ID
	 * @param property 属性类型 参考EyeStateDetector.Property.ordinal()
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:30:19
	 */
	public static native void set(long nativeId, int property, double value);
	
	/**
	 * 获取相关属性值
	 * @param nativeId 持久化ID
	 * @param property 属性类型 参考EyeStateDetector.Property.ordinal()
	 * @return 属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:30:19
	 */
	public static native double get(long nativeId, int property);

	/**
	 * 释放资源
	 * @param nativeId 持久化ID
	 * @author Onion_Ye
	 * @time 2020年7月17日 下午5:50:57
	 */
	public static native void close(long nativeId);
	
}
