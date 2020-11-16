#include "pch.h"
#include <yezhss/cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative.h>
#include <seeta/FaceAntiSpoofing.h>

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    init
 * Signature: (Lcn/yezhss/seetaface/po/SeetaModelSetting;)J
 */
JNIEXPORT jlong JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_init
(JNIEnv* env, jclass, jobject setting, jstring append)
{
	seeta::ModelSetting modelSetting = toSetting(env, setting);
	if (append != NULL)
	{
		const char* appendSetting = env->GetStringUTFChars(append, JNI_FALSE);
		modelSetting.append(appendSetting);
	}
	seeta::FaceAntiSpoofing* spoofing = new seeta::FaceAntiSpoofing(modelSetting);
	return (jlong)spoofing;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    predict
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;Lcn/yezhss/seetaface/po/SeetaRect;[Lcn/yezhss/seetaface/po/SeetaPointF;)I
 */
JNIEXPORT jint JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_predict
(JNIEnv* env, jclass, jlong nativeId, jobject image, jobject rect, jobjectArray points)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	SeetaImageData imageData = toSeetaImageData(env, image);

	jbyteArray dataArray = getSeetaImageDataByteArray(env, image);
	jbyte* array = env->GetByteArrayElements(dataArray, 0);
	imageData.data = (unsigned char*)array;

	SeetaRect face = toRect(env, rect);
	SeetaPointF* pointFs = toPoints(env, points);
	int result = (int)spoofing->Predict(imageData, face, pointFs);

	delete pointFs;
	env->ReleaseByteArrayElements(dataArray, array, 0);
	env->DeleteLocalRef(image);

	return result;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    predictVideo
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;Lcn/yezhss/seetaface/po/SeetaRect;[Lcn/yezhss/seetaface/po/SeetaPointF;)I
 */
JNIEXPORT jint JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_predictVideo
(JNIEnv* env, jclass, jlong nativeId, jobject image, jobject rect, jobjectArray points)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;

	SeetaImageData imageData = toSeetaImageData(env, image);
	jbyteArray dataArray = getSeetaImageDataByteArray(env, image);
	jbyte* array = env->GetByteArrayElements(dataArray, 0);
	imageData.data = (unsigned char*)array;

	SeetaRect face = toRect(env, rect);
	SeetaPointF* pointFs = toPoints(env, points);
	int result = (int)spoofing->PredictVideo(imageData, face, pointFs);

	delete pointFs;
	env->ReleaseByteArrayElements(dataArray, array, 0);
	env->DeleteLocalRef(image);

	return result;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    resetVideo
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_resetVideo
(JNIEnv* env, jclass, jlong nativeId)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	spoofing->ResetVideo();
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    getPreFrameScore
 * Signature: (J)Lcn/yezhss/seetaface/po/PreFrameScore;
 */
JNIEXPORT jobject JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_getPreFrameScore
(JNIEnv* env, jclass, jlong nativeId)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	float* clarity = new float();
	float* reality = new float();
	spoofing->GetPreFrameScore(clarity, reality);
	jclass preFrameScoreClazz = getClass(env, "cn/yezhss/seetaface/po/PreFrameScore");
	jobject preFrameScore = newObject(env, preFrameScoreClazz);
	setFloat(env, preFrameScore, preFrameScoreClazz, "clarity", *clarity);
	setFloat(env, preFrameScore, preFrameScoreClazz, "reality", *reality);
	delete clarity;
	delete reality;
	return preFrameScore;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    setVideoFrameCount
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_setVideoFrameCount
(JNIEnv* env, jclass, jlong nativeId, jint number)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	spoofing->SetVideoFrameCount(number);
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    getVideoFrameCount
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_getVideoFrameCount
(JNIEnv* env, jclass, jlong nativeId)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	return spoofing->GetVideoFrameCount();
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    setThreshold
 * Signature: (JFF)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_setThreshold
(JNIEnv* env, jclass, jlong nativeId, jfloat clarity, jfloat reality)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	spoofing->SetThreshold(clarity, reality);
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    setBoxThresh
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_setBoxThresh
(JNIEnv* env, jclass, jlong nativeId, jfloat boxThresh)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	spoofing->SetBoxThresh(boxThresh);
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    getBoxThresh
 * Signature: (J)F
 */
JNIEXPORT jfloat JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_getBoxThresh
(JNIEnv* env, jclass, jlong nativeId)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	return spoofing->GetBoxThresh();
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    getThreshold
 * Signature: (J)Lcn/yezhss/seetaface/po/PreFrameScore;
 */
JNIEXPORT jobject JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_getThreshold
(JNIEnv* env, jclass, jlong nativeId)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	float* clarity = new float();
	float* reality = new float();
	spoofing->GetThreshold(clarity, reality);
	jclass preFrameScoreClazz = getClass(env, "cn/yezhss/seetaface/po/PreFrameScore");
	jobject preFrameScore = newObject(env, preFrameScoreClazz);
	setFloat(env, preFrameScore, preFrameScoreClazz, "clarity", *clarity);
	setFloat(env, preFrameScore, preFrameScoreClazz, "reality", *reality);
	delete clarity;
	delete reality;
	return preFrameScore;
	
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    set
 * Signature: (JID)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_set
(JNIEnv* env, jclass, jlong nativeId, jint property, jdouble val)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	spoofing->set(seeta::FaceAntiSpoofing::Property(property), val);
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    get
 * Signature: (JI)D
 */
JNIEXPORT jdouble JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_get
(JNIEnv* env, jclass, jlong nativeId, jint property)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	return spoofing->get(seeta::FaceAntiSpoofing::Property(property));
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative
 * Method:    close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_FaceAntiSpoofingNative_close
(JNIEnv*, jclass, jlong nativeId)
{
	seeta::FaceAntiSpoofing* spoofing = (seeta::FaceAntiSpoofing*) nativeId;
	delete spoofing;
}