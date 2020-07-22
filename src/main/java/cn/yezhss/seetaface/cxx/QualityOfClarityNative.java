package cn.yezhss.seetaface.cxx;

import cn.yezhss.seetaface.po.QualityResult;
import cn.yezhss.seetaface.po.SeetaImageData;
import cn.yezhss.seetaface.po.SeetaPointF;
import cn.yezhss.seetaface.po.SeetaRect;

/**
 * 非深度学习的人脸清晰度评估器
 * @author Onion_Ye
 * @time 2020年7月6日 上午11:53:09
 */
public class QualityOfClarityNative {

	/**
	 * 默认值为low=0.1 high=0.2 {@link this#init(float, float)}
	 * @return QualityOfClarity在C++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月6日 下午2:47:51
	 */
	public static native long init();
	
	/**
	 * <pre>
	 * [0, low)=> LOW 0
	 * [low, high)=> MEDIUM 1
	 * [high, ~)=> HIGH 2
	 *</pre>
	 * @param low 分级参数一
	 * @param high 分级参数二
	 * @return QualityOfClarity在C++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月6日 下午2:50:59
	 */
	public static native long init(float low, float high);
	
	/**
	 * 检测人脸清晰度
	 * @param nativeId QualityOfClarity在C++的序列化
	 * @param image 原始图像数据
	 * @param face 人脸位置
	 * @param points 人脸特征点数组
	 * @return 人脸清晰度检测结果
	 * @author Onion_Ye
	 * @time 2020年7月6日 下午6:26:19
	 */
	public static native QualityResult check(long nativeId, SeetaImageData image, SeetaRect face, SeetaPointF[] points);

	/**
	 * 释放资源
	 * @param nativeId QualityOfClarity在C++的序列化
	 * @author Onion_Ye
	 * @time 2020年7月20日 下午6:04:10
	 */
	public static native void close(long nativeId);
	
}
