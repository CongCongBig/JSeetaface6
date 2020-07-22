package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.BlurInfo;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;

/**
 * 深度学习的人脸清晰度评估器
 * @author Onion_Ye
 * @time 2020年7月6日 下午6:28:18
 */
public class QualityOfLBNNative {
	
	/**
	 * 人脸清晰度评估器构造函数
	 * @param setting 对象构造结构体参数
	 * @return QualityOfLBN在C++的序列号
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午9:56:59
	 */
	public static native long init(SeetaModelSetting setting);
	
	/**
	 * 检测人脸清晰度。
	 * @param nativeId QualityOfLBN在C++的序列号
	 * @param image 原始图像数据
	 * @param points 人脸68个特征点数组
	 * @return 模糊信息
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午9:56:25
	 */
	public static native BlurInfo detect(long nativeId, SeetaImageData image, SeetaPointF[] points);

	/**
	 * 设置相关属性值
	 * @param nativeId QualityOfLBN在C++的序列号
	 * @param property 参考 QualityOfLBN.Property.getValue
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午9:58:03
	 */
	public static native void set(long nativeId, int property, double value);
	
	/**
	 * 获取相关属性值
	 * @param nativeId QualityOfLBN在C++的序列号
	 * @param property 参考 QualityOfLBN.Property.getValue
	 * @return 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午9:59:53
	 */
	public static native double get(long nativeId, int property);

	/**
	 * 释放资源
	 * @param nativeId QualityOfLBN在C++的序列号
	 * @author Onion_Ye
	 * @time 2020年7月21日 上午9:25:49
	 */
	public static native void close(long nativeId);
	
}
