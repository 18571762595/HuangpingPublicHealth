package cn.com.huangpingpublichealth.ui

import androidx.recyclerview.widget.RecyclerView
import cn.com.huangpingpublichealth.R
import cn.com.huangpingpublichealth.base.BaseKotlinActivity
import cn.com.huangpingpublichealth.entity.SettingParamsBean
import cn.com.huangpingpublichealth.ui.adapter.SettingParamsAdapter
import cn.com.huangpingpublichealth.utils.spaces_item_decoration.RecyclerViewUtils

class SettingParamsActivity : BaseKotlinActivity() {
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recycler_view) }

    override fun getLayoutId(): Int {
        return R.layout.activity_setting_params
    }

    override fun title(): String {
        return getString(R.string.setting_params)
    }

    override fun initView() {
        RecyclerViewUtils.setRecyclerViewDivider(recyclerView, this, R.drawable.divider_tran_shape_8dp)

        val paramsAdapter = SettingParamsAdapter()
        recyclerView.adapter = paramsAdapter
        paramsAdapter.datas = SettingParamsBean.getInstance().settingBeans
    }
}