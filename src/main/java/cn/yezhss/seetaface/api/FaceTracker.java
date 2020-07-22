package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.FaceTrackerNative;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaTrackingFaceInfoArray;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 人脸跟踪器,人脸跟踪器会对输入的彩色图像或者灰度图像中的人脸进行跟踪，并返回所有跟踪到的人脸信息。
 * @author Onion_Ye
 * @time 2020年7月10日 下午2:20:06
 */
public class FaceTracker implements Closeable {
	
	private final long NATIVE_ID;
	
	private boolean isClose = false;

	/**
	 * 人脸跟踪器的构造器
	 * @param cstaPath face_recognizer.csta路径
	 * @param videoWidth 视频的宽度
	 * @param videoHeight 视频的高度
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午3:20:34
	 */
	public FaceTracker(String cstaPath, int videoWidth, int videoHeight) {
		this(new SeetaModelSetting(cstaPath), videoWidth, videoHeight);
	}

	/**
	 * 人脸跟踪器的构造器
	 * @param setting 跟踪器结构参数
	 * @param videoWidth 视频的宽度
	 * @param videoHeight 视频的高度
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午3:17:40
	 */
	public FaceTracker(SeetaModelSetting setting, int videoWidth, int videoHeight) {
		NATIVE_ID = FaceTrackerNative.init(setting, videoWidth, videoHeight);
	}

	/**
	 * 设置底层的计算线程数量
	 * @param num 线程数量
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:27:11
	 */
	public void setSingleCalculationThreads(int num) {
		SeetaAssert.validate(isClose);
		FaceTrackerNative.setSingleCalculationThreads(NATIVE_ID, num);
	}
	
	/**
	 * 对视频帧中的人脸进行跟踪
	 * @param image 原始图像数据
	 * @param frameNo 视频帧索引
	 * @return 跟踪到的人脸信息数组
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:32:20
	 */
	public SeetaTrackingFaceInfoArray track(SeetaImageData image, int frameNo) {
		SeetaAssert.validate(isClose, image);
		return FaceTrackerNative.track(NATIVE_ID, image, frameNo);
	}
	
	/**
	 * 设置检测器的最小人脸大小
	 * @param size 最小人脸大小 size 的大小保证大于等于 20，size的值越小，能够检测到的人脸的尺寸越小，检测速度越慢。
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:34:10
	 */
	public void setMinFaceSize(int size) {
		SeetaAssert.validate(isClose);
		FaceTrackerNative.setMinFaceSize(NATIVE_ID, size);
	}
	
	/**
	 * 获取检测器的最小人脸大小 与{@link #setMinFaceSize(long, int)}对应
	 * @return 获取检测器的最小人脸大小
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:35:43
	 */
	public int getMinFaceSize() {
		SeetaAssert.validate(isClose);
		return FaceTrackerNative.getMinFaceSize(NATIVE_ID);
	}
	
	/**
	 * 设置检测器的检测阈值
	 * @param thresh 检测阈值
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:37:57
	 */
	public void setThreshold(float thresh) {
		SeetaAssert.validate(isClose);
		FaceTrackerNative.setThreshold(NATIVE_ID, thresh);
	}
	
	/**
	 * 获取检测器的检测阈值 与{@link #setThreshold(long, float)}对应
	 * @return 获取检测器的检测阈值
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:39:29
	 */
	public float getThreshold() {
		SeetaAssert.validate(isClose);
		return FaceTrackerNative.getThreshold(NATIVE_ID);
	}
	
	/**
	 * 设置以稳定模式输出人脸跟踪结果。 只有在视频中连续跟踪时，才使用此方法。
	 * @param stable 是否是稳定模式
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:41:02
	 */
	public void setVideoStable(boolean stable) {
		SeetaAssert.validate(isClose);
		FaceTrackerNative.setVideoStable(NATIVE_ID, stable);
	}
	
	/**
	 * 获取当前是否是稳定工作模式 与{@link #setVideoStable(long, boolean)}对应
	 * @return 是否是稳定模式
	 * @author Onion_Ye
	 * @time 2020年7月10日 下午2:42:00
	 */
	public boolean getVideoStable() {
		SeetaAssert.validate(isClose);
		return FaceTrackerNative.getVideoStable(NATIVE_ID);
	}

	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月21日 上午9:36:58
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		FaceTrackerNative.close(NATIVE_ID);
		isClose = true;
	}
	
}
