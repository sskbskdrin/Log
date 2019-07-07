package cn.sskbskdrin.log.widget;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author sskbskdrin
 * @date 2019-07-07
 */
final class Log {
    private static final String[] LEVEL = {"V", "D", "I", "W", "E", "A"};
    private static final int[] COLOR = {0xFF6F7365, 0XFF3578D4, 0XFF11BB2F, 0XFFFFB157, 0XFFCA3A33, 0XFFFF0000};

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss.SSS", Locale.US);

    private static final Date date = new Date();

    final int priority;

    final String tag;

    final String content;
    final int size;

    boolean isRemove = false;

    Log(int level, String tag, String message) {
        if (level < 2) {
            priority = 0;
        } else if (level > 7) {
            priority = 5;
        } else {
            priority = level - 2;
        }
        this.tag = tag;
        date.setTime(System.currentTimeMillis());
        content = dateFormat.format(date) + " " + LEVEL[priority] + "/" + tag + ": " + message;
        size = content.getBytes().length;
    }

    int color() {
        return COLOR[priority];
    }

    void markRemove() {
        isRemove = true;
    }

    @Override
    public String toString() {
        return content;
    }
}
