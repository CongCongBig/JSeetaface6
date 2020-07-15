package cn.yezhss.seetaface.po;

/**
 * 
 * @author Onion_Ye
 * @time 2020年6月24日 下午6:06:16
 */
public class PreFrameScore {

	/**
	 * 人脸质量分数/清晰度阈值 当设置时越高要求输入的图像质量越高 默认0.3
	 */
	float clarity;
	
	/**
	 * 真实度/活体阈值 当设置时越高对识别要求越严格 0.8
	 */
	float reality;
	
}
