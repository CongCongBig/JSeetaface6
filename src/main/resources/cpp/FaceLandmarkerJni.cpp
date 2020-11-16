#include "pch.h"
#include <yezhss/cn_yezhss_seetaface_cxx_FaceLandmarkerNative.h>
#include <seeta/FaceLandmarker.h>

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceLandmarkerNative
 * Method:    init
 * Signature: (Lcn/yezhss/seetaface/po/SeetaModelSetting;)J
 */
JNIEXPORT jlong JNICALL Java_cn_yezhss_seetaface_cxx_FaceLandmarkerNative_init
(JNIEnv* env, jclass, jobject setting)
{
	seeta::ModelSetting modelSetting = toSetting(env, setting);
	seeta::FaceLandmarker* faceLandmarker = new seeta::FaceLandmarker(modelSetting);
	return (jlong)faceLandmarker;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceLandmarkerNative
 * Method:    number
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_cn_yezhss_seetaface_cxx_FaceLandmarkerNative_number
(JNIEnv* env, jclass, jlong nativeId)
{
	seeta::FaceLandmarker* landmarker = (seeta::FaceLandmarker*) nativeId;
	return landmarker->number();
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceLandmarkerNative
 * Method:    mark
 * Signature: (JLcn/yezhss/seetaface/po/SeetaImageData;Lcn/yezhss/seetaface/po/SeetaRect;)[Lcn/yezhss/seetaface/po/PointWithMask;
 */
JNIEXPORT jobjectArray JNICALL Java_cn_yezhss_seetaface_cxx_FaceLandmarkerNative_mark
(JNIEnv* env, jclass, jlong nativeId, jobject image, jobject rect)
{
	// 调用mark_v2
	seeta::FaceLandmarker* landmarker = (seeta::FaceLandmarker*) nativeId;

	SeetaImageData imageData = toSeetaImageData(env, image);
	jbyteArray dataArray = getSeetaImageDataByteArray(env, image);
	jbyte* array = env->GetByteArrayElements(dataArray, 0);
	imageData.data = (unsigned char*)array;

	SeetaRect face = toRect(env, rect);
	vector<seeta::FaceLandmarker::PointWithMask> result = landmarker->mark_v2(imageData, face);

	// 封装结果
	jclass maskClazz = getClass(env, "cn/yezhss/seetaface/po/PointWithMask");
	jobjectArray masks = env->NewObjectArray(landmarker->number(), maskClazz, 0);
	jmethodID maskInitMethod = getInitMethod(env, maskClazz);
	jclass pointFClazz = getClass(env, "cn/yezhss/seetaface/po/SeetaPointF");
	jfieldID maskField = env->GetFieldID(maskClazz, "mask", "Z");
	jfieldID seetaPointFField = env->GetFieldID(maskClazz, "point", "Lcn/yezhss/seetaface/po/SeetaPointF;");
	jfieldID xField = env->GetFieldID(pointFClazz, "x", "D");
	jfieldID yField = env->GetFieldID(pointFClazz, "y", "D");
	int i = 0;
	for (auto &mask : result) {
		jobject maskJni = newObject(env, maskClazz);
		env->SetBooleanField(maskJni, maskField, mask.mask);

		jobject pointF = newObject(env, pointFClazz);
		SeetaPointF pf = mask.point;
		env->SetDoubleField(pointF, xField, pf.x);
		env->SetDoubleField(pointF, yField, pf.y);
		env->SetObjectField(maskJni, seetaPointFField, pointF);

		env->SetObjectArrayElement(masks, i++, maskJni);
	}
	
	env->ReleaseByteArrayElements(dataArray, array, 0);
	env->DeleteLocalRef(image);

	return masks;
}

/*
 * Class:     cn_yezhss_seetaface_cxx_FaceLandmarkerNative
 * Method:    close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_cn_yezhss_seetaface_cxx_FaceLandmarkerNative_close
(JNIEnv*, jclass, jlong nativeId)
{
	seeta::FaceLandmarker* landmarker = (seeta::FaceLandmarker*) nativeId;
	delete landmarker;
}