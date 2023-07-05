# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep public class * extends android.app.Activity
-keep public class * extends androidx.appcompat.app.AppCompatActivity
-keep public class * extends androidx.fragment.app.FragmentActivity
-keep public class * extends androidx.fragment.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.Fragment

-keep class org.apache.* { *; }
-keep interface org.apache.* { *; }

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}


#Material Dialog
-keep class com.afollestad.materialdialogs.internal.progress.* { *; }



-dontwarn com.parse.**
-keep class com.parse.* { *; }

#-dontwarn com.androidquery.auth.**
-dontwarn oauth.signpost.**

#General
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-dontskipnonpubliclibraryclasses
-dontobfuscate
-forceprocessing
-optimizationpasses 5


#-keep class com.didaagency.adopteunelivraison.data.network.request.** { *; }
#-keep class com.didaagency.adopteunelivraison.data.network.response.** { *; }
#-keep class com.didaagency.adopteunelivraison.data.database.entities.** { *; }

-keep class com.didaagency.adopteunelivraison.data.** { <fields>; }
#-keep class pitb.com.pk.sampleproject.apiModels.requestModels.** { <fields>; }

 #### -- Apache Commons --
 -dontwarn org.apache.commons.logging.**
 -dontwarn okhttp3.internal.platform.*
 -dontwarn org.conscrypt.*

 #### -- OkHttp --
-dontwarn com.squareup.okhttp.internal.**
-dontwarn okio.**
-dontwarn okhttp3.**

-keep class okhttp3.* { *; }
-keep interface okhttp3.* { *; }

-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

# Retrofit
-keep class com.google.gson.* { *; }
-keep public class com.google.gson.* {public private protected *;}
-keep class com.google.inject.* { *; }
-keep class org.apache.http.* { *; }
-keep class org.apache.james.mime4j.* { *; }
-keep class javax.inject.* { *; }
-keep class javax.xml.stream.* { *; }
-keep class retrofit.* { *; }
-keep class com.google.appengine.* { *; }
-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.squareup.okhttp.*
-dontwarn rx.**
-dontwarn javax.xml.stream.**
-dontwarn com.google.appengine.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.**



-dontwarn retrofit2.**
-dontwarn org.codehaus.mojo.**

-keep class retrofit2.* { *; }
-keepattributes Exceptions
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepattributes EnclosingMethod
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}
# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

-keep class com.crashlytics.* { *; }
-dontwarn com.crashlytics.**