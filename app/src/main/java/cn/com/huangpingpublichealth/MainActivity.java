package cn.com.huangpingpublichealth;

import android.content.Intent;

import cn.com.huangpingpublichealth.base.BaseKotlinActivity;
import cn.com.huangpingpublichealth.ui.FileSelectorActivity;
import cn.com.huangpingpublichealth.utils.LogUtils;
import cn.com.huangpingpublichealth.utils.PermissionHelper;

public class MainActivity extends BaseKotlinActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        boolean hasPermission = PermissionHelper.hasPermission(this);
        LogUtils.i("hasPermission=" + hasPermission);
        findViewById(R.id.tv_go_to_file).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FileSelectorActivity.class);
            startActivity(intent);
        });
    }
}