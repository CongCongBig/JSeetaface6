package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;

/**
 * 性别估计器
 * @author Onion_Ye
 * @time 2020年6月22日 下午5:20:35
 */
public class GenderPredictorNative {

	/**
	 * 初始化一个GenderPredictor
	 * @param setting 配置
	 * @return GenderPredictor在c++持久化的序列号
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:25:23
	 */
	public static native long init(SeetaModelSetting setting);
	
	/**
	 * 裁剪人脸
	 * @param nativeId 持久化ID
	 * @param image 原始图像数据
	 * @param points 人脸特征点数
	 * @return 裁剪好的人脸数据
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:45:59
	 */
	public static native SeetaImageData cropFace(long nativeId, SeetaImageData image, SeetaPointF[] points);
	
	/**
	 * 裁剪好的人脸数据
	 * @param nativeId 持久化ID
	 * @param face 裁剪好的人脸数据
	 * @param gender 估计的性别 参考GenderPredictor.GENDER.ordinal()
	 * @return true表示估计成功
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:42:46
	 */
	public static native int predictGender(long nativeId, SeetaImageData face);
	
	/**
	 * 输入原始图像数据和人脸特征点，返回估计的性别。
	 * @param nativeId 持久化ID
	 * @param image 原始人脸数据
	 * @param points 人脸特征点
	 * @param gender 估计的性别 参考GenderPredictor.GENDER.ordinal()
	 * @return true表示估计成功
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:35:48
	 */
	public static native int predictGenderWithCrop(long nativeId, SeetaImageData image, SeetaPointF[] points);
	
	/**
	 * 设置相关属性值
	 * @param nativeId 持久化ID
	 * @param property 属性类型 参考GenderPredictor.Property.ordinal()
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:30:19
	 */
	public static native void set(long nativeId, int property, double value);
	
	/**
	 * 获取相关属性值
	 * @param nativeId 持久化ID
	 * @param property 属性类型 参考GenderPredictor.Property.ordinal()
	 * @return 属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:30:19
	 */
	public static native double get(long nativeId, int property);

	/**
	 * 释放资源
	 * @param nativeId
	 * @author Onion_Ye
	 * @time 2020年7月21日 上午9:44:29
	 */
	public static native void close(long nativeId);
	
}
