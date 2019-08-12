package cn.sskbskdrin.log.demo;

import android.app.Activity;
import android.os.Bundle;

import cn.sskbskdrin.log.SSKLog;
import cn.sskbskdrin.log.disk.DiskPrinter;
import cn.sskbskdrin.log.logcat.LogcatPrinter;
import cn.sskbskdrin.log.logcat.PrettyFormat;
import cn.sskbskdrin.log.widget.LogWidget;

public class MainActivity extends Activity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogWidget.init(getApplication());

        final SSKLog L = new SSKLog();

        L.tag("ayke", "");
        L.addPinter(LogWidget.getPrinter());
        L.addPinter(new LogcatPrinter(new PrettyFormat()).setNew(true));
        L.addPinter(new DiskPrinter(getExternalFilesDir("log").getAbsolutePath()));
        findViewById(android.R.id.content).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count < 0) {
                    return;
                }
                findViewById(android.R.id.content).postDelayed(this, 1000);
                L.w("","test" + count--);
                L.d("","{}","<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "<FrameLayout " + "xmlns:android=\"http" +
                    "://schemas" +
                    ".android" + ".com/apk/res/android\"" + "             " + "android:layout_width=\"match_parent\"" + "             " + "android:layout_height=\"match_parent\">" + "" + "    <TextView" + "        " + "android:layout_width" + "=\"wrap_content\"" + "        " + "android:layout_height=\"wrap_content\"" + "        " + "android:text=\"Hello " + "World!\"/>" + "" + "</FrameLayout>");
            }
        }, 1000);
        L.d("wwwww","\nwwwwwrtyuio");
        L.enableJsonOrXml(true, true);
        L.d("","sskbskdrin{}","[{\"a\":234},{\"b\":543}]");
        L.e("", "null ", new NullPointerException());
    }
}
