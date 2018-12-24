package cn.sskbskdrin.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

class LoggerHelper implements LogHelper {

    private String localTag = "";

    private final Set<Printer> logPrinters = new HashSet<>();

    @Override
    public void tag(String tag) {
        localTag = tag;
    }

    @Override
    public synchronized void log(int priority, String tag, String message, Throwable throwable) {
        if (throwable != null) {
            if (message == null) {
                message = "";
            }
            message += "\n" + getStackTraceString(throwable);
        }
        if (message == null || message.length() == 0) {
            message = "Empty/NULL log message";
        }
        if (useGlobalTag()) {
            if (tag != null && tag.length() > 0) {
                message = tag + ": " + message;
            }
            tag = localTag;
        }
        for (Printer printer : logPrinters) {
            printer.log(priority, tag, message);
        }
    }

    @Override
    public void clearAdapters() {
        logPrinters.clear();
    }

    @Override
    public boolean useGlobalTag() {
        return localTag != null && localTag.length() > 0;
    }

    @Override
    public void addPrinter(Printer printer) {
        if (!logPrinters.contains(printer)) {
            logPrinters.add(printer);
        }
    }

    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

}
