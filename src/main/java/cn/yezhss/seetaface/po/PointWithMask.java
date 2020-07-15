package cn.yezhss.seetaface.po;

/**
 * 
 * @author Onion_Ye
 * @time 2020年6月22日 下午2:10:17
 */
public class PointWithMask {
	
	/**
	 * 人脸特征点
	 */
	public SeetaPointF point;
	/**
	 * false表示未被遮挡,true表示被遮挡
	 */
	public boolean mask;
	
}
