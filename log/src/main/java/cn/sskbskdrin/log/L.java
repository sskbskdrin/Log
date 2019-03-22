package cn.sskbskdrin.log;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
    private static int INDENT = 2;

    private static boolean enableJson = false;
    private static boolean enableXML = false;

    private static LogHelper helper = new LoggerHelper();
    private static StringBuilder builder = new StringBuilder();

    private static boolean JSON = false;

    static {
        try {
            JSON = Class.forName("org.json.JSONObject") != null;
        } catch (ClassNotFoundException e) {
        }
    }

    private L() {
    }

    /**
     * 设置全局tag
     *
     * @param globalTag  全局tag
     * @param defaultTag 默认tag
     */
    public static void tag(String globalTag, String defaultTag) {
        if (helper != null) {
            helper.tag(globalTag);
        }
        if (defaultTag != null) {
            DEFAULT_TAG = defaultTag;
        }
    }

    /**
     * 设置自定义helper
     *
     * @param helper 自定义LogHelper
     */
    public static void helper(LogHelper helper) {
        L.helper = helper;
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
        if (helper != null && printers != null) {
            for (Printer printer : printers) {
                helper.addPrinter(printer);
            }
        }
    }

    /**
     * 清除所有打印者
     */
    public static void clearPrinters() {
        if (helper != null) {
            helper.clearAdapters();
        }
    }

    public static void enableJsonOrXml(boolean json, boolean xml) {
        if (json) {
            try {
                Class.forName("org.json.JSONObject");
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("环境不支持 json, 没有找到org.json.JSONObject");
            }
        }
        enableJson = json;
        enableXML = xml;
    }

    public static void indent(int indent) {
        INDENT = indent;
    }

    /**
     * 追加打印内容，将同下一次打印一起被输出
     *
     * @param msg 追加内容
     */
    public static void append(String msg) {
        if (msg != null) {
            String temp = null;
            if (enableJson && JSON) {
                temp = Json.json(msg, INDENT);
            }
            if (enableXML && temp == null) {
                temp = xml(msg, INDENT);
            }
            if (temp != null) {
                msg = temp;
            }
        }
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
            append(msg);
            msg = builder.toString();
            builder.setLength(0);
            helper.log(level, tag, msg, e);
        }
    }

    private static String xml(String xml, int indent) {
        xml = xml.trim();
        try {
            if (xml.startsWith("<")) {
                Source xmlInput = new StreamSource(new StringReader(xml));
                StreamResult xmlOutput = new StreamResult(new StringWriter());
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.METHOD, "html");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", String.valueOf(indent));
                transformer.transform(xmlInput, xmlOutput);
                return "\n" + xmlOutput.getWriter().toString() + "\n";
            }
        } catch (TransformerException ignored) {
        }
        return null;
    }
}
