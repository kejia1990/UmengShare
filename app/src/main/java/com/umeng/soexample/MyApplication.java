package com.umeng.soexample;

import android.app.Application;
import android.util.Log;

import com.umeng.socialize.PlatformConfig;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by HouZhiguo on 17/11/23.
 */

public class MyApplication extends Application {

    public static final String TAG = "UMENG_SHARE";

    @Override
    public void onCreate() {
        super.onCreate();

        initUmengShare();
        catchCrush();
    }

    private void initUmengShare() {
        {
            PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
            //豆瓣RENREN平台目前只能在服务器端配置
            // PlatformConfig.setSinaWeibo("449725355", "079af290ee164b811efe01a15a93194a", "http://sns.whalecloud.com");
            PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
            PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        }
    }

    private void catchCrush() {

        // 捕获 app crush
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, final Throwable ex) {
                final StringBuilder sb = new StringBuilder();
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                ex.printStackTrace(pw);
                sb.append(sw.toString());
                // MyLog.openLog(MyLog.LEVEL_TRACE);
                Log.e(TAG, "Catch Exception:" + sb.toString());
                // MyLog.closeLog();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }



}
