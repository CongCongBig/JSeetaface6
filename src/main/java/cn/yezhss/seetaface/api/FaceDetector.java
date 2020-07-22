package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.FaceDetectorNative;
import cn.yezhss.seetaface.po.SeetaFaceInfoArray;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 人脸检测器
 * @author Onion_Ye
 * @time 2020年6月22日 下午12:02:01
 */
public class FaceDetector implements Closeable {

	private Long NATIVE_ID;
	
	private boolean isClose = false;
	
	/**
	 * 人脸检测器
	 * @param cstaPath face_detector.csta模型路径 
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:22:29
	 */
	public FaceDetector(String cstaPath) {
		this(new SeetaModelSetting(cstaPath));
	}
	
	/**
	 * 人脸检测器
	 * @param setting 检测器结构参数
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:22:22
	 */
	public FaceDetector(SeetaModelSetting setting) {
		if (setting == null) {
			throw new NullPointerException("配置不能为空.");
		}
		NATIVE_ID = FaceDetectorNative.init(setting);
	}
	
	/**
	 * 输入彩色图像，检测其中的人脸。
	 * @param image 输入的图像数据
	 * @return 人脸信息数组
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:19:32
	 */
	public SeetaFaceInfoArray detect(SeetaImageData image) {
		SeetaAssert.validate(isClose, image);
		return FaceDetectorNative.detect(NATIVE_ID, image);
	}
	
	/**
	 * 设置人脸检测器相关属性值 property来自{@linkplain Property}
	 * @param property 属性类型 
	 * @param value 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:07:14
	 */
	public void set(Property property, double value) {
		SeetaAssert.validate(isClose, property);
		FaceDetectorNative.set(NATIVE_ID, property.getValue(), value);
	}
	
	/**
	 * 获取人脸检测器相关属性值 property来自{@linkplain Property}
	 * @param property 属性类型 
	 * @return 设置的属性值
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:10:52
	 */
	public double get(Property property) {
		SeetaAssert.validate(isClose, property);
		return FaceDetectorNative.get(NATIVE_ID, property.getValue());
	}
	
	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月21日 上午9:36:58
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		FaceDetectorNative.close(NATIVE_ID);
		isClose = true;
	}
	
	/**
	 * 可供修改的属性
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午12:51:43
	 */
	public enum Property {
		/**
		 * 表示人脸检测器可以检测到的最小人脸，该值越小，支持检测到的人脸尺寸越小，检测速度越慢，默认值为20
		 */
		PROPERTY_MIN_FACE_SIZE(0),
        /**
         * 表示人脸检测器过滤阈值，默认为 0.90
         */
        PROPERTY_THRESHOLD(1),
        /**
         * 表示支持输入的图像的最大宽度
         */
        PROPERTY_MAX_IMAGE_WIDTH(2),
        /**
         * 表示支持输入的图像的最大高度
         */
        PROPERTY_MAX_IMAGE_HEIGHT(3),
        /**
         * 表示人脸检测器计算线程数 默认为 4.
         */
        PROPERTY_NUMBER_THREADS(4),
        PROPERTY_ARM_CPU_MODE(0x101);
		
		private int num;
		
		Property(int num){
			this.num = num;
		}
		
		public int getValue() {
			return num;
		}
	}

}
