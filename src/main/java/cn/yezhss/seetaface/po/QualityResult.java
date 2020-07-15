package cn.yezhss.seetaface.po;

/**
 * 质量评估结果集
 * @author Onion_Ye
 * @time 2020年7月6日 上午11:33:17
 */
public class QualityResult {

	/**
	 * 表示质量等级 差、良、优
	 * <pre>
	 * 差 LOW = 0 
	 * 良 MEDIUM = 1
	 * 优 HIGH = 2
	 * </pre>
	 */
	public int level;
	
	/**
	 * 质量评价 但是不保证取值范围，越大质量越好
	 */
	public float score;
	
}
