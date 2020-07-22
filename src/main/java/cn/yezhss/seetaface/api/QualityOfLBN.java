package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.QualityOfLBNNative;
import cn.yezhss.seetaface.po.BlurInfo;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 深度学习的人脸清晰度评估器
 * @author Onion_Ye
 * @time 2020年7月6日 下午6:28:18
 */
public class QualityOfLBN implements Closeable {
	
	private final long NATIVE_ID;
	
	private boolean isClose = false;
	
	/**
	 * 深度学习的人脸清晰度评估器
	 * @param cstaPath quality_lbn.csta的路径
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:41:19
	 */
	public QualityOfLBN(String cstaPath) {
		this(new SeetaModelSetting(cstaPath));
	}
	
	/**
	 * 深度学习的人脸清晰度评估器
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午11:40:21
	 */
	public QualityOfLBN(SeetaModelSetting setting) {
		NATIVE_ID = QualityOfLBNNative.init(setting);
	}

	/**
	 * 检测人脸清晰度。
	 * @param image 原始图像数据
	 * @param points 人脸68个特征点数组
	 * @return 模糊信息
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午9:56:25
	 */
	public BlurInfo detect(SeetaImageData image, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, points);
		return QualityOfLBNNative.detect(NATIVE_ID, image, points);
	}

	/**
	 * 设置相关属性值
	 * @param nativeId QualityOfLBN在C++的序列号
	 * @param property 参考 QualityOfLBN.Property.getValue
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午9:58:03
	 */
	public void set(Property property, double value) {
		SeetaAssert.validate(isClose, property);
		QualityOfLBNNative.set(NATIVE_ID, property.getValue(), value);
	}
	
	/**
	 * 获取相关属性值
	 * @param nativeId QualityOfLBN在C++的序列号
	 * @param property 参考 QualityOfLBN.Property.getValue
	 * @return 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年7月9日 上午9:59:53
	 */
	public double get(Property property) {
		SeetaAssert.validate(isClose, property);
		return QualityOfLBNNative.get(NATIVE_ID, property.getValue());
	}
	
	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月20日 下午6:06:55
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		QualityOfLBNNative.close(NATIVE_ID);
		isClose = true;
	}
	
	public enum Property {
		/**
		 * 表示计算线程数，默认为 4
		 */
		PROPERTY_NUMBER_THREADS(4),
        /**
         * 针对于移动端，表示设置的 cpu 计算模式。0 表示大核计算模式，1 表示小核计算模式，2 表示平衡模式，为默认模式。
         */
        PROPERTY_ARM_CPU_MODE(5),
        PROPERTY_LIGHT_THRESH(10),
        /**
         * 表示人脸模糊阈值，默认值大小为 0.80
         */
        PROPERTY_BLUR_THRESH(11),
        PROPERTY_NOISE_THRESH(12);
        
        int value;
		
		Property(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}

}
