package cn.yezhss.seetaface.exception;

/**
 * 
 * @author Onion_Ye
 * @time 2020年7月20日 上午9:45:22
 */
public class JSeetaface6ValidateException extends RuntimeException {

	private static final long serialVersionUID = -6043854326657008674L;
	
	public static final int NATIVE_ID_NULL = 1404;
	
	public static final int PARAMS_NULL = 1403;
	
	private int errCode = -1;

	public JSeetaface6ValidateException() {
		super();
	}
	
	public JSeetaface6ValidateException(String msg) {
		super(msg);
	}
	
	public JSeetaface6ValidateException(int errCode, String msg) {
		super(msg);
	}
	
	/**
	 * 获取异常码
	 * @author Onion_Ye
	 * @time 2020年7月20日 上午9:53:30
	 */
	public int getErrCode() {
		return errCode;
	}
	
}
