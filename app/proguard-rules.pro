# Project Custom Keep Rules
-keep class com.kurostream.tv.data.adapter.stremio.model.** { *; }
-keep class com.kurostream.tv.domain.model.** { *; }
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep @androidx.annotation.Keep class * {*;}

# Preserve annotations and type signatures for Retrofit/Gson reflection
-keepattributes Signature, InnerClasses, EnclosingMethod, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations, AnnotationDefault

# Retrofit Keep Rules
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepclassmembers class * {
    @retrofit2.http.** <methods>;
}

# Gson Keep Rules
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Room Keep Rules
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.Dao
-dontwarn androidx.room.**

# OkHttp & Okio Keep Rules
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.bouncycastle.**
-dontwarn org.conscrypt.**
-dontwarn org.openjsse.**

# Coil Keep Rules
-dontwarn coil.**
