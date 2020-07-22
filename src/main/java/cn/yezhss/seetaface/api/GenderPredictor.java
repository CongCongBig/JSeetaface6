package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.GenderPredictorNative;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 性别估计器
 * @author Onion_Ye
 * @time 2020年6月22日 下午5:20:29
 */
public class GenderPredictor implements Closeable {

	private final long NATIVE_ID;
	
	private boolean isClose = false;

	/**
	 * 性别估计器
	 * @param cstaPath gender_predictor.csta的路径
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:58:44
	 */
	public GenderPredictor(String cstaPath) {
		this(new SeetaModelSetting(cstaPath));
	}

	/**
	 * 性别估计器
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:47:53
	 */
	public GenderPredictor(SeetaModelSetting seetaModelSetting) {
		NATIVE_ID = GenderPredictorNative.init(seetaModelSetting);
	}

	/**
	 * 裁剪人脸
	 * @param image 原始图像数据
	 * @param points 人脸特征点数
	 * @return 裁剪好的人脸数据
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:45:59
	 */
	public SeetaImageData cropFace(SeetaImageData image, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, points);
		return GenderPredictorNative.cropFace(NATIVE_ID, image, points);
	}

	/**
	 * 裁剪好的人脸数据
	 * @param face 裁剪好的人脸数据
	 * @param gender 估计的性别 参考GenderPredictor.GENDER
	 * @return true表示估计成功
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:42:46
	 */
	public Gender predictGender(SeetaImageData face) {
		SeetaAssert.validate(isClose, face);
		int result = GenderPredictorNative.predictGender(NATIVE_ID, face);
		return toGender(result);
	}

	/**
	 * 
	 * @param result
	 * @return
	 * @author Onion_Ye
	 * @time 2020年6月29日 下午2:59:57
	 */
	private Gender toGender(int result) {
		Gender gender = null;
		switch(result) {
		case -1: gender = Gender.UNKNOW; break;
		case 0: gender = Gender.MALE; break;
		case 1: gender = Gender.FEMALE; break;
		}
		return gender;
	}

	/**
	 * 输入原始图像数据和人脸特征点，返回估计的性别。
	 * @param image 原始人脸数据
	 * @param points 人脸特征点
	 * @param gender 估计的性别 参考GenderPredictor.GENDER
	 * @return true表示估计成功
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:35:48
	 */
	public Gender predictGenderWithCrop(SeetaImageData image, SeetaPointF[] points) {
		SeetaAssert.validate(isClose, image, points);
		int result = GenderPredictorNative.predictGenderWithCrop(NATIVE_ID, image, points);
		return toGender(result);
	}

	/**
	 * 设置相关属性值
	 * @param property 属性类型 参考GenderPredictor.Property
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:30:19
	 */
	public void set(Property property, double value) {
		SeetaAssert.validate(isClose, property);
		GenderPredictorNative.set(NATIVE_ID, property.getValue(), value);
	}

	/**
	 * 获取相关属性值
	 * @param property 属性类型 参考GenderPredictor.Property
	 * @return 属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:30:19
	 */
	public double get(Property property) {
		SeetaAssert.validate(isClose, property);
		return GenderPredictorNative.get(NATIVE_ID, property.getValue());
	}
	
	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月21日 上午9:48:20
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		GenderPredictorNative.close(NATIVE_ID);
		isClose = true;
	}

	/**
	 * 相关属性
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:54:11
	 */
	public enum Property {
		/**
		 * 计算线程数，默认为 4.
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

	/**
	 * 性别
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:54:19
	 */
	public enum Gender {
		/**
		 * 男性
		 */
		MALE, 
		/**
		 * 女性
		 */
		FEMALE,
		/**
		 * 未知
		 */
		UNKNOW
	}

}
