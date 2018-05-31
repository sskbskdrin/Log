package cn.sskbskdrin.log.logcat;

import cn.sskbskdrin.log.Format;

/**
 * Created by ex-keayuan001 on 2018/5/31.
 *
 * @author ex-keayuan001
 */
public class NormalFormat implements Format {
    @Override
    public String formatTag(int priority, String tag) {
        return tag;
    }

    @Override
    public String format(String msg) {
        return msg;
    }
}
