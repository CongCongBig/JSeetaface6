package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.AgePredictorNative;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 年龄估计器
 * @author Onion_Ye
 * @time 2020年6月23日 上午9:51:36
 */
public class AgePredictor implements Closeable {
	
	private final long NATIVE_ID;
	
	private boolean isClose = false;
	
	/**
	 * 年龄估计器
	 * @param cstaPath age_predictor.csta的路径
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:43:14
	 */
	public AgePredictor(String cstaPath) {
		this(new SeetaModelSetting(cstaPath));
	}

	/**
	 * 年龄估计器
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:43:03
	 */
	public AgePredictor(SeetaModelSetting seetaModelSetting) {
		if (seetaModelSetting == null) {
			throw new NullPointerException("配置不能为空.");
		}
		NATIVE_ID = AgePredictorNative.init(seetaModelSetting);
	}
	
	/**
	 * 裁剪人脸
	 * @param nativeId AgePredictor在c++持久化的序列号
	 * @param image 原始图像数据
	 * @param points 人脸特征点数组
	 * @param face 返回的裁剪人脸
	 * @return 裁剪成功的人脸
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:26:51
	 */
	public SeetaImageData cropFace(SeetaImageData image, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, points);
		return AgePredictorNative.cropFace(NATIVE_ID, image, points);
	}
	
	/**
	 * 输入裁剪好的人脸，返回估计的年龄。
	 * @param image 裁剪好的人脸数据
	 * @return 估计的年龄 估计失败时返回-1
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:30:12
	 */
	public int predictAge(SeetaImageData image) {
		SeetaAssert.validate(isClose, image);
		return AgePredictorNative.predictAge(NATIVE_ID, image);
	}
	
	/**
	 * 输入原始图像数据和人脸特征点，返回估计的年龄。
	 * @param image 原始人脸数据
	 * @param points 人脸特征点
	 * @return 估计的年龄 估计失败时返回-1
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:33:38
	 */
	public int predictAgeWithCrop(SeetaImageData image, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, points);
		return AgePredictorNative.predictAgeWithCrop(NATIVE_ID, image, points);
	}
	
	/**
	 * 设置相关属性值
	 * @param property 属性类别 参考AgePredictor.Property.ordinal()
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:34:53
	 */
	public void set(Property property, double value) {
		SeetaAssert.validate(isClose, property);
		AgePredictorNative.set(NATIVE_ID, property.getValue(), value);
	}
	
	/**
	 * 获取相关属性值
	 * @param property 属性类别
	 * @return 对应的属性值
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:36:15
	 */
	public double get(Property property) {
		SeetaAssert.validate(isClose, property);
		return AgePredictorNative.get(NATIVE_ID, property.getValue());
	}

	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月17日 下午5:44:31
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		AgePredictorNative.close(NATIVE_ID);
		isClose = true;
	}
	
	public enum Property {
		/**
		 * 表示计算线程数，默认为 4.
		 */
		PROPERTY_NUMBER_THREADS(4),
        PROPERTY_ARM_CPU_MODE(5);
        
		int num;
		
        Property(int num) {
			this.num = num;
		}
        
        public int getValue() {
        	return num;
        }
        
	}
	
}
