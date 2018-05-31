package cn.sskbskdrin.log;

/**
 * <pre>
 *  ┌────────────────────────────────────────────
 *  │ LOGGER
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ Standard logging mechanism
 *  ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
 *  │ But more pretty, simple and powerful
 *  └────────────────────────────────────────────
 * </pre>
 * <h3>How to use it</h3>
 * Initialize it first
 * <pre><code>
 *   L.addPinter(new LogcatPrinter());
 * </code></pre>
 * <p>
 * And use the appropriate static L methods.
 * </p>
 * <pre><code>
 *   L.d("debug");
 *   L.e("error");
 *   L.w("warning");
 *   L.v("verbose");
 *   L.i("information");
 * </code></pre>
 * <h3>Json and Xml support (output will be in debug level)</h3>
 * <h3>Customize L</h3>
 * Based on your needs, you can change the following settings:
 * <ul>
 * <li>Different {@link Printer}</li>
 * <li>Different {@link Format}</li>
 * <li>Different {@link LogStrategy}</li>
 * </ul>
 *
 * @see Printer
 * @see Format
 * @see LogStrategy
 */
public final class L {

    public static final int VERBOSE = 2;
    public static final int DEBUG = 3;
    public static final int INFO = 4;
    public static final int WARN = 5;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;

    private static String DEFAULT_TAG = "DEFAULT_TAG";

    private static LogHelper helper = new LoggerHelper();
    private static StringBuilder builder = new StringBuilder();

    private L() {
    }

    public static void tag(String tag, String defaultTag) {
        if (helper != null) {
            helper.tag(tag);
        }
        if (defaultTag != null) {
            DEFAULT_TAG = defaultTag;
        }
    }

    public static void helper(LogHelper helper) {
        L.helper = helper;
    }

    public static void addPinter(Printer printer) {
        if (helper != null) {
            helper.addAdapter(printer);
        }
    }

    public static void clearPrinters() {
        if (helper != null) {
            helper.clearAdapters();
        }
    }

    public static void append(String msg) {
        builder.append(msg);
    }

    public static void v(String msg) {
        println(VERBOSE, DEFAULT_TAG, msg);
    }

    public static void d(String msg) {
        println(DEBUG, DEFAULT_TAG, msg);
    }

    public static void i(String msg) {
        println(INFO, DEFAULT_TAG, msg);
    }

    public static void w(String msg) {
        println(WARN, DEFAULT_TAG, msg);
    }

    public static void e(String msg) {
        println(ERROR, DEFAULT_TAG, msg);
    }

    public static void e(String msg, Throwable e) {
        println(ERROR, DEFAULT_TAG, msg, e);
    }

    public static void v(String tag, String msg) {
        println(VERBOSE, tag, msg);
    }

    public static void d(String tag, String msg) {
        println(DEBUG, tag, msg);
    }

    public static void i(String tag, String msg) {
        println(INFO, tag, msg);
    }

    public static void w(String tag, String msg) {
        println(WARN, tag, msg);
    }

    public static void e(String tag, String msg) {
        println(ERROR, tag, msg);
    }

    public static void e(String tag, String msg, Throwable e) {
        println(ERROR, tag, msg);
    }

    private static void println(int level, String tag, String msg) {
        println(level, tag, msg, null);
    }

    private static void println(int level, String tag, String msg, Throwable e) {
        if (helper != null) {
            if (builder.length() > 0) {
                msg = builder.append(msg).toString();
                builder.setLength(0);
            }
            helper.log(level, tag, msg, e);
        }
    }
}
