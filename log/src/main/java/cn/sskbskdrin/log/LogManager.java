package cn.sskbskdrin.log;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import cn.sskbskdrin.log.console.ConsolePrinter;
import cn.sskbskdrin.log.logcat.LogcatPrinter;

class LogManager implements LogHelper {

    private String globalTag;

    private final Set<Printer> logPrinters = new HashSet<>();
    private Printer defaultPrint;

    private static boolean logcat;
    private boolean useGlobalTag;
    private ReentrantLock lock = new ReentrantLock(true);

    LogManager() {
        try {
            logcat = Class.forName("android.util.Log") != null;
        } catch (ClassNotFoundException ignored) {
        }
        defaultPrint = logcat ? new LogcatPrinter() : new ConsolePrinter();
        logPrinters.add(defaultPrint);
    }

    @Override
    public void tag(String tag) {
        globalTag = tag;
        useGlobalTag = globalTag != null && globalTag.length() > 0;
    }

    @Override
    public void log(int priority, String tag, String message, Object... obj) {
        if (obj != null && obj.length > 0) {
            if (message == null) {
                message = "";
            }

            int maxLength = obj.length;
            String[] mm = message.split("\\{}");
            if (mm.length > maxLength) {
                maxLength = mm.length;
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < maxLength; i++) {
                if (i < mm.length) {
                    builder.append(mm[i]);
                }
                if (i < obj.length) {
                    Utils.objToString(builder, obj[i]);
                }
            }
            message = builder.toString();
        }

        if (message == null || message.length() == 0) {
            message = "Empty/NULL log message";
        }
        if (useGlobalTag) {
            if (tag != null && tag.length() > 0) {
                message = tag + ": " + message;
            }
            tag = globalTag;
        }
        lock.lock();
        try {
            for (Printer printer : logPrinters) {
                printer.log(priority, tag, message);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clearAdapters() {
        logPrinters.clear();
    }

    @Override
    public void addPrinter(Printer printer) {
        if (!logcat && printer instanceof LogcatPrinter) {
            throw new IllegalArgumentException("环境不支持 logcat, 没有找到android.util.Log");
        }
        if (defaultPrint != null) {
            logPrinters.remove(defaultPrint);
            defaultPrint = null;
        }
        logPrinters.add(printer);
    }
}
