#include <jni.h>
JNIEXPORT jstring JNICALL
Java_cosmas_egbosi_photosapp_MainActivity_getPixaApiKey(JNIEnv *env, jobject instance) {
 return (*env)->  NewStringUTF(env, "2*********");
}
