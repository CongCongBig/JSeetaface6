package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.QualityResult;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;

/**
 * 非深度的人脸亮度评估器
 * @author Onion_Ye
 * @time 2020年7月3日 下午3:02:17
 */
public class QualityOfBrightnessNative {

	/**
	 * 默认值为{level0:70, 100, 210, 230}
	 * @return
	 * @author Onion_Ye
	 * @time 2020年7月3日 下午3:04:52
	 */
	public static native long init();
	
	/**
	 * 分类依据为[0, v0) & [v3, ~) => LOW;[v0, v1) & [v2, v3) => MEDIUM;[v1, v2) => HIGH;
	 * @param v0 分级参数一
	 * @param v1 分级参数二
	 * @param v2 分级参数三
	 * @param v3 分级参数四
	 * @return QualityOfBrightness在C++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月6日 上午11:40:03
	 */
	public static native long init(float v0, float v1, float v2, float v3);
	
	/**
	 * 检测人脸亮度
	 * @param nativeId QualityOfBrightness在C++的序列化
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param points 人脸特征点数组
	 * @return 人脸亮度检测结果
	 * @author Onion_Ye
	 * @time 2020年7月6日 上午11:41:53
	 */
	public static native QualityResult check(long nativeId, SeetaImageData image, SeetaRect face, SeetaPointF[] points);

	/**
	 * 释放资源
	 * @param nativeId QualityOfBrightness在C++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月20日 下午6:04:10
	 */
	public static native void close(long nativeId);
	
}
