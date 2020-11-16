#include "pch.h"
#include <yezhss/cn_yezhss_seetaface_cxx_QualityOfResolutionNative.h>
#include <seeta/QualityOfResolution.h>

static jobject toQualityResult(JNIEnv* env, seeta::QualityResult result)
{
	jclass qualityResultClazz = getClass(env, "cn.yezhss.seetaface.po.QualityResult");
	jobject qualityResult = newObject(env, qualityResultClazz);
	setInt(env, qualityResult, qualityResultClazz, "level", result.level);
	setFloat(env, qualityResult, qualityResultClazz, "score", result.score);
	return qualityResult;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfResolutionNative
 * Method:    init
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfResolutionNative_init__
(JNIEnv*, jclass)
{
	seeta::QualityOfResolution* quality = new seeta::QualityOfResolution();
	return (jlong)quality;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfResolutionNative
 * Method:    init
 * Signature: (FF)J
 */
JNIEXPORT jlong JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfResolutionNative_init__FF
(JNIEnv*, jclass, jfloat low, jfloat high)
{
	seeta::QualityOfResolution* quality = new seeta::QualityOfResolution(low, high);
	return (jlong)quality;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfResolutionNative
 * Method:    check
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;Lcn/yezhss/seetaface/po/SeetaRect;[Lcn/yezhss/seetaface/po/SeetaPointF;)Lcn/yezhss/seetaface/po/QualityResult;
 */
JNIEXPORT jobject JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfResolutionNative_check
(JNIEnv* env, jclass, jlong nativeId, jobject image, jobject rect, jobjectArray points)
{
	seeta::QualityOfResolution* quality = (seeta::QualityOfResolution*)nativeId;
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
 * Class:     cn_yezhss_seetaface_cxx_QualityOfResolutionNative
 * Method:    close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfResolutionNative_close
(JNIEnv*, jclass, jlong nativeId)
{
	seeta::QualityOfResolution* quality = (seeta::QualityOfResolution*)nativeId;
	delete quality;
}