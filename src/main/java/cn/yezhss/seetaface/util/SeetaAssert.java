package cn.yezhss.seetaface.util;

import cn.yezhss.seetaface.exception.JSeetaface6ValidateException;
import static cn.yezhss.seetaface.exception.JSeetaface6ValidateException.NATIVE_ID_NULL;
import static cn.yezhss.seetaface.exception.JSeetaface6ValidateException.PARAMS_NULL;;

/**
 * 断言
 * @author Onion_Ye
 * @time 2020年7月20日 上午9:47:32
 */
public class SeetaAssert {

	/**
	 * 校验入参以及类是否完整
	 * @param nativeId 序列化ID
	 * @param objects 入参对象
	 * @author Onion_Ye
	 * @time 2020年7月20日 上午9:54:02
	 */
	public static void validate(boolean isClose, Object...objects) {
		if (isClose)
			throw new JSeetaface6ValidateException(NATIVE_ID_NULL, "该类已被释放无法使用.");
		for (Object o : objects) {
			if (o == null) {
				throw new JSeetaface6ValidateException(PARAMS_NULL, "参数不能为空.");
			}
		}
	}
	
}
