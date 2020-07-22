package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.EyeStateDetectorNative;
import cn.yezhss.seetaface.po.EyeState;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 眼睛状态检测器
 * @author Onion_Ye
 * @time 2020年6月22日 下午6:24:23
 */
public class EyeStateDetector implements Closeable {

	private final long NATIVE_ID;
	
	private boolean isClose = false;
	
	/**
	 * 眼睛状态检测器
	 * @param cstaPath eye_state.csta的路径
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午6:25:27
	 */
	public EyeStateDetector(String cstaPath) {
		this(new SeetaModelSetting(cstaPath));
	}

	/**
	 * 眼睛状态检测器
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午6:25:02
	 */
	public EyeStateDetector(SeetaModelSetting seetaModelSetting) {
		if (seetaModelSetting == null) {
			throw new NullPointerException("配置不能为空.");
		}
		NATIVE_ID = EyeStateDetectorNative.init(seetaModelSetting);
	}
	
	/**
	 * 输入原始图像数据和人脸特征点，返回左眼和右眼的状态。
	 * @param image 原始图像数据
	 * @param points 人脸特征点数组
	 * @return 眼睛状态
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午6:13:19
	 */
	public EyeState detect(SeetaImageData image, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, points);
		return EyeStateDetectorNative.detect(NATIVE_ID, image, points);
	}
	
	/**
	 * 设置相关属性值
	 * @param property 属性类型 参考EyeStateDetector.Property
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:30:19
	 */
	public void set(Property property, double value) {
		SeetaAssert.validate(isClose, property);
		EyeStateDetectorNative.set(NATIVE_ID, property.getValue(), value);
	}
	
	/**
	 * 获取相关属性值
	 * @param property 属性类型 参考EyeStateDetector.Property
	 * @return 属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:30:19
	 */
	public double get(Property property) {
		SeetaAssert.validate(isClose, property);
		return EyeStateDetectorNative.get(NATIVE_ID, property.getValue());
	}
	
	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月17日 下午5:52:00
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		EyeStateDetectorNative.close(NATIVE_ID);
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
