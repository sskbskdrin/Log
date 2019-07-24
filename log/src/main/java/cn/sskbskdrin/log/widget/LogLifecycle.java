package cn.sskbskdrin.log.widget;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by ex-keayuan001 on 2019-07-24.
 *
 * @author ex-keayuan001
 */
class LogLifecycle implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "LogLifecycle";
    private int showCount = 0;

    private static LogLifecycle instance;

    static LogLifecycle getInstance() {
        if (instance == null) {
            instance = new LogLifecycle();
        }
        return instance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "onActivityStarted: ");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        LogWidget.getInstance().attach(activity);
        showCount++;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        showCount--;
    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (showCount <= 0) {
            LogWidget.destroy();
        }
    }
}
