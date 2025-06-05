package cn.com.huangpingpublichealth.ui

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import cn.com.huangpingpublichealth.R
import cn.com.huangpingpublichealth.base.BaseKotlinActivity
import cn.com.huangpingpublichealth.manager.DeviceManager
import cn.com.huangpingpublichealth.utils.LogUtils
import cn.com.huangpingpublichealth.utils.ToastHelper
import com.hjq.shape.view.ShapeTextView
import kotlin.math.abs

class CalibrationAngleActivity : BaseKotlinActivity(), View.OnClickListener {
    private val etRealAngle by lazy { findViewById<TextView>(R.id.et_real_angle) }
    private val etRealCapacitance by lazy { findViewById<TextView>(R.id.et_real_capacitance) }
    private val ivCalibrate by lazy { findViewById<ImageView>(R.id.iv_calibrate) }
    private val tvStepTip by lazy { findViewById<TextView>(R.id.tv_step_tip) }
    private val tvCalibrateAuto by lazy { findViewById<ShapeTextView>(R.id.tv_calibrate_auto) }
    private val tvCalibrateNext by lazy { findViewById<ShapeTextView>(R.id.tv_calibrate_next) }
    private var stepOneExecuted = false
    private var handler: Handler = Handler(Looper.getMainLooper())


    override fun getLayoutId(): Int {
        return R.layout.activity_calibration_angle
    }

    override fun title(): String {
        return getString(R.string.collect_angle)
    }

    override fun initView() {
        updateRealCapacitance()
    }

    override fun initListener() {
        tvCalibrateAuto.setOnClickListener(this)
        tvCalibrateNext.setOnClickListener(this)
//        handler.postDelayed(updateCapacitanceRunnable,500L)
    }

    private fun updateRealCapacitance() {
        LogUtils.i("updateRealCapacitance")
        etRealCapacitance.text = DeviceManager.getInstance().currentCapacitance.toString()
    }

//    private val updateCapacitanceRunnable: Runnable = object : Runnable {
//        override fun run() {
//            updateRealCapacitance()
//            handler.postDelayed(this, 500L)
//        }
//    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_calibrate_auto -> updateRealCapacitance()
            R.id.tv_calibrate_next -> executeCalibrate()
        }
    }

    private fun executeCalibrate() {
        LogUtils.i("executeCalibrate")
        if (!stepOneExecuted) {
            val angle = etRealAngle.text.toString().toFloat()
            val capacitance = etRealCapacitance.text.toString().toFloat()
            LogUtils.i("angle=$angle, capacitance=$capacitance")
            DeviceManager.getInstance().angle1 = angle
            DeviceManager.getInstance().p1 = capacitance
            updateRealCapacitance()

            etRealAngle.text = getString(R.string.text_value_ninety)
            tvStepTip.text = getString(R.string.text_step_two)
            ivCalibrate.background =
                    ResourcesCompat.getDrawable(resources, R.mipmap.icon_angle_90, null)
            tvCalibrateNext.text = getString(R.string.text_complete)
            stepOneExecuted = true
        } else {
            val angle = etRealAngle.text.toString().toFloat()
            val capacitance = etRealCapacitance.text.toString().toFloat()
            LogUtils.i("angle=$angle, capacitance=$capacitance")
            DeviceManager.getInstance().angle2 = angle
            DeviceManager.getInstance().p2 = capacitance

            if (abs(DeviceManager.getInstance().p2 - DeviceManager.getInstance().p1) < 0.1) {
                DeviceManager.getInstance().calibrateState = false
                DeviceManager.getInstance().calibrateFail()
                ToastHelper.showShort("两次测得的电容值过于接近, 校准失败")
                handler.postDelayed({ onBackPressed() }, 1500L)
                DeviceManager.getInstance().resetCalibrate()
            } else {
                DeviceManager.getInstance().calibrateState = true
                DeviceManager.getInstance().calibrateSuccess()
                ToastHelper.showShort("校准成功")
                handler.postDelayed({ onBackPressed() }, 1000L)
            }
        }
    }

}