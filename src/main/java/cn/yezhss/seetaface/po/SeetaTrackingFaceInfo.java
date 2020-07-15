package cn.yezhss.seetaface.po;

/**
 * 人脸信息
 * @author Onion_Ye
 * @time 2020年7月10日 下午2:30:09
 */
public class SeetaTrackingFaceInfo {

	/**
	 * 人脸位置
	 */
	public SeetaRect pos;
	
	/**
	 * 人脸置信分数
	 */
	public float score;
	
	/**
	 * 视频帧的索引
	 */
	public int frameNo;
	
	/**
	 * 跟踪的人脸标识id
	 */
	public int pid;
	
	public int step;
	
}
