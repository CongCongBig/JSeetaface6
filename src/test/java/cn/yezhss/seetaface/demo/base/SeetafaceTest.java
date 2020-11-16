package cn.yezhss.seetaface.demo.base;

import java.util.Properties;

/**
 * @author Onion_Ye
 * @time 2020年6月23日 下午4:14:12
 */
public class SeetafaceTest {

	/**
	 * dll基础路径
	 * <pre>
	 * 1. 下载路径:https://github.com/seetafaceengine/SeetaFace6
	 * 2. 拖到下面介绍有一个模块叫<strong>下载地址</strong>
	 * 3. 找到windows下载开发包
	 * 4. 下载完成后解压 将下面地址指向 %解压的路径%/sf6.0_windows/lib/x64
	 * </pre>
	 */
	public static final String DLL_PATH = "D:/sf6.0_windows/lib/x64";
	
	/**
	 * CSTA基础路径
	 * <pre>
	 * 1. 下载路径:https://github.com/seetafaceengine/SeetaFace6
	 * 2. 拖到下面介绍有一个模块叫<strong>下载地址</strong>
	 * 3. 找到模型文件 下载Part1
	 * 4. 下载完成后解压 将下面地址指向 %解压的路径%/sf3.0_models
	 * </pre>
	 */
	public static final String CSTA_PATH = "D:/sf3.0_models";
	
	/**
	 * 测试的图片(更换到你自己的图片路径)
	 */
	public static final String TEST_PICT = "D:/test/3.jpg";
	
	
	static {
		// 加载dll
        System.load(DLL_PATH + "/tennis.dll");
        System.load(DLL_PATH + "/SeetaAuthorize.dll");
        System.load(DLL_PATH + "/SeetaFaceDetector600.dll");
        System.load(DLL_PATH + "/SeetaFaceLandmarker600.dll");
        System.load(DLL_PATH + "/SeetaMaskDetector200.dll");
        System.load(DLL_PATH + "/SeetaGenderPredictor600.dll");
        System.load(DLL_PATH + "/SeetaAgePredictor600.dll");
        System.load(DLL_PATH + "/SeetaEyeStateDetector200.dll");
        System.load(DLL_PATH + "/SeetaFaceAntiSpoofingX600.dll");
        System.load(DLL_PATH + "/SeetaPoseEstimation600.dll");
        System.load(DLL_PATH + "/SeetaQualityAssessor300.dll");
        System.load(DLL_PATH + "/SeetaFaceRecognizer610.dll");
        System.load(DLL_PATH + "/SeetaFaceTracking600.dll");
        Properties pro = System.getProperties();
        // 仅支持
        String os = pro.getProperty("os.name").toUpperCase();
        if (!os.contains("WINDOWS")) {
        	throw new RuntimeException("暂不支持非windows系统");
        }
        String arch = pro.getProperty("os.arch");
        if (!arch.contains("64")) {
        	throw new RuntimeException("暂不支持非64位系统");
        }
        String path = SeetafaceTest.class.getResource("/x64/JSeetaFace6.dll").getPath();
        System.load(path);
	}
	
}
