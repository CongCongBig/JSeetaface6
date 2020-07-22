package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.FaceLandmarkerNative;
import cn.yezhss.seetaface.po.PointWithMask;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 人脸特征点检测器
 * @author Onion_Ye
 * @time 2020年6月22日 下午2:13:54
 */
public class FaceLandmarker implements Closeable {

	private Long NATIVE_ID;
	
	private boolean isClose = false;
	
	/**
	 * 人脸特征点检测器
	 * @param cstaPath face_landmarker_pts5.csta或face_landmarker_pts68.csta的路径
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:17:37
	 */
	public FaceLandmarker(String cstaPath) {
		this(new SeetaModelSetting(cstaPath));
	}

	/**
	 * 人脸特征点检测器
	 * @param seetaModelSetting 检测器结构参数
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午5:18:33
	 */
	public FaceLandmarker(SeetaModelSetting seetaModelSetting) {
		if (seetaModelSetting == null) {
			throw new NullPointerException("");
		}
		this.NATIVE_ID = FaceLandmarkerNative.init(seetaModelSetting);
	}
	
	/**
	 * 获取模型对应的特征点数组长度
	 * @return 模型特征点数组长度
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:28:26
	 */
	public int number() {
		SeetaAssert.validate(isClose);
		return FaceLandmarkerNative.number(NATIVE_ID);
	}
	
	/**
	 * 获取人脸特征点。
	 * @param nativeId 持久化id
	 * @param image 图像原始数据
	 * @param face 人脸位置
	 * @return 人脸特征点数组
	 * @author Onion_Ye
	 * @time 2020年6月22日 下午1:31:11
	 */
	public PointWithMask[] mark(SeetaImageData image, SeetaRect face) {
		SeetaAssert.validate(isClose, image, face);
		return FaceLandmarkerNative.mark(NATIVE_ID, image, face);
	}

	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月17日 下午5:01:11
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		FaceLandmarkerNative.close(NATIVE_ID);
		isClose = true;
	}
	
}
