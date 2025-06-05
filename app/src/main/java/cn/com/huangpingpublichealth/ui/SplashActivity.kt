package cn.com.huangpingpublichealth.ui

import android.content.Intent
import android.os.Handler
import cn.com.huangpingpublichealth.R
import cn.com.huangpingpublichealth.base.BaseKotlinActivity

class SplashActivity : BaseKotlinActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(getActivity(), InformationCollectionActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }
}