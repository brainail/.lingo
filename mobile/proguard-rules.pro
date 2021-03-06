### Specifies to write out some more information during processing.
-verbose

### Specifies not to generate mixed-case class names while obfuscating.
-dontusemixedcaseclassnames

### Uncomment if using annotations to keep them.
-keepattributes *Annotation*

### Js
-keepattributes JavascriptInterface

### Specifies the number of optimization passes to be performed.
-optimizationpasses 5

### Specifies to repackage all class files that are renamed,
### by moving them into the single given package.
-repackageclasses ''

### Disable some simplifications, modifications for fields & classes merging process
-optimizations !code/simplification/arithmetic
-optimizations !code/simplification/cast
-optimizations !field/*
-optimizations !class/merging/*
-optimizations !code/allocation/variable

### Specifies the optimizations to be enabled and disabled.
# -optimizations code/simplification/*

### Performs peephole optimizations for arithmetic instructions (disabled) & casting (disabled)
# -optimizations !code/simplification/arithmetic
# -optimizations !code/simplification/cast

### Removes dead code, unused variables, ...
# -optimizations code/removal/*

### Inlines short methods, methods that are only called once, ...
# -optimizations method/inlining/*

### Marks methods as static, whenever possible.
# -optimizations method/marking/static

### Removes write-only fields.
# -optimizations field/removal/writeonly

### Removes unused method parameters.
# -optimizations method/removal/parameter

### Specifies not to ignore non-public library classes.
-dontskipnonpubliclibraryclasses

### Specifies that the access modifiers of classes and class members may be broadened during processing.
-allowaccessmodification

### Specifies not to preverify the processed class files.
### By default, class files are preverified if they are targeted at Java Micro Edition or at Java 6 or higher.
### For Java Micro Edition, preverification is required, ...
### For Java 6, preverification is optional, but as of Java 7, it is required.
### Only when eventually targeting Android, it is not necessary,
### so you can then switch it off to reduce the processing time a bit.
-dontpreverify

### Specifies to keep the parameter names and types of methods that are kept.
-keepparameternames

### We should at least keep the Exceptions, InnerClasses,
### and Signature attributes when processing a library.
-keepattributes Exceptions,InnerClasses,Signature

### We should also keep the SourceFile and LineNumberTable attributes
### for producing useful obfuscated stack traces.
-keepattributes SourceFile,LineNumberTable

### Some code may make further use of introspection
### to figure out the enclosing methods of anonymous inner classes
-keepattributes EnclosingMethod

### To keep the "Deprecated" attribute.
-keepattributes Deprecated

### Specifies a constant string to be put
### in the SourceFile attributes (and SourceDir attributes) of the class files.
-renamesourcefileattribute SourceFile

### Explicitly preserve all serialization members. The Serializable interface
### is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

### To maintain custom components names that are used on layouts XML.
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

### keep setters in Views so that animations can still work.
### see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

### Context specific
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.content.Context {
   public void *(android.view.View);
   public void *(android.view.MenuItem);
}

### We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

### Preserve static fields of inner classes of R classes
### that might be accessed through introspection.
-keepclassmembers class **.R$* {
    public static <fields>;
}

### To keep main things for parcelable objects.
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

### To keep the interface by AIDL.
-keepclasseswithmembernames class * implements android.os.IInterface {
    public <methods>;
}

### Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

### Keep classes that are referenced on the AndroidManifest.
-keep public class * extends android.app.Application
-keep public class * extends android.app.Activity
-keep public class * extends android.view.ViewGroup
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.Service
-keep public class * implements android.os.IInterface
-keep public class * extends android.content.BroadcastReceiver
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

### To remove debug logs.
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int w(...);
    public static int d(...);
    public static int i(...);
    public static int e(...);
}

### To remove prints.
-assumenosideeffects public class java.io.PrintStream {
    public void println;
    public void print(**);
    public void println(**);
}

### To remove output errors.
-assumenosideeffects public class java.lang.Throwable {
    public void printStackTrace();
    public java.lang.String toString();
    public java.lang.String getMessage();
}

### System
-assumenosideeffects public class java.lang.System {
    public static native long currentTimeMillis();
    static java.lang.Class getCallerClass();
    public static native int identityHashCode(java.lang.Object);
}

### Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclassmembernames class * {
    java.lang.Class class$(java.lang.String);
    java.lang.Class class$(java.lang.String, boolean);
}

### Google Play Services
### See: https://developer.android.com/google/play-services/setup.html#Proguard
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

### Google specific
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

### Parcelable objects (IPC, intents, ...)
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

### Needed by google-http-client to keep generic types and @Key annotations accessed via reflection
-keepclassmembers class * {
  @com.google.api.client.util.Key <fields>;
}

### The support library contains references to newer platform versions.
### Don't warn about those in case this app is linking against an older
### platform version.  We know about them, and they are safe.
-dontwarn android.support.**

### Warnings from Gps
-dontwarn sun.misc.**

### Crashlytics
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-dontwarn com.crashlytics.**

### Kotlin
-dontwarn org.w3c.dom.events.**
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
}

### Support/AndroidX
-keep public class android.support.design.R$* { *; }
# Support design > CoordinatorLayout resolves the behaviors of its child components with reflection.
-keep public class * extends androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior { *; }
# Keep drawable.* to fix problems with animated vector drawable
-keep class androidx.core.graphics.drawable.** { *; }

### JsBridge
# Keeping Javascript interfaces
-keep public interface android.webkit.JavascriptInterface { *; }
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

### OkHttp 3.X
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

### Retrofit
-dontnote retrofit2.**

### LeakCanary
# -keep class org.eclipse.mat.** { *; }
# -keep class com.squareup.leakcanary.** { *; }


