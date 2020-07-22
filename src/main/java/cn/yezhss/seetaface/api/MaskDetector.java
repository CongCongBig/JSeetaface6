package cn.yezhss.seetaface.api;

import java.io.Closeable;

import cn.yezhss.seetaface.cxx.MaskDetectorNative;
import cn.yezhss.seetaface.po.MaskStatus;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaRect;
import cn.yezhss.seetaface.util.SeetaAssert;

/**
 * 口罩检测器
 * @author Onion_Ye
 * @time 2020年6月24日 下午5:11:58
 */
public class MaskDetector implements Closeable {
	
	private final long NATIVE_ID;
	
	private boolean isClose = false;

	/**
	 * 口罩检测器
	 * @param cstaPath mask_detector.csta的路径
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:14:39
	 */
	public MaskDetector(String cstaPath) {
		this(new SeetaModelSetting(cstaPath));
	}

	/**
	 * 口罩检测器
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:13:27
	 */
	public MaskDetector(SeetaModelSetting seetaModelSetting) {
		NATIVE_ID = MaskDetectorNative.init(seetaModelSetting);
	}
	
	/**
	 * 输入图像数据、人脸位置，返回是否佩戴口罩的检测结果。
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param score 戴口罩的置信度
	 * @return false没戴口罩/true戴了口罩
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:07:49
	 */
	public MaskStatus detect(SeetaImageData image, SeetaRect face) {
		SeetaAssert.validate(isClose, image, face);
		return MaskDetectorNative.detect(NATIVE_ID, image, face);
	}
	
	/**
	 * 释放资源
	 * @author Onion_Ye
	 * @time 2020年7月21日 上午9:36:58
	 */
	public void close() {
		SeetaAssert.validate(isClose);
		MaskDetectorNative.close(NATIVE_ID);
		isClose = true;
	}
	
}
