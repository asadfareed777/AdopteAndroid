#include <jni.h>
#include <string>



std::string baseurl = "http://api.adopteunelivraison.bruncheat.fr/"; //  Stagging URL
//std::string baseurl = "https://saaf.punjab.gov.pk/api_pose/"; //  Live URL

extern "C"
JNIEXPORT jstring JNICALL
Java_com_didaagency_adopteunelivraison_base_BaseApp_baseurl(JNIEnv *env, jobject thiz) {
    // TODO: implement baseurl()
    return env->NewStringUTF(baseurl.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_didaagency_adopteunelivraison_base_BaseApp_apiurl(JNIEnv *env, jobject thiz) {
    // TODO: implement apiurl()
    return env->NewStringUTF(baseurl.c_str());
}