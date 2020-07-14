package cn.yezhss.seetaface.demo.base;

import java.util.Properties;

/**
 * 
 * @author YeZhiCong
 * @time 2020年6月23日 下午4:14:12
 */
public class SeetafaceTest {

	/**
	 * dll基础路径
	 */
	public static final String DLL_PATH = "E:/workspace/github/SeetaFace6/sf6.0_windows/lib/x64/";
	
	/**
	 * CSTA基础路径
	 */
	public static final String CSTA_PATH = "E:/workspace/github/SeetaFace6/sf3.0_models";
	
	/**
	 * 测试的图片
	 */
	public static final String TEST_PICT = "D:/18368/9d52c073gy1ftz5ra6ocuj20sg11x1kx.jpg";
	
	
	static {
		// 加载dll
        System.load(DLL_PATH + "tennis.dll");
        System.load(DLL_PATH + "SeetaAuthorize.dll");
        System.load(DLL_PATH + "SeetaFaceDetector600.dll");
        System.load(DLL_PATH + "SeetaFaceLandmarker600.dll");
        System.load(DLL_PATH + "SeetaMaskDetector200.dll");
        System.load(DLL_PATH + "SeetaGenderPredictor600.dll");
        System.load(DLL_PATH + "SeetaAgePredictor600.dll");
        System.load(DLL_PATH + "SeetaEyeStateDetector200.dll");
        System.load(DLL_PATH + "SeetaFaceAntiSpoofingX600.dll");
        System.load(DLL_PATH + "SeetaPoseEstimation600.dll");
        System.load(DLL_PATH + "SeetaQualityAssessor300.dll");
        System.load(DLL_PATH + "SeetaFaceRecognizer610.dll");
        System.load(DLL_PATH + "SeetaFaceTracking600.dll");
        Properties pro = System.getProperties();
        String os = pro.getProperty("os.name").toUpperCase();
        if (os.contains("WINDOWS")) {
        	String arch = pro.getProperty("os.arch");
        	if (arch.contains("64")) {
		        String path = SeetafaceTest.class.getResource("/x64/JSeetaFace6.dll").getPath();
		        System.load(path);
        	}
        }
	}
	
}
