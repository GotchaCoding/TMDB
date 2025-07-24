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

#-keep class org.techtown.diffuser.response.video.** { *; }
-keepattributes SourceFile,LineNumberTable        # Keep file names and line numbers.
-keep public class * extends java.lang.Exception  # Optional: Keep custom exceptions.

#-keep class org.techtown.diffuser.room.WordRoomDatabase.** { *; }

-keep class * implements java.io.Serializable { *; }

############ Retrofit & Gson ############

# Retrofit
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-dontwarn retrofit2.**

# Gson (혹은 Moshi 사용 시 수정)
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Model 클래스에 @SerializedName 사용 시
-keepattributes *Annotation*

# retrofit2.Call<>, etc.
-keep interface okhttp3.* { *; }
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# Keep generic type info for Gson
-keepattributes Signature

############ Your Models (예시 경로) ############

# 전체 response 모델 경로 보존
-keep class org.techtown.diffuser.response.** { *; }
-keep class org.techtown.diffuser.model.** { *; }
-keep class org.techtown.diffuser.room.** { *; }

############ 기타 (Serializable, Enum 등) ############

-keep class * implements java.io.Serializable { *; }
-keepclassmembers class * {
    public <init>(...);
}

############ Activity, Fragment, ViewModel 유지 ############

-keep class * extends android.app.Activity { *; }
-keep class * extends androidx.fragment.app.Fragment { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }

############ Firebase (사용 시) ############
-dontwarn com.google.firebase.**
-keep class com.google.firebase.** { *; }