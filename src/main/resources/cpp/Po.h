#include <seeta/Common/CStruct.h>
#include <seeta/Common/Struct.h>
#include <seeta/CFaceInfo.h>
#include "JniUtil.h"

/*
	java ModelSetting -> c++ ModelSetting
	@author YeZhiCong
	@Date 2020-06-23
*/
static seeta::ModelSetting toSetting(JNIEnv *env, jobject setting) {
	jclass sClazz = getClass(env, setting);
	int device = getInt(env, setting, sClazz, "device");
	int id = getInt(env, setting, sClazz, "id");
	char* model = getString(env, setting, sClazz, "model");
	seeta::ModelSetting modelSetting;
	modelSetting.append(model);
	if (device != NULL) {
		if (device == 1) {
			modelSetting.device = SeetaDevice::SEETA_DEVICE_CPU;
		}
		else if (device == 2) {
			modelSetting.device = SeetaDevice::SEETA_DEVICE_GPU;
			modelSetting.id = id;
		}
		else {
			modelSetting.device = SeetaDevice::SEETA_DEVICE_AUTO;
		}
	}
	return modelSetting;
}

static unsigned char* jByteaArray2Chars(JNIEnv* env, jbyteArray bytearray)
{
	jbyte* bytes = env->GetByteArrayElements(bytearray, JNI_FALSE);
	int chars_len = env->GetArrayLength(bytearray);
	unsigned char* chars = new unsigned char[chars_len + 1];
	memset(chars, 0, chars_len + 1);
	memcpy(chars, bytes, chars_len);
	chars[chars_len] = 0;
	env->ReleaseByteArrayElements(bytearray, bytes, 0);
	return chars;
}

/*
	java SeetaImageData -> c++ SeetaImageData
	@author YeZhiCong
	@Date 2020-06-23
*/
static SeetaImageData toSeetaImageData(JNIEnv* env, jobject seetaImageData)
{
	SeetaImageData* sid = new SeetaImageData();
	jclass imageClazz = getClass(env, seetaImageData);
	sid->width = getInt(env, seetaImageData, imageClazz, "width");
	sid->height = getInt(env, seetaImageData, imageClazz, "height");
	sid->channels = getInt(env, seetaImageData, imageClazz, "channels");

	jfieldID id_data = env->GetFieldID(imageClazz, "data", "[B");
	jobject data = env->GetObjectField(seetaImageData, id_data);
	jbyteArray dataArray = (jbyteArray)data;
	sid->data = jByteaArray2Chars(env, dataArray);
	SeetaImageData result = *sid;
	delete sid;
	return result;
}

/*
	c++ SeetaImageData -> java SeetaImageData
	@author YeZhiCong
	@Date 2020-06-23
*/
static jobject toSeetaImageData(JNIEnv* env, SeetaImageData seetaImageData)
{
	jclass imageClazz = getClass(env, "cn/yezhss/seetaface/po/SeetaImageData");
	jobject image = newObject(env, imageClazz);
	setInt(env, image, imageClazz, "width", seetaImageData.width);
	setInt(env, image, imageClazz, "height", seetaImageData.height);
	setInt(env, image, imageClazz, "channels", seetaImageData.channels);

	jfieldID data_data = env->GetFieldID(imageClazz, "data", "[B");
	int count = seetaImageData.width * seetaImageData.height * seetaImageData.channels;
	jbyteArray byteArray = env->NewByteArray(count);
	jbyte* bytes = new jbyte();
	memcpy(seetaImageData.data, bytes, count);
	env->SetByteArrayRegion(byteArray, 0, count, bytes);
	delete bytes;
	env->SetObjectField(image, data_data, byteArray);
	return image;
}

/*
	c++ SeetaFaceInfoArray -> java SeetaFaceInfoArray
	@author YeZhiCong
	@Date 2020-06-23
*/
static jobject toSeetaFaceInfoArray(JNIEnv *env, SeetaFaceInfoArray seetaFaceInfoArray) {
	jclass infosClazz = getClass(env, "cn/yezhss/seetaface/po/SeetaFaceInfoArray");
	// 新建对象
	jobject infos = newObject(env, infosClazz);
	// 设置size
	setInt(env, infos, infosClazz, "size", seetaFaceInfoArray.size);
	// 设置data
	jclass infoClazz = getClass(env, "cn/yezhss/seetaface/po/SeetaFaceInfo");
	jobjectArray datas = env->NewObjectArray(seetaFaceInfoArray.size, infoClazz, NULL);
	jmethodID initMethod = getInitMethod(env, infoClazz);
	// 设置SeetaRect
	jclass rectClazz = getClass(env, "cn/yezhss/seetaface/po/SeetaRect");
	jmethodID initRect = getInitMethod(env, rectClazz);
	jfieldID posField = env->GetFieldID(infoClazz, "pos", "Lcn/yezhss/seetaface/po/SeetaRect;");
	for (int i = 0; i < seetaFaceInfoArray.size; i++) {
		jobject info = newObject(env, infoClazz, initMethod);
		jobject rect = newObject(env, rectClazz, initRect);
		SeetaFaceInfo seetaFaceInfo = seetaFaceInfoArray.data[i];
		setFloat(env, info, infoClazz, "score", seetaFaceInfo.score);
		SeetaRect seetaRect = seetaFaceInfo.pos;
		setInt(env, rect, rectClazz, "x", seetaRect.x);
		setInt(env, rect, rectClazz, "y", seetaRect.y);
		setInt(env, rect, rectClazz, "width", seetaRect.width);
		setInt(env, rect, rectClazz, "height", seetaRect.height);
		env->SetObjectField(info, posField, rect);
		env->SetObjectArrayElement(datas, i, info);
	}
	jfieldID dataField = env->GetFieldID(infosClazz, "data", "[Lcn/yezhss/seetaface/po/SeetaFaceInfo;");
	env->SetObjectField(infos, dataField, datas);
	return infos;
}

static SeetaRect toRect(JNIEnv* env, jobject rect) {
	SeetaRect* seetaRect = new SeetaRect();
	jclass rectClazz = getClass(env, rect);
	
	seetaRect->x = getInt(env, rect, rectClazz, "x");
	seetaRect->y = getInt(env, rect, rectClazz, "y");
	seetaRect->width = getInt(env, rect, rectClazz, "width");
	seetaRect->height = getInt(env, rect, rectClazz, "height");
	SeetaRect resutl = *seetaRect;
	delete seetaRect;
	return resutl;
}

static SeetaPointF* toPoints(JNIEnv* env, jobjectArray arr) {
	int len = env->GetArrayLength(arr);
	SeetaPointF* points = new SeetaPointF[len];
	for (int i = 0; i < len; i++) {
		jobject seetaPointF = env->GetObjectArrayElement(arr, i);
		jclass seetaPointFObject = getClass(env, seetaPointF);
		jfieldID xField = env->GetFieldID(seetaPointFObject, "x", "D");
		jfieldID yField = env->GetFieldID(seetaPointFObject, "y", "D");
		points[i].x = env->GetDoubleField(seetaPointF, xField);
		points[i].y = env->GetDoubleField(seetaPointF, yField);
	}
	return points;
}