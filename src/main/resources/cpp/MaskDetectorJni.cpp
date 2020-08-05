#include "pch.h"
#include <yezhss/cn_yezhss_seetaface_cxx_MaskDetectorNative.h>
#include <seeta/MaskDetector.h>

/*
 * Class:     cn_yezhss_seetaface_cxx_MaskDetectorNative
 * Method:    init
 * Signature: (Lcn/yezhss/seetaface/po/SeetaModelSetting;)J
 */
JNIEXPORT jlong JNICALL Java_cn_yezhss_seetaface_cxx_MaskDetectorNative_init
(JNIEnv* env, jclass, jobject setting)
{
	seeta::ModelSetting modelSetting = toSetting(env, setting);
	seeta::MaskDetector* maskDetector = new seeta::MaskDetector(modelSetting);
	return (jlong)maskDetector;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_MaskDetectorNative
 * Method:    detect
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;Lcn/yezhss/seetaface/po/SeetaRect;F)Z
 */
JNIEXPORT jobject JNICALL Java_cn_yezhss_seetaface_cxx_MaskDetectorNative_detect
(JNIEnv* env, jclass, jlong nativeId, jobject imageData, jobject rect)
{
	SeetaImageData image = toSeetaImageData(env, imageData);
	SeetaRect face = toRect(env, rect);
	seeta::MaskDetector* maskDetector = (seeta::MaskDetector*) nativeId;
	float* score = new float();
	bool isMask = maskDetector->detect(image, face, score);
	jclass maskStatusClazz = getClass(env, "cn/yezhss/seetaface/po/MaskStatus");
	jobject maskStatus = newObject(env, maskStatusClazz);
	jfieldID statusfield = env->GetFieldID(maskStatusClazz, "status", "Z");
	jfieldID scorefield = env->GetFieldID(maskStatusClazz, "score", "F");
	env->SetBooleanField(maskStatus, statusfield, isMask);
	env->SetFloatField(maskStatus, scorefield, *score);
	delete score;
	return maskStatus;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_MaskDetectorNative
 * Method:    close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_MaskDetectorNative_close
(JNIEnv*, jclass, jlong nativeId)
{
	seeta::MaskDetector* maskDetector = (seeta::MaskDetector*) nativeId;
	delete maskDetector;
}