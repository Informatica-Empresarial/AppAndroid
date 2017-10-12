# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/oscargallon/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclassmembernames interface * {
    @retrofit.http.* <methods>;
}
# keep everything in this package from being removed or renamed
-keep class co.com.lafemmeapp.dataprovider.network.api_routes.** { *; }

# keep everything in this package from being renamed only
-keepnames class co.com.lafemmeapp.dataprovider.network.api_routes.** { *; }
-keep class com.squareup.okhttp3.** {
*;
}
# keep everything in this package from being removed or renamed
-keep class co.com.lafemmeapp.dataprovider.network.entities.** { *; }

# keep everything in this package from being renamed only
-keepnames class co.com.lafemmeapp.dataprovider.network.entities.** { *; }

# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @android.support.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class co.com.lafemmeapp.core.domain.entities.** { *; }

# keep everything in this package from being renamed only
-keepnames class co.com.lafemmeapp.core.domain.entities.** { *; }

# Add *one* of the following rules to your Proguard configuration file.
# Alternatively, you can annotate classes and class members with @android.support.annotation.Keep

# keep everything in this package from being removed or renamed
-keep class co.com.lafemmeapp.core.domain.params.** { *; }

# keep everything in this package from being renamed only
-keepnames class co.com.lafemmeapp.core.domain.params.** { *; }
-keep interface okhttp3.* {
 *;
}
-keepattributes Signature
-keepattributes Annotation

-dontwarn okhttp3.
-dontwarn okio.**

# rxjava
-keep class rx.schedulers.Schedulers {
    public static <methods>;
}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class rx.schedulers.TestScheduler {
    public <methods>;
}
-keep class rx.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}
-dontwarn org.joda.convert.**
-dontwarn org.joda.time.**
-keep class org.joda.time.** { *; }
-keep interface org.joda.time.** { *; }
-keepattributes SourceFile,LineNumberTable
-keep class com.parse.*{ *; }
-dontwarn com.parse.**
-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}
-dontwarn rx.internal.util.unsafe.**
-optimizationpasses 1
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

#-dontobfuscate

-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue
-dontwarn javax.swing.**
-dontwarn java.awt.**
-dontwarn org.jasypt.encryption.pbe.**
-dontwarn java.beans.**
-dontwarn org.joda.time.**
-dontwarn com.google.android.gms.**
-dontwarn org.w3c.dom.bootstrap.**
-dontwarn com.ibm.icu.text.**
-dontwarn demo.**

# Hold onto the mapping.text file, it can be used to unobfuscate stack traces in the developer console using the retrace tool
-printmapping mapping.txt

# Keep line numbers so they appear in the stack trace of the develeper console
-keepattributes *Annotation*,SourceFile,LineNumberTable

-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keep class android.support.v4.app.** { *; }
-keep interface android.support.v4.app.** { *; }
-keep class com.actionbarsherlock.** { *; }
-keep interface com.actionbarsherlock.** { *; }

# https://sourceforge.net/p/proguard/discussion/182456/thread/e4d73acf
-keep class org.codehaus.** { *; }

-assumenosideeffects class android.util.Log {
  public static int d(...);
  public static int i(...);
  public static int e(...);
  public static int v(...);
}




-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

# I use Google Guava in my app
# see http://code.google.com/p/guava-libraries/wiki/UsingProGuardWithGuava

-keepclasseswithmembers class com.google.common.base.internal.Finalizer{
    <methods>;
}
-keep class okio.** { *; }
-dontwarn okio.**

-keep class com.fasterxml.** { *; }
-dontwarn com.fasterxml.**


-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*,!code/allocation/variable



-dontwarn javax.annotation.**

-dontwarn android.app.**
-dontwarn android.support.**
-dontwarn android.view.**
-dontwarn android.widget.**

-dontwarn com.google.common.primitives.**

-dontwarn **CompatHoneycomb
-dontwarn **CompatHoneycombMR2
-dontwarn **CompatCreatorHoneycombMR2


-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.* { *; }
-dontwarn com.google.errorprone.annotations.*

-keep class com.google.common.io.Resources {
    public static <methods>;
}
-keep class com.google.common.collect.Lists {
    public static ** reverse(**);
}
-keep class com.google.common.base.Charsets {
    public static <fields>;
}

-keep class com.google.common.base.Joiner {
    public static com.google.common.base.Joiner on(java.lang.String);
    public ** join(...);
}

-keep class com.google.common.collect.MapMakerInternalMap$ReferenceEntry
-keep class com.google.common.cache.LocalCache$ReferenceEntry

# http://stackoverflow.com/questions/9120338/proguard-configuration-for-guava-with-obfuscation-and-optimization
-dontwarn javax.annotation.**
-dontwarn javax.inject.**
-dontwarn sun.misc.Unsafe

# Guava 19.0
-dontwarn java.lang.ClassValue
-dontwarn com.google.j2objc.annotations.Weak
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#SnappyDB
-dontwarn sun.reflect.**
-dontwarn java.beans.**
-dontwarn sun.nio.ch.**
-dontwarn sun.misc.**

-keep class com.esotericsoftware.** {*;}

-keep class java.beans.** { *; }
-keep class sun.reflect.** { *; }
-keep class sun.nio.ch.** { *; }

-keep class com.snappydb.** { *; }
-dontwarn com.snappydb.**
#kryo
-dontwarn sun.reflect.**
-dontwarn java.beans.**
-keep,allowshrinking class com.esotericsoftware.** {
   <fields>;
   <methods>;
}
-keep,allowshrinking class java.beans.** { *; }
-keep,allowshrinking class sun.reflect.** { *; }
-keep,allowshrinking class com.esotericsoftware.kryo.** { *; }
-keep,allowshrinking class com.esotericsoftware.kryo.io.** { *; }
-keep,allowshrinking class sun.nio.ch.** { *; }
-dontwarn sun.nio.ch.**
-keep public class * extends com.google.protobuf.GeneratedMessage { *; }
-keep class de.javakaffee.kryoserializers.** { *; }
-dontwarn de.javakaffee.kryoserializers.**
-keep class com.esotericsoftware.kryo.serializers.** { *; }
-dontwarn com.esotericsoftware.kryo.serializers.**

-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.**
-dontwarn okio.**
# Ignores: can't find referenced class javax.lang.model.element.Modifier
-dontwarn com.google.errorprone.annotations.**
-keep class io.grpc.internal.DnsNameResolverProvider
-keep class io.grpc.okhttp.OkHttpChannelProvider

