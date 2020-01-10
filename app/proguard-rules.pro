#https://github.com/square/okhttp/issues/3922
-dontwarn okhttp3.internal.platform.*
-dontwarn org.conscrypt.*

#shrink debug log
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** e(...);
    public static *** i(...);
}

-assumenosideeffects class java.lang.Throwable {
    public void printStackTrace();
}

-assumenosideeffects class kotlin.io {
    public void print(...);
    public void println(...);
}