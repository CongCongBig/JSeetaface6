#include "jni.h"
#include <iostream>
using namespace std;

static jclass getClass(JNIEnv* env, jobject obj) {
	return env->GetObjectClass(obj);
}

static jclass getClass(JNIEnv* env, const char* classPath) {
	return env->FindClass(classPath);
}

static jmethodID getInitMethod(JNIEnv* env, jclass clazz) {
	return env->GetMethodID(clazz, "<init>", "()V");
}

static jobject newObject(JNIEnv* env, jclass clazz, jmethodID methodId) {
	return env->NewObject(clazz, methodId);
}

static jobject newObject(JNIEnv* env, jclass clazz) {
	return newObject(env, clazz, getInitMethod(env, clazz));
}

static int getInt(JNIEnv* env, jobject obj, jclass clazz, const char* field) {
	jfieldID intId = env->GetFieldID(clazz, field, "I");
	return env->GetIntField(obj, intId);
}

static int getInt(JNIEnv* env, jobject obj, const char* field) {
	jclass clazz = getClass(env, obj);
	return getInt(env, obj, clazz, field);
}

static void setInt(JNIEnv* env, jobject obj, jclass clazz, const char* field, int val) {
	jfieldID intId = env->GetFieldID(clazz, field, "I");
	return env->SetIntField(obj, intId, val);
}

static void setFloat(JNIEnv* env, jobject obj, jclass clazz, const char* field, float val) {
	jfieldID floatId = env->GetFieldID(clazz, field, "F");
	return env->SetFloatField(obj, floatId, val);
}

static char* getString(JNIEnv* env, jobject obj, jclass clazz, const char* field) {
	jfieldID objId = env->GetFieldID(clazz, field, "Ljava/lang/String;");
	jstring str = (jstring)env->GetObjectField(obj, objId);
	return (char*)(env->GetStringUTFChars(str, 0));
}

static char* getString(JNIEnv* env, jobject obj, const char* field) {
	jclass clazz = getClass(env, obj);
	return getString(env, obj, clazz, field);
}