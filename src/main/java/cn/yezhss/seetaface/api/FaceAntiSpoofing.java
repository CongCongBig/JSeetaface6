package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.FaceAntiSpoofingNative;
import cn.yezhss.seetaface.po.PreFrameScore;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 活体识别
 * @author Onion_Ye
 * @time 2020年7月3日 上午10:15:13
 */
public class FaceAntiSpoofing implements Closeable {

	private final long NATIVE_ID;
	
	private boolean isClose = false;
	
	/**
	 * 初始化一个活体识别器
	 * @param cstaPath 局部检测模型(必传) fas_first.csta
	 * @param cstaPathTwo 全局检测模型(选传) fas_second.csta
	 * @author Onion_Ye
	 * @time 2020年7月3日 上午10:17:45
	 */
	public FaceAntiSpoofing(String cstaPath, String... cstaPathTwo) {
		this(new SeetaModelSetting(cstaPath), cstaPathTwo);
	}

	/**
	 * 初始化一个活体识别器
	 * @author Onion_Ye
	 * @time 2020年7月3日 上午10:16:32
	 */
	public FaceAntiSpoofing(SeetaModelSetting seetaModelSetting, String... cstaPathTwo) {
		if (seetaModelSetting == null) {
			throw new NullPointerException("配置不能为空.");
		}
		String appendCstaPath = cstaPathTwo == null || cstaPathTwo.length == 0 ? null : cstaPathTwo[0];
		NATIVE_ID = FaceAntiSpoofingNative.init(seetaModelSetting, appendCstaPath);
	}
	
	/**
	 * 检测活体
	 * @param image 输入图像，需要 RGB 彩色通道
	 * @param face 要识别的人脸位置
	 * @param points 要识别的人脸特征点
	 * @return 人脸状态 参考AgePredictor.Status.ordinal()
	 * <pre>
	 * 此函数不支持多线程调用，在多线程环境下需要建立对应的 FaceAntiSpoofing 的对象分别调用检测函数
	 * 当前版本可能返回 REAL, SPOOF, FUZZY
	 * </pre>
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:59:45
	 */
	public Status predict(SeetaImageData image, SeetaRect face, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, face, points);
		return Status.values()[FaceAntiSpoofingNative.predict(NATIVE_ID, image, face, points)];
	}
	
	/**
	 * 检测活体（Video模式）
	 * @param image 输入图像，需要 RGB 彩色通道
	 * @param face 要识别的人脸位置
	 * @param points 要识别的人脸特征点
	 * @return 人脸状态 参考AgePredictor.Status.ordinal()
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:03:12
	 */
	public Status predictVideo(SeetaImageData image, SeetaRect face, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, face, points);
		return Status.values()[FaceAntiSpoofingNative.predictVideo(NATIVE_ID, image, face, points)];
	}
	
	/**
	 * 重置 Video,开始下一次 predictVideo识别
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:04:55
	 */
	public void resetVideo() {
		SeetaAssert.validate(isClose);
		FaceAntiSpoofingNative.resetVideo(NATIVE_ID);
	}
	
	/**
	 * 获取活体检测内部分数
	 * @return 输出人脸质量分数和真实度,获取的是上一次调用predict或predictVideo接口后内部的阈值
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:08:27
	 */
	public PreFrameScore getPreFrameScore() {
		SeetaAssert.validate(isClose);
		return FaceAntiSpoofingNative.getPreFrameScore(NATIVE_ID);
	}
	
	/**
	 * 设置 Video模式中,识别视频帧数,当输入帧数为该值以后才会有返回值
	 * @param number 视频帧数
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:09:47
	 */
	public void setVideoFrameCount(int number) {
		SeetaAssert.validate(isClose);
		FaceAntiSpoofingNative.setVideoFrameCount(NATIVE_ID, number);
	}
	
	/**
	 * 与{@link #setVideoFrameCount(long,int)}相对应
	 * @return 获取视频帧数设置
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:10:27
	 */
	public int getVideoFrameCount() {
		SeetaAssert.validate(isClose);
		return FaceAntiSpoofingNative.getVideoFrameCount(NATIVE_ID);
	}
	
	/**
	 * 设置阈值
	 * @param clarity 清晰度阈值 越高要求输入的图像质量越高 默认0.3
	 * @param reality 活体阈值 越高对识别要求越严格 0.8
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:17:42
	 */
	public void setThreshold(float clarity, float reality) {
		SeetaAssert.validate(isClose);
		FaceAntiSpoofingNative.setThreshold(NATIVE_ID, clarity, reality);
	}
	
	/**
	 * 设置全局阈值
	 * @param boxThresh 全局检测阈值 默认阈值为 0.8
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:19:28
	 */
	public void setBoxThresh(float boxThresh) {
		SeetaAssert.validate(isClose);
		FaceAntiSpoofingNative.setBoxThresh(NATIVE_ID, boxThresh);
	}
	
	/**
	 * 与{@link #setBoxThresh(float)}相对应
	 * @return 获取全局域值
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:20:03
	 */
	public float getBoxThresh() {
		SeetaAssert.validate(isClose);
		return FaceAntiSpoofingNative.getBoxThresh(NATIVE_ID);
	}
	
	/**
	 * 与{@link #setThreshold(long,float,float)}相对应
	 * @return 获取域值
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:22:42
	 */
	public PreFrameScore getThreshold() {
		SeetaAssert.validate(isClose);
		return FaceAntiSpoofingNative.getThreshold(NATIVE_ID);
	}
	
	/**
	 * 设置相关属性值
	 * @param property FaceAntiSpoofing.Property.ordinal()
	 * @param value 设置的值
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:25:15
	 */
	public void set(Property property, double value) {
		SeetaAssert.validate(isClose, property);
		FaceAntiSpoofingNative.set(NATIVE_ID, property.getValue(), value);
	}
	
	/**
	 * 与{@link #set(long, int, double)}相对应
	 * @param nativeId FaceAntiSpoofing在c++持久化的序列号
	 * @param property 参考FaceAntiSpoofing.Property.ordinal()
	 * @return 设置的值
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午6:26:06
	 */
	public double get(Property property) {
		SeetaAssert.validate(isClose, property);
		return FaceAntiSpoofingNative.get(NATIVE_ID, property.getValue());
	}

	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月17日 下午6:19:29
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		FaceAntiSpoofingNative.close(NATIVE_ID);
		isClose = true;
	}
	
	public enum Property {
		PROPERTY_NUMBER_THREADS(4),
		PROPERTY_ARM_CPU_MODE(5);
		
		private int num;
		
		private Property(int num) {
			this.num = num;
		}
		
		public int getValue() {
			return num;
		}
	}
	
	public enum Status {
		/**
		 * 真实人脸
		 */
		REAL,
		/**
		 * 攻击人脸（假人脸）
		 */
		SPOOF,
		/**
		 * 无法判断（人脸成像质量不好）
		 */
		FUZZY,
		/**
		 * 正在检测
		 */
		DETECTING
	}

}
