#include "pch.h"
#include <yezhss/cn_yezhss_seetaface_cxx_QualityOfLBNNative.h>
#include <seeta/QualityOfLBN.h>

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfLBNNative
 * Method:    init
 * Signature: (Lcn/yezhss/seetaface/po/SeetaModelSetting;)J
 */
JNIEXPORT jlong JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfLBNNative_init
(JNIEnv* env, jclass, jobject setting)
{
	seeta::ModelSetting modelSetting = toSetting(env, setting);
	seeta::QualityOfLBN* quality = new seeta::QualityOfLBN(modelSetting);
	return (jlong)quality;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfLBNNative
 * Method:    detect
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;[Lcn/yezhss/seetaface/po/SeetaPointF;)Lcn/yezhss/seetaface/po/BlurInfo;
 */
JNIEXPORT jobject JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfLBNNative_detect
(JNIEnv* env, jclass, jlong nativeId, jobject image, jobjectArray points)
{
	seeta::QualityOfLBN* quality = (seeta::QualityOfLBN*) nativeId;

	int* light = new int(), *blur = new int(), *noise = new int();
	SeetaImageData imageData = toSeetaImageData(env, image);
	jbyteArray dataArray = getSeetaImageDataByteArray(env, image);
	jbyte* array = env->GetByteArrayElements(dataArray, 0);
	imageData.data = (unsigned char*)array;

	SeetaPointF* pointFs = toPoints(env, points);
	quality->Detect(imageData, pointFs, light, blur, noise);
	jclass blurInfoClazz = getClass(env, "cn.yezhss.seetaface.po.BlurInfo");
	jobject blurInfo = newObject(env, blurInfoClazz);
	setInt(env, blurInfo, blurInfoClazz, "light", *light);
	setInt(env, blurInfo, blurInfoClazz, "blur", *blur);
	setInt(env, blurInfo, blurInfoClazz, "noise", *noise);
	delete pointFs, light, blur, noise;
	env->ReleaseByteArrayElements(dataArray, array, 0);
	env->DeleteLocalRef(image);
	return blurInfo;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfLBNNative
 * Method:    set
 * Signature: (JID)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfLBNNative_set
(JNIEnv* env, jclass, jlong nativeId, jint property, jdouble value)
{
	seeta::QualityOfLBN* quality = (seeta::QualityOfLBN*) nativeId;
	quality->set(seeta::QualityOfLBN::Property(property), value);
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfLBNNative
 * Method:    get
 * Signature: (JI)D
 */
JNIEXPORT jdouble JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfLBNNative_get
(JNIEnv*, jclass, jlong nativeId, jint property)
{
	seeta::QualityOfLBN* quality = (seeta::QualityOfLBN*) nativeId;
	return quality->get(seeta::QualityOfLBN::Property(property));
}

/*
 * Class:     cn_yezhss_seetaface_cxx_QualityOfLBNNative
 * Method:    close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_QualityOfLBNNative_close
(JNIEnv*, jclass, jlong nativeId)
{
	seeta::QualityOfLBN* quality = (seeta::QualityOfLBN*) nativeId;
	delete quality;
}