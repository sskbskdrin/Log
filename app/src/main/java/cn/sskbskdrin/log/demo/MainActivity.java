package cn.sskbskdrin.log.demo;

import android.app.Activity;
import android.os.Bundle;

import cn.sskbskdrin.log.Logger;
import cn.sskbskdrin.log.disk.DiskLogStrategy;
import cn.sskbskdrin.log.disk.DiskPrinter;
import cn.sskbskdrin.log.logcat.LogcatPrinter;

public class MainActivity extends Activity {

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.tag("ayke", "");
        Logger.addPinter(new LogcatPrinter().setNew(true));
        Logger.addPinter(new DiskPrinter(new DiskLogStrategy(getExternalFilesDir("log").getAbsolutePath()), null));
        findViewById(android.R.id.content).postDelayed(new Runnable() {
            @Override
            public void run() {
                Logger.w("test" + count++);
                findViewById(android.R.id.content).postDelayed(this, 1000);
            }
        }, 1000);
    }
}
