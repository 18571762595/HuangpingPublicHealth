package cn.com.huangpingpublichealth.ui.dialog

import android.app.Activity
import android.app.Dialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.TextView
import cn.com.huangpingpublichealth.R
import cn.com.huangpingpublichealth.entity.Constant
import cn.com.huangpingpublichealth.manager.DeviceManager
import cn.com.huangpingpublichealth.utils.ToastHelper


fun showTimeScaleDialog(activity: Activity, settingType: Int, listen: SettingDialogListener) {
    val dialog = Dialog(activity, R.style.MyDialogStyle)
    val layoutId: Int = R.layout.layout_dialog_setting_scale
    val view = LayoutInflater.from(activity).inflate(layoutId, null)
    val etContent = view.findViewById<EditText>(R.id.tv_content)
    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
    when (settingType) {
        Constant.SETTING_TYPE_TIME_LENGTH -> {
            tvTitle.text = "显示时长/s"
            etContent.hint = "输入范围(2~5)"
        }
        Constant.SETTING_TYPE_EMG_SCALE_RANGE -> {
            tvTitle.text = "EMG刻度范围/mV"
            etContent.hint = "输入范围(1~400)"
            etContent.maxEms = 3
        }
        Constant.SETTING_TYPE_CAP_SCALE_RANGE -> {
            if (DeviceManager.getInstance().calibrateState) {
                tvTitle.text = "角度刻度范围/度"
                etContent.hint = "输入范围(-19~160)"
                etContent.maxEms = 3
            } else {
                tvTitle.text = "电容刻度范围/pF"
                etContent.hint = "输入范围(1~200)"
                etContent.maxEms = 3
            }
        }
        Constant.SETTING_TYPE_RECORD_CAPTURE_TIME -> {
            tvTitle.text = "记录采样数据/s"
            etContent.hint = "输入范围(0~1000)"
            etContent.maxEms = 3
        }
    }

    etContent.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

            val toString = s.toString()

            when (settingType) {
                Constant.SETTING_TYPE_TIME_LENGTH -> {
                    if (toString.length > 1) {
                        etContent.setText(toString.subSequence(0, 1))
                        etContent.setSelection(1)
                    }
                }
                Constant.SETTING_TYPE_EMG_SCALE_RANGE -> {
                    if (toString.length > 3) {
                        etContent.setText(toString.subSequence(0, 3))
                        etContent.setSelection(3)
                    }
                }
                Constant.SETTING_TYPE_CAP_SCALE_RANGE -> {
                    if (toString.length > 3) {
                        etContent.setText(toString.subSequence(0, 3))
                        etContent.setSelection(3)
                    }
                }
                Constant.SETTING_TYPE_RECORD_CAPTURE_TIME -> {
                    if (toString.length > 4) {
                        etContent.setText(toString.subSequence(0, 4))
                        etContent.setSelection(4)
                    }
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })

    val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
    val tvSure = view.findViewById<TextView>(R.id.tv_sure)
    tvSure.setOnClickListener {
        val toString = etContent.text.toString()
        if (toString.isEmpty()) {
            ToastHelper.showShort("请输入内容")
            return@setOnClickListener
        }
        var settingValue = 0
        try {
            settingValue = toString.toInt()
        } catch (e: Exception) {
        }

        when (settingType) {
            Constant.SETTING_TYPE_TIME_LENGTH -> {
                if (settingValue < 2 || settingValue > 5) {
                    ToastHelper.showShort("输入范围(2~5)")
                    return@setOnClickListener
                }
            }
            Constant.SETTING_TYPE_EMG_SCALE_RANGE -> {
                if (settingValue < 1 || settingValue > 400) {
                    ToastHelper.showShort("输入范围(1~400)")
                    return@setOnClickListener
                }
            }
            Constant.SETTING_TYPE_CAP_SCALE_RANGE -> {
                if (DeviceManager.getInstance().calibrateState) {
                    if (settingValue < -19 || settingValue > 160) {
                        ToastHelper.showShort("输入范围(-19~160)")
                        return@setOnClickListener
                    }
                } else {
                    if (settingValue < 1 || settingValue > 200) {
                        ToastHelper.showShort("输入范围(1~200)")
                        return@setOnClickListener
                    }
                }
            }
            Constant.SETTING_TYPE_RECORD_CAPTURE_TIME -> {
                if (settingValue < 0 || settingValue > 1000) {
                    ToastHelper.showShort("输入范围(0~1000)")
                    return@setOnClickListener
                }
            }
        }
        dialog.dismiss()
        listen.settingComplete(toString.toInt())
        ToastHelper.showShort("设置成功")
    }
    tvCancel.setOnClickListener { dialog.dismiss() }
    dialog.setCanceledOnTouchOutside(true)
    dialog.setContentView(view)
    setFullScreenDialog(dialog)
}

interface SettingDialogListener {
    fun settingComplete(settingValue: Int)
}