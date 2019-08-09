package cn.sskbskdrin.log;

import android.util.Log;

/**
 * Created by ex-keayuan001 on 2019-08-09.
 *
 * @author ex-keayuan001
 */
public class SLog {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    private static String DEFAULT_TAG = "DEFAULT_TAG";

    public static void main(String[] args) {
        new SLog().v("abc","");
    }

    public void v(String tag, String msg) {
        println(VERBOSE, tag, msg);
    }

    public void v(String msg, Object... obj) {
        println(VERBOSE, DEFAULT_TAG, msg);
    }

    public void d(String msg) {
        println(DEBUG, DEFAULT_TAG, msg);
    }

    public void i(String msg) {
        println(INFO, DEFAULT_TAG, msg);
    }

    public void w(String msg) {
        println(WARN, DEFAULT_TAG, msg);
    }

    public void w(String msg, Throwable e) {
        println(WARN, DEFAULT_TAG, msg, e);
    }

    public void e(String msg) {
        println(ERROR, DEFAULT_TAG, msg);
    }

    public void e(String msg, Throwable e) {
        println(ERROR, DEFAULT_TAG, msg, e);
    }


    public void d(String tag, String msg) {
        println(DEBUG, tag, msg);
    }

    public void i(String tag, String msg) {
        println(INFO, tag, msg);
    }

    public void w(String tag, String msg) {
        println(WARN, tag, msg);
    }

    public void w(String tag, String msg, Throwable e) {
        println(WARN, tag, msg, e);
    }

    public void e(String tag, String msg) {
        println(ERROR, tag, msg);
    }

    public void e(String tag, String msg, Throwable e) {
        println(ERROR, tag, msg, e);
    }

    private void println(int priority, String tag, String msg) {
        println(priority, tag, msg, null);
    }

    private void println(int priority, String tag, String msg, Object... obj) {
        Log.println(priority, tag, msg);
    }

}
