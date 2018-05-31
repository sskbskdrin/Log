package cn.sskbskdrin.log.logcat;

import android.util.Log;

import cn.sskbskdrin.log.Format;
import cn.sskbskdrin.log.Printer;

public class LogcatPrinter implements Printer {

    private final Format format;
    private boolean isNew = true;

    public LogcatPrinter() {
        this.format = new PrettyFormat();
    }

    public LogcatPrinter(Format formatStrategy) {
        this.format = formatStrategy;
    }

    public LogcatPrinter setNew(boolean isNew) {
        this.isNew = isNew;
        return this;
    }

    @Override
    public boolean isLoggable(int priority, String tag) {
        return true;
    }

    @Override
    public synchronized void log(int priority, String tag, String message) {
        if (format != null) {
            tag = format.formatTag(priority, tag);
            message = format.format(message);
        }
        if (isNew) {
            Log.println(priority, tag, " " + System.getProperty("line.separator") + message);
        } else {
            String[] result = message.split(System.getProperty("line.separator"));
            for (String msg : result) {
                Log.println(priority, tag, msg);
            }
        }
    }
}
