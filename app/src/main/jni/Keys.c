#include <jni.h>
JNIEXPORT jstring JNICALL
Java_cosmas_egbosi_photosapp_MainActivity_getPixaApiKey(JNIEnv *env, jobject instance) {
 return (*env)->  NewStringUTF(env, "27423077-f271be85fd266b68c3258c7f9");
}
