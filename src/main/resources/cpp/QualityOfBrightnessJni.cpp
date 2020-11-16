#include "pch.h"
#include <yezhss/cn_yezhss_seetaface_cxx_QualityOfBrightnessNative.h>
#include <seeta/QualityOfBrightness.h>

static jobject toQualityResult(JNIEnv* env, seeta::QualityResult result)
{
	jclass qualityResultClazz = getClass(env, "cn.yezhss.seetaface.po.QualityResult");
	jobject qualityResult = newObject(env, qualityResultClazz);
	setInt(env, qualityResult, qualityResultClazz, "level", result.level);
	setFloat(env, qualityResult, qualityResultClazz, "score", result.score);
	return qualityResult;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfBrightnessNative
 * Method:    init
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfBrightnessNative_init__
(JNIEnv*, jclass)
{
	seeta::QualityOfBrightness* quality = new seeta::QualityOfBrightness();
	return (jlong)quality;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfBrightnessNative
 * Method:    init
 * Signature: (FFFF)J
 */
JNIEXPORT jlong JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfBrightnessNative_init__FFFF
(JNIEnv*, jclass, jfloat v0, jfloat v1, jfloat v2, jfloat v3)
{
	seeta::QualityOfBrightness* quality = new seeta::QualityOfBrightness(v0, v1, v2, v3);
	return (jlong)quality;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfBrightnessNative
 * Method:    check
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;Lcn/yezhss/seetaface/po/SeetaRect;[Lcn/yezhss/seetaface/po/SeetaPointF;)Lcn/yezhss/seetaface/po/QualityResult;
 */
JNIEXPORT jobject JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfBrightnessNative_check
(JNIEnv* env, jclass, jlong nativeId, jobject image, jobject rect, jobjectArray points)
{
	seeta::QualityOfBrightness* quality = (seeta::QualityOfBrightness*) nativeId;
	int n = env->GetArrayLength(points);
	SeetaImageData imageData = toSeetaImageData(env, image);
	jbyteArray dataArray = getSeetaImageDataByteArray(env, image);
	jbyte* array = env->GetByteArrayElements(dataArray, 0);
	imageData.data = (unsigned char*)array;

	SeetaRect face = toRect(env, rect);
	SeetaPointF* pointFs = toPoints(env, points);
	seeta::QualityResult result = quality->check(imageData, face, pointFs, n);
	delete pointFs;
	env->ReleaseByteArrayElements(dataArray, array, 0);
	env->DeleteLocalRef(image);
	return toQualityResult(env, result);
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfBrightnessNative
 * Method:    close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfBrightnessNative_close
(JNIEnv*, jclass, jlong nativeId)
{
	seeta::QualityOfBrightness* quality = (seeta::QualityOfBrightness*) nativeId;
	delete quality;
}