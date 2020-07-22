package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.MaskStatus;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaModelSetting;
import cn.yezhss.seetaface.po.SeetaRect;

/**
 * 口罩检测
 * @author Onion_Ye
 * @time 2020年6月24日 下午5:00:53
 */
public class MaskDetectorNative {

	/**
	 * 初始化一个MaskDetector
	 * @param setting 配置
	 * @return MaskDetector在c++持久化的序列号
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:02:27
	 */
	public static native long init(SeetaModelSetting setting);
	
	/**
	 * 输入图像数据、人脸位置，返回是否佩戴口罩的检测结果。
	 * @param nativeId MaskDetector在c++持久化的序列号
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param score 戴口罩的置信度
	 * @return false没戴口罩/true戴了口罩
	 * @author Onion_Ye
	 * @time 2020年6月24日 下午5:07:49
	 */
	public static native MaskStatus detect(long nativeId, SeetaImageData image, SeetaRect face);

	/**
	 * 释放资源
	 * @param nativeId MaskDetector在c++持久化的序列号
	 * @author Onion_Ye
	 * @time 2020年7月20日 上午9:57:26
	 */
	public static native void close(long nativeId);
	
}
