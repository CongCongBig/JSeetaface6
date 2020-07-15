package cn.yezhss.seetaface.po;

/**
 * 模糊信息
 * @author Onion_Ye
 * @time 2020年7月9日 上午9:53:39
 */
public class BlurInfo {

	/**
	 * 亮度返回结果，暂不推荐使用该返回结果
	 */
	public int light;
	
	/**
	 * 结果返回 0 说明人脸是清晰的, 为 1 说明人脸是模糊的。
	 */
	public int blur;
	
	/**
	 * 是否有噪声返回结果，暂不推荐使用该返回结果
	 */
	public int noise;
	
}
