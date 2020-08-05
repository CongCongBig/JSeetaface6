#include "pch.h"
#include <yezhss/cn_yezhss_seetaface_cxx_AgePredictorNative.h>
#include <seeta/AgePredictor.h>

/*
 * Class:     cn_yezhss_seetaface_cxx_AgePredictorNative
 * Method:    init
 * Signature: (Lcn/yezhss/seetaface/po/SeetaModelSetting;)J
 */
JNIEXPORT jlong JNICALL Java_cn_yezhss_seetaface_cxx_AgePredictorNative_init
(JNIEnv* env, jclass, jobject setting)
{
	seeta::ModelSetting modelSetting = toSetting(env, setting);
	seeta::AgePredictor* age = new seeta::AgePredictor(modelSetting);
	return (jlong)age;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_AgePredictorNative
 * Method:    cropFace
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;[Lcn/yezhss/seetaface/po/SeetaPointF;)Lcn/yezhss/seetaface/po/SeetaImageData;
 */
JNIEXPORT jobject JNICALL Java_cn_yezhss_seetaface_cxx_AgePredictorNative_cropFace
(JNIEnv* env, jclass, jlong nativeId, jobject imageData, jobjectArray seetaPointFs)
{
	seeta::AgePredictor* age = (seeta::AgePredictor*) nativeId;
	SeetaImageData image = toSeetaImageData(env, imageData);
	SeetaImageData face;
	SeetaPointF* points = toPoints(env, seetaPointFs);
	bool isCrop = age->CropFace(image, points, face);
	delete points;
	return isCrop ? toSeetaImageData(env, face) : NULL;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_AgePredictorNative
 * Method:    predictAge
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;)I
 */
JNIEXPORT jint JNICALL Java_cn_yezhss_seetaface_cxx_AgePredictorNative_predictAge
(JNIEnv* env, jclass, jlong nativeId, jobject face)
{
	seeta::AgePredictor* age = (seeta::AgePredictor*) nativeId;
	SeetaImageData faceData = toSeetaImageData(env, face);
	int* ageNum = new int();
	bool isSuccess = age->PredictAge(faceData, *ageNum);
	int result = -1;
	if (isSuccess) {
		result = *ageNum;
	}
	delete ageNum;
	return result;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_AgePredictorNative
 * Method:    predictAgeWithCrop
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;[Lcn/yezhss/seetaface/po/SeetaPointF;)I
 */
JNIEXPORT jint JNICALL Java_cn_yezhss_seetaface_cxx_AgePredictorNative_predictAgeWithCrop
(JNIEnv* env, jclass, jlong nativeId, jobject image, jobjectArray points)
{
	seeta::AgePredictor* age = (seeta::AgePredictor*) nativeId;
	SeetaImageData imageData = toSeetaImageData(env, image);
	SeetaPointF* pointFs = toPoints(env, points);
	int* ageNum = new int();
	bool isSuccess = age->PredictAgeWithCrop(imageData, pointFs, *ageNum);
	int result = -1;
	if (isSuccess) {
		result = *ageNum;
	}
	delete ageNum;
	return result;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_AgePredictorNative
 * Method:    set
 * Signature: (JID)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_AgePredictorNative_set
(JNIEnv* env, jclass, jlong nativeId, jint property, jdouble value)
{
	seeta::AgePredictor* age = (seeta::AgePredictor*) nativeId;
	age->set(seeta::AgePredictor::Property(property), value);
}

/*
 * Class:     cn_yezhss_seetaface_cxx_AgePredictorNative
 * Method:    get
 * Signature: (JI)D
 */
JNIEXPORT jdouble JNICALL Java_cn_yezhss_seetaface_cxx_AgePredictorNative_get
(JNIEnv* env, jclass, jlong nativeId, jint property)
{
	seeta::AgePredictor* age = (seeta::AgePredictor*) nativeId;
	return age->get(seeta::AgePredictor::Property(property));
}

/*
 * Class:     cn_yezhss_seetaface_cxx_AgePredictorNative
 * Method:    close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_AgePredictorNative_close
(JNIEnv*, jclass, jlong nativeId)
{
	seeta::AgePredictor* age = (seeta::AgePredictor*) nativeId;
	delete age;
}