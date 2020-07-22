package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.PointWithMask;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaRect;

/**
 * 人脸特征点检测器
 * @author Onion_Ye
 * @time 2020年6月22日 下午1:26:16
 */
public class FaceLandmarkerNative {
	
	/**
	 * 人脸特征点检测器
	 * @param setting 检测器结构参数
	 * @return FaceLandmarker在c++的持久化Id
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:27:01
	 */
	public static native long init(SeetaModelSetting setting);
	
	/**
	 * 获取模型对应的特征点数组长度
	 * @param nativeId 持久化id
	 * @return 模型特征点数组长度
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:28:26
	 */
	public static native int number(long nativeId);
	
	/**
	 * 获取人脸特征点。
	 * @param nativeId 持久化id
	 * @param image 图像原始数据
	 * @param face 人脸位置
	 * @return 人脸特征点数组
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:31:11
	 */
	public static native PointWithMask[] mark(long nativeId, SeetaImageData image, SeetaRect face);
	
	/**
	 * 释放资源
	 * @param nativeId 持久化id
	 * @author Onion_Ye
	 * @time 2020年7月17日 下午4:51:27
	 */
	public static native void close(long nativeId);
	
}
