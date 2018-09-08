package cn.sskbskdrin.log.demo;

import cn.sskbskdrin.log.Format;
import cn.sskbskdrin.log.L;
import cn.sskbskdrin.log.console.ConsolePrinter;

public class Main {
    public static void main(String[] args) {
        L.clearPrinters();
        L.enableJsonOrXml(false, true);
        L.tag("","");
        L.addPinter(new ConsolePrinter(new Format() {
            @Override
            public String formatTag(int priority, String tag) {
                return tag;
            }

            @Override
            public String format(String msg) {
                return msg;
            }
        }));
        L.d("[{\"a\":234},{\"b\":543}]");
        L.d("<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<FrameLayout " +
                "xmlns:android=\"http://schemas.android.com/apk/res/android\"" + "             "
                + "android:layout_width=\"match_parent\"" + "             " +
                "android:layout_height=\"match_parent\">" + "" + "    <TextView" + "        " +
                "android:layout_width=\"wrap_content\"" + "        " +
                "android:layout_height=\"wrap_content\"" + "        android:text=\"Hello " +
                "World!\"/>" + "" + "</FrameLayout>");
        L.d("<?xml version=\"1.0\" encoding=\"UTF-8\"?> " + "<employees>" + "<employee> "
                + "<name>ddviplinux</name> " + "<sex>m</sex> " + "<age>30</age> " +
                "</employee> " + "</employees>");
    }

}
