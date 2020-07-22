package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.api.FaceDetector;
import cn.yezhss.seetaface.po.SeetaFaceInfoArray;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;

/**
 * 
 * @author Onion_Ye
 * @time 2020年6月22日 下午12:54:57
 */
public class FaceDetectorNative {

	/**
	 * 初始化一个FaceDetector
	 * @param setting 配置
	 * @return FaceDetector在c++持久化的序列号
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午12:56:59
	 */
	public static native long init(SeetaModelSetting setting);
	
	/**
	 * 输入彩色图像，检测其中的人脸。
	 * @param nativeId init接口获取的持久化序列号
	 * @param image 输入的图像数据
	 * @return 人脸信息数组
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:04:22
	 */
	public static native SeetaFaceInfoArray detect(long nativeId, SeetaImageData image);
	
	/**
	 * 设置人脸检测器相关属性值 property来自{@linkplain FaceDetector.Property}
	 * @param nativeId init接口获取的持久化序列号
	 * @param property 属性类型 
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:07:14
	 */
	public static native void set(long nativeId, int property, double value);
	
	/**
	 * 获取人脸检测器相关属性值 property来自{@linkplain FaceDetector.Property}
	 * @param nativeId init接口获取的持久化序列号
	 * @param property 属性类型 
	 * @return 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:10:52
	 */
	public static native double get(long nativeId, int property);
	
	/**
	 * 释放资源
	 * @param nativeId FaceDetector在c++持久化的序列号
	 * @author Onion_Ye
	 * @time 2020年7月17日 下午4:51:27
	 */
	public static native void close(long nativeId);
	
}
