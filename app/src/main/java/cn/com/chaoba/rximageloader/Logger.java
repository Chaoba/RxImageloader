package cn.com.chaoba.rximageloader;

import android.util.Log;

/**
 * A class to control log show and save it to sdcard.
 *
 * @author Liyanshun
 */
public class Logger {
    private static final String TAG = "Logger";
    /**
     * whether show log in logcat.
     */
    private static boolean mShowLogInLogCat = BuildConfig.DEBUG;
    private static String CLASS_NAME = null;

    private static String getFunctionName() {
        if (CLASS_NAME == null) {
            CLASS_NAME = Logger.class.getName();
        }
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        if (sts == null) {
            return null;
        }
        for (StackTraceElement st : sts) {
            if (st.isNativeMethod()) {
                continue;
            }
            if (st.getClassName().equals(Thread.class.getName())) {
                continue;
            }
            if (st.getClassName().equals(CLASS_NAME)) {
                continue;
            }
            return st.getFileName() + "[Line: " + st.getLineNumber() + "] ";
        }
        return null;
    }


    public static void i(String tag, Object message) {
        String name = getFunctionName();
        if (mShowLogInLogCat) {
            if (name == null) {
                Log.i(tag, message.toString());
            } else {
                Log.i(tag, name + message.toString());
            }
        }
    }

    public static void i(Object message) {
        i(TAG, message);
    }

    public static void d(String tag, Object message) {
        String name = getFunctionName();
        if (mShowLogInLogCat) {
            if (name == null) {
                Log.d(tag, message.toString());
            } else {
                Log.d(tag, name + message.toString());
            }
        }
    }

    public static void d(Object message) {
        d(TAG, message);
    }

    public static void v(String tag, Object message) {
        String name = getFunctionName();
        if (mShowLogInLogCat) {
            if (name == null) {
                Log.v(tag, message.toString());
            } else {
                Log.v(tag, name + message.toString());
            }
        }
    }

    public static void v(Object message) {
        v(TAG, message);
    }

    public static void w(String tag, Object message) {
        String name = getFunctionName();
        if (mShowLogInLogCat) {
            if (name == null) {
                Log.w(tag, message.toString());
            } else {
                Log.w(tag, name + message.toString());
            }
        }
    }

    public static void w(Object message) {
        w(TAG, message);
    }

    public static void e(String tag, Object message) {
        String name = getFunctionName();
        if (mShowLogInLogCat) {
            if (name == null) {
                Log.e(tag, message.toString());
            } else {
                Log.e(tag, name + message);
            }
        }
    }

    public static void e(Object message) {
        e(TAG, message);
    }

    public static void e(String tag, Exception e) {
        String name = getFunctionName();
        if (mShowLogInLogCat) {
            if (name == null) {
                Log.e(tag, e.getMessage());
            } else {
                Log.e(tag, name + e.getMessage());
            }
        }
    }

    public static void e(Exception e) {
        e(TAG, e);
    }


}
