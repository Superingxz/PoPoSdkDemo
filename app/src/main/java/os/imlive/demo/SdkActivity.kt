package os.imlive.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sdk_demo.*
import os.imlive.base.http.response.LoginResponse
import os.imlive.floating.FloatLiveManager
import os.imlive.framework.utils.ToastUtils

class SdkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdk_demo)
        login.setOnClickListener {
            FloatLiveManager.getInstance()
                .getLoginInstance(this)
                .setLoginListener { state, msg ->
                    when (state) {
                        LoginResponse.FAILED -> { // 登陆失败
                            ToastUtils.show(msg)
                        }
                        LoginResponse.AUTH_FAILED -> { // 授权失败
                            ToastUtils.show("授权失败")
                        }
                        LoginResponse.AUTH_CANCEL -> { // 授权取消
                            ToastUtils.show("授权取消")
                        }
                        LoginResponse.NEED_REGISTER -> { // 未注册
                            ToastUtils.show("未注册")
                        }
                    }
                }
                .loginSdk(this)
        }
    }
}
