package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;

/**
 * 
 * @author Onion_Ye
 * @time 2020年6月23日 上午9:51:36
 */
public class AgePredictorNative {

	/**
	 * 初始化一个AgePredictor
	 * @param setting 配置
	 * @return AgePredictor在c++持久化的序列号
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:23:09
	 */
	public static native long init(SeetaModelSetting setting);
	
	/**
	 * 裁剪人脸
	 * @param nativeId AgePredictor在c++持久化的序列号
	 * @param image 原始图像数据
	 * @param points 人脸特征点数组
	 * @param face 返回的裁剪人脸
	 * @return 裁剪成功的人脸
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:26:51
	 */
	public static native SeetaImageData cropFace(long nativeId, SeetaImageData image, SeetaPointF[] points);
	
	/**
	 * 输入裁剪好的人脸，返回估计的年龄。
	 * @param nativeId AgePredictor在c++持久化的序列号
	 * @param image 裁剪好的人脸数据
	 * @return 估计的年龄 估计失败时返回-1
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:30:12
	 */
	public static native int predictAge(long nativeId, SeetaImageData image);
	
	/**
	 * 输入原始图像数据和人脸特征点，返回估计的年龄。
	 * @param nativeId AgePredictor在c++持久化的序列号
	 * @param image 原始人脸数据
	 * @param points 人脸特征点
	 * @return 估计的年龄 估计失败时返回-1
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:33:38
	 */
	public static native int predictAgeWithCrop(long nativeId, SeetaImageData image, SeetaPointF[] points);
	
	/**
	 * 设置相关属性值
	 * @param nativeId AgePredictor在c++持久化的序列号
	 * @param property 属性类别 参考AgePredictor.Property.ordinal()
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:34:53
	 */
	public static native void set(long nativeId, int property, double value);
	
	/**
	 * 获取相关属性值
	 * @param nativeId AgePredictor在c++持久化的序列号
	 * @param property 属性类别
	 * @return 对应的属性值
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:36:15
	 */
	public static native double get(long nativeId, int property);
	
	/**
	 * 释放资源
	 * @param nativeId AgePredictor在c++持久化的序列号
	 * @author Onion_Ye
	 * @time 2020年7月17日 下午5:43:15
	 */
	public static native void close(long nativeId);
}
