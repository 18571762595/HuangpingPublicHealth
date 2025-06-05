package cn.com.huangpingpublichealth.app;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

import cn.com.huangpingpublichealth.utils.LogUtils;
import cn.com.huangpingpublichealth.utils.PathUtils;

public class ProjectApplication extends Application {
    private static ProjectApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Utils.init(this);
        PathUtils.initPathConfig(this);
        LogUtils.LOG_PATH = getExternalFilesDir(null).getPath() + "/logs";
    }

    public static ProjectApplication getApp() {
        return application;
    }
}
