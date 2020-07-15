package cn.yezhss.seetaface.po;

/**
 * 
 * @author Onion_Ye
 * @time 2020年6月19日 上午10:28:41
 */
public class SeetaModelSetting {

	public static final int SEETA_DEVICE_AUTO = 0;
	
	public static final int SEETA_DEVICE_CPU = 1;
	
	public static final int SEETA_DEVICE_GPU = 2;
	
	/**
	 * SEETA_DEVICE_AUTO = 0
	 * SEETA_DEVICE_CPU  = 1
	 * SEETA_DEVICE_GPU  = 2
	 */
	public int device = -1;
	
	/**
	 * 当device是SEETA_DEVICE_GPU, id为使用GPU的id
	 */
	public int id = -1;
	
	/**
	 * csta的具体路径
	 */
	public String model;
	
	public SeetaModelSetting(String cstaPath) {
		this.model = cstaPath;
	}
	
}
