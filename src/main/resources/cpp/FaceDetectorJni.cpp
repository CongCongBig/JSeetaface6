#include "pch.h"
#include <yezhss/cn_yezhss_seetaface_cxx_FaceDetectorNative.h>
#include <seeta/FaceDetector.h>

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceDetectorNative
 * Method:    init
 * Signature: (Lcn/yezhss/seetaface/po/SeetaModelSetting;)J
 */
JNIEXPORT jlong JNICALL Java_cn_yezhss_seetaface_cxx_FaceDetectorNative_init
(JNIEnv* env, jclass, jobject setting) 
{
	seeta::ModelSetting modelSetting = toSetting(env, setting);
	seeta::FaceDetector* facedector = new seeta::FaceDetector(modelSetting);
	return (jlong)facedector;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceDetectorNative
 * Method:    detect
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;)Lcn/yezhss/seetaface/po/SeetaFaceInfoArray;
 */
JNIEXPORT jobject JNICALL Java_cn_yezhss_seetaface_cxx_FaceDetectorNative_detect
(JNIEnv* env, jclass, jlong nativeId, jobject image)
{
	seeta::FaceDetector* facedector = (seeta::FaceDetector*)nativeId;
	SeetaFaceInfoArray infos = facedector->detect(toSeetaImageData(env, image));
	return toSeetaFaceInfoArray(env, infos);
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceDetectorNative
 * Method:    set
 * Signature: (JID)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_FaceDetectorNative_set
(JNIEnv* env, jclass, jlong nativeId, jint property, jdouble val)
{
	seeta::FaceDetector* facedector = (seeta::FaceDetector*)nativeId;
	facedector->set(seeta::FaceDetector::Property(property), val);
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceDetectorNative
 * Method:    get
 * Signature: (JI)D
 */
JNIEXPORT jdouble JNICALL Java_cn_yezhss_seetaface_cxx_FaceDetectorNative_get
(JNIEnv* env, jclass, jlong nativeId, jint property)
{
	seeta::FaceDetector* facedector = (seeta::FaceDetector*)nativeId;
	return facedector->get(seeta::FaceDetector::Property(property));
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceDetectorNative
 * Method:    close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_FaceDetectorNative_close
(JNIEnv*, jclass, jlong nativeId)
{
	seeta::FaceDetector* facedector = (seeta::FaceDetector*)nativeId;
	delete facedector;
}

