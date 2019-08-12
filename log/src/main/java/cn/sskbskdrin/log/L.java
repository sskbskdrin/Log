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

    private static final SSKLog log = new SSKLog();

    private L() {
    }

    /**
     * 设置全局tag
     *
     * @param globalTag  全局tag
     * @param defaultTag 默认tag
     */
    public static void tag(String globalTag, String defaultTag) {
        log.tag(globalTag, defaultTag);
    }

    /**
     * 添加打印者
     * <ul>
     * <li>Different {@link cn.sskbskdrin.log.logcat.LogcatPrinter}</li>
     * <li>Different {@link cn.sskbskdrin.log.disk.DiskPrinter}</li>
     * <li>Different {@link cn.sskbskdrin.log.console.ConsolePrinter}</li>
     * </ul>
     *
     * @param printers 打印者
     * @see cn.sskbskdrin.log.logcat.LogcatPrinter
     * @see cn.sskbskdrin.log.disk.DiskPrinter
     * @see cn.sskbskdrin.log.console.ConsolePrinter
     */
    public static void addPinter(Printer... printers) {
        log.addPinter(printers);
    }

    /**
     * 清除所有打印者
     */
    public static void clearPrinters() {
        log.clearPrinters();
    }

    public static void enableJsonOrXml(boolean json, boolean xml) {
        log.enableJsonOrXml(json, xml);
    }

    public static void v(String msg) {
        log.v(null, msg);
    }

    public static void d(String msg) {
        log.d(null, msg);
    }

    public static void i(String msg) {
        log.i(null, msg);
    }

    public static void w(String msg) {
        log.w(null, msg);
    }

    public static void w(String msg, Throwable e) {
        log.w(null, msg, e);
    }

    public static void e(String msg) {
        log.e(null, msg);
    }

    public static void e(String msg, Throwable e) {
        log.e(null, msg, e);
    }

    public static void v(String tag, String msg, Object... obj) {
        log.v(tag, msg);
    }

    public static void d(String tag, String msg, Object... obj) {
        log.d(tag, msg, obj);
    }

    public static void i(String tag, String msg, Object... obj) {
        log.i(tag, msg, obj);
    }

    public static void w(String tag, String msg, Object... obj) {
        log.w(tag, msg, obj);
    }

    public static void e(String tag, String msg, Object... obj) {
        log.e(tag, msg, obj);
    }

}
