package cn.yezhss.seetaface.api;


import cn.yezhss.seetaface.cxx.QualityOfPoseExNative;
import cn.yezhss.seetaface.po.QualityResult;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;

/**
 * 深度学习的人脸姿态评估器。
 * @author YeZhiCong
 * @time 2020年7月9日 上午11:11:16
 */
public class QualityOfPoseEx {

	private final long NATIVE_ID;

	/**
	 * 人脸姿态评估器构造函数。
	 * @param cstaPath pose_estimation.csta的路径
	 * @author YeZhiCong
	 * @time 2020年7月10日 上午11:49:47
	 */
	public QualityOfPoseEx(String cstaPath) {
		this(new SeetaModelSetting(cstaPath));
	}
	
	/**
	 * 人脸姿态评估器构造函数。
	 * @author YeZhiCong
	 * @time 2020年7月10日 上午11:48:57
	 */
	public QualityOfPoseEx(SeetaModelSetting setting) {
		NATIVE_ID = QualityOfPoseExNative.init(setting);
	}

	/**
	 * 检测人脸姿态
	 * @param nativeId QualityOfPoseEx在c++的序列化
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param points 人脸5个特征点数组
	 * @return 人脸姿态检测结果
	 * @author YeZhiCong
	 * @time 2020年7月9日 上午11:13:12
	 */
	public QualityResult check(SeetaImageData image, SeetaRect face, SeetaPointF[] points) {
		return QualityOfPoseExNative.check(NATIVE_ID, image, face, points);
	}
	
	/**
	 * 设置相关属性
	 * @param nativeId QualityOfPoseEx在c++的序列化
	 * @param property 属性 参考 QualityOfPose.Property.getValue()
	 * @param value 值
	 * @author YeZhiCong
	 * @time 2020年7月9日 上午11:14:28
	 */
	public void set(Property property, double value) {
		QualityOfPoseExNative.set(NATIVE_ID, property.getValue(), value);
	}
	
	/**
	 * 获取相关属性值
	 * @param nativeId QualityOfPoseEx在c++的序列化
	 * @param property 属性 参考 QualityOfPose.Property.getValue()
	 * @return 值
	 * @author YeZhiCong
	 * @time 2020年7月9日 上午11:16:27
	 */
	public double get(long nativeId, Property property) {
		return QualityOfPoseExNative.get(NATIVE_ID, property.getValue());
	}
	
	public enum Property {
        YAW_LOW_THRESHOLD(0),
        YAW_HIGH_THRESHOLD(1),
        PITCH_LOW_THRESHOLD(2),
        PITCH_HIGH_THRESHOLD(3),
        ROLL_LOW_THRESHOLD(4),
        ROLL_HIGH_THRESHOLD(5);
		
		private int num;
		
		Property(int num) {
			this.num = num;
		}
		
		public int getValue() {
			return num;
		}
	}
}
