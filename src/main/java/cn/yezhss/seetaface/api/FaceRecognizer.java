package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.FaceRecognizerNative;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 人脸识别器 人脸识别器要求输入原始图像数据和人脸特征点（或者裁剪好的人脸数据），对输入的人脸提取特征值数组，根据提取的特征值数组对人脸进行相似度比较。
 * @author Onion_Ye
 * @time 2020年7月10日 下午2:44:00
 */
public class FaceRecognizer implements Closeable {
	
	private final long NATIVE_ID;
	
	private boolean isClose = false;

	/**
	 * 人脸识别器的构造函数
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午3:07:53
	 */
	public FaceRecognizer(String cstaPath) {
		this(new SeetaModelSetting(cstaPath));
	}

	/**
	 * 人脸识别器的构造函数
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午3:07:11
	 */
	public FaceRecognizer(SeetaModelSetting setting) {
		NATIVE_ID = FaceRecognizerNative.init(setting);
	}

	/**
	 * 裁剪人脸
	 * @param image 原始图像数据
	 * @param points 人脸特征点数组
	 * @return 返回的裁剪人脸
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:54:32
	 */
	public SeetaImageData cropFace(SeetaImageData image, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, points);
		return FaceRecognizerNative.cropFace(NATIVE_ID, image, points);
	}
	
	/**
	 * 输入裁剪后的人脸图像，提取人脸的特征值数组
	 * @param image 裁剪后的人脸图像数据
	 * @return 如果提取成功返回数组 如果提取失败返回null
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:57:32
	 */
	public float[] extractCroppedFace(SeetaImageData face) {
		SeetaAssert.validate(isClose, face);
		return FaceRecognizerNative.extractCroppedFace(NATIVE_ID, face);
	}
	
	/**
	 * 输入原始图像数据和人脸特征点数组，提取人脸的特征值数组。
	 * @param image 裁剪后的人脸图像数据
	 * @return 如果提取成功返回数组 如果提取失败返回null
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:57:32
	 */
	public float[] extract(SeetaImageData image, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, points);
		return FaceRecognizerNative.extract(NATIVE_ID, image, points);
	}
	
	/**
	 * 比较两人脸的特征值数据，获取人脸的相似度值。
	 * @param features1 特征数组一
	 * @param features2 特征数组二
	 * @return 相似度值
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午3:02:23
	 */
	public float calculateSimilarity(float[] features1, float[] features2) {
		SeetaAssert.validate(isClose, features1, features2);
		return FaceRecognizerNative.calculateSimilarity(NATIVE_ID, features1, features2);
	}
	
	/**
	 * 设置相关属性值
	 * @param property 属性 参考 FaceRecognizer.Property.getValue()
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午3:04:17
	 */
	public void set(Property property, double value) {
		SeetaAssert.validate(isClose, property);
		FaceRecognizerNative.set(NATIVE_ID, property.getValue(), value);
	}
	
	/**
	 * 获取相关属性值
	 * @param nativeId FaceRecognizer在c++的序列化
	 * @param property 属性 参考 FaceRecognizer.Property.getValue()
	 * @return 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午3:05:28
	 */
	public double get(Property property) {
		SeetaAssert.validate(isClose, property);
		return FaceRecognizerNative.get(NATIVE_ID, property.getValue());
	}
	
	/**
	 * 释放C++资源
	 * @author Onion_Ye
	 * @time 2020年7月17日 下午5:00:13
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		FaceRecognizerNative.close(NATIVE_ID);
		isClose = true;
	}
	
	public enum Property {
        PROPERTY_NUMBER_THREADS(4),
        PROPERTY_ARM_CPU_MODE(5);
		
		private int num;
		
		Property(int num) {
			this.num = num;
		}
		
		public int getValue() {
			return num;
		}
	}
}
