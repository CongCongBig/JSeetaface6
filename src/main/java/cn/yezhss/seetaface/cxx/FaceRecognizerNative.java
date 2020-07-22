package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;

/**
 * 人脸识别器 人脸识别器要求输入原始图像数据和人脸特征点（或者裁剪好的人脸数据），对输入的人脸提取特征值数组，根据提取的特征值数组对人脸进行相似度比较。
 * @author Onion_Ye
 * @time 2020年7月10日 下午2:44:00
 */
public class FaceRecognizerNative {

	/**
	 * 人脸识别器的构造函数
	 * @param setting 配置
	 * @return FaceRecognizer在c++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:45:35
	 */
	public static native long init(SeetaModelSetting setting);
	
	/**
	 * 裁剪人脸
	 * @param nativeId FaceRecognizer在c++的序列化
	 * @param image 原始图像数据
	 * @param points 人脸特征点数组
	 * @return 返回的裁剪人脸
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:54:32
	 */
	public static native SeetaImageData cropFace(long nativeId, SeetaImageData image, SeetaPointF[] points);
	
	/**
	 * 输入裁剪后的人脸图像，提取人脸的特征值数组
	 * @param nativeId FaceRecognizer在c++的序列化
	 * @param image 裁剪后的人脸图像数据
	 * @return 如果提取成功返回数组 如果提取失败返回null
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:57:32
	 */
	public static native float[] extractCroppedFace(long nativeId, SeetaImageData face);
	
	/**
	 * 输入原始图像数据和人脸特征点数组，提取人脸的特征值数组。
	 * @param nativeId FaceRecognizer在c++的序列化
	 * @param image 裁剪后的人脸图像数据
	 * @return 如果提取成功返回数组 如果提取失败返回null
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:57:32
	 */
	public static native float[] extract(long nativeId, SeetaImageData image, SeetaPointF[] points);
	
	/**
	 * 比较两人脸的特征值数据，获取人脸的相似度值。
	 * @param nativeId FaceRecognizer在c++的序列化
	 * @param features1 特征数组一
	 * @param features2 特征数组二
	 * @return 相似度值
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午3:02:23
	 */
	public static native float calculateSimilarity(long nativeId, float[] features1, float[] features2);
	
	/**
	 * 设置相关属性值
	 * @param nativeId FaceRecognizer在c++的序列化
	 * @param property 属性 参考 FaceRecognizer.Property.getValue()
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午3:04:17
	 */
	public static native void set(long nativeId, int property, double value);
	
	/**
	 * 获取相关属性值
	 * @param nativeId FaceRecognizer在c++的序列化
	 * @param property 属性 参考 FaceRecognizer.Property.getValue()
	 * @return 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午3:05:28
	 */
	public static native double get(long nativeId, int property);
	
	/**
	 * 释放资源
	 * @param nativeId FaceRecognizer在c++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月17日 下午4:51:27
	 */
	public static native void close(long nativeId);
	
}
