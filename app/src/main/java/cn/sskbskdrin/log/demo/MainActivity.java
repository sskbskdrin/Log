package cn.sskbskdrin.log.demo;

import android.app.Activity;
import android.os.Bundle;

import cn.sskbskdrin.log.L;
import cn.sskbskdrin.log.disk.DiskLogStrategy;
import cn.sskbskdrin.log.disk.DiskPrinter;
import cn.sskbskdrin.log.logcat.LogcatPrinter;

public class MainActivity extends Activity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        L.tag("ayke", "");
        L.addPinter(new LogcatPrinter().setNew(true));
        L.addPinter(new DiskPrinter(new DiskLogStrategy(getExternalFilesDir("log").getAbsolutePath())));
        findViewById(android.R.id.content).postDelayed(new Runnable() {
            @Override
            public void run() {
                L.w("test" + count++);
                findViewById(android.R.id.content).postDelayed(this, 1000);
            }
        }, 1000);
        L.append("wwwww");
        L.append("\nwwwwwrtyuio");
        L.append("\nwwwwwfsdfs");
        L.d("56789876");
        L.e("null ", new NullPointerException());
    }
}
