package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaTrackingFaceInfoArray;

/**
 * 人脸跟踪器,人脸跟踪器会对输入的彩色图像或者灰度图像中的人脸进行跟踪，并返回所有跟踪到的人脸信息。
 * @author Onion_Ye
 * @time 2020年7月10日 下午2:20:06
 */
public class FaceTrackerNative {

	/**
	 * 人脸跟踪器的构造器
	 * @param setting 跟踪器结构参数
	 * @param videoWidth 视频的宽度
	 * @param videoHeight 视频的高度
	 * @return FaceTracker在C++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:23:32
	 */
	public static native long init(SeetaModelSetting setting, int videoWidth, int videoHeight);
	
	/**
	 * 设置底层的计算线程数量
	 * @param nativeId FaceTracker在C++的序列化
	 * @param num 线程数量
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:27:11
	 */
	public static native void setSingleCalculationThreads(long nativeId, int num);
	
	/**
	 * 对视频帧中的人脸进行跟踪
	 * @param nativeId FaceTracker在C++的序列化
	 * @param image 原始图像数据
	 * @param frameNo 视频帧索引
	 * @return 跟踪到的人脸信息数组
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:32:20
	 */
	public static native SeetaTrackingFaceInfoArray track(long nativeId, SeetaImageData image, int frameNo);
	
	/**
	 * 设置检测器的最小人脸大小
	 * @param nativeId FaceTracker在C++的序列化
	 * @param size 最小人脸大小 size 的大小保证大于等于 20，size的值越小，能够检测到的人脸的尺寸越小，检测速度越慢。
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:34:10
	 */
	public static native void setMinFaceSize(long nativeId, int size);
	
	/**
	 * 获取检测器的最小人脸大小 与{@link #setMinFaceSize(long, int)}对应
	 * @param nativeId FaceTracker在C++的序列化
	 * @return 获取检测器的最小人脸大小
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:35:43
	 */
	public static native int getMinFaceSize(long nativeId);
	
	/**
	 * 设置检测器的检测阈值
	 * @param nativeId FaceTracker在C++的序列化
	 * @param thresh 检测阈值
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:37:57
	 */
	public static native void setThreshold(long nativeId, float thresh);
	
	/**
	 * 获取检测器的检测阈值 与{@link #setThreshold(long, float)}对应
	 * @param nativeId FaceTracker在C++的序列化
	 * @return 获取检测器的检测阈值
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:39:29
	 */
	public static native float getThreshold(long nativeId);
	
	/**
	 * 设置以稳定模式输出人脸跟踪结果。 只有在视频中连续跟踪时，才使用此方法。
	 * @param nativeId FaceTracker在C++的序列化
	 * @param stable 是否是稳定模式
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:41:02
	 */
	public static native void setVideoStable(long nativeId, boolean stable);
	
	/**
	 * 获取当前是否是稳定工作模式 与{@link #setVideoStable(long, boolean)}对应
	 * @param nativeId FaceTracker在C++的序列化
	 * @return 是否是稳定模式
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:42:00
	 */
	public static native boolean getVideoStable(long nativeId);

	/**
	 * 释放资源
	 * @param nativeId MaskDetector在c++持久化的序列号
	 * @author Onion_Ye
	 * @time 2020年7月20日 上午9:57:26
	 */
	public static native void close(long nativeId);
	
}
