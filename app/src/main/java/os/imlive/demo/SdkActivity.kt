package os.imlive.demo

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sdk_demo.*
import os.imlive.base.http.response.LoginResponse
import os.imlive.sdk.FloatLiveManager

/**
 * <pre>
 *     author: myz
 *     email : moyaozhi@imdawei.com
 *     time  : 2021/4/12 12:16
 *     desc  : SDKDemo
 * </pre>
 */
class SdkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdk_demo)
        // 登录SDK前需要调用
        FloatLiveManager.getInstance().getLoginInstance(this)
            .getThirdAdviceParams()
        initViews()
    }

    private fun initViews() {
        backImg.setOnClickListener {
            finish()
        }
        login.setOnClickListener {
            if (TextUtils.isEmpty(tokenEt.text.toString())) {
                ToastKit.show(this, R.string.value_token_input)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(userIdEt.text.toString())) {
                ToastKit.show(this, R.string.value_user_id_input)
                return@setOnClickListener
            }
            FloatLiveManager.getInstance()
                .getLoginInstance(this)
                .setLoginListener { state, msg ->
                    when (state) {
                        LoginResponse.FAILED -> { // 登陆失败
                            ToastKit.show(this, msg)
                        }
                        LoginResponse.AUTH_FAILED -> { // 授权失败
                            ToastKit.show(this, R.string.string_auth_failed)
                        }
                        LoginResponse.AUTH_CANCEL -> { // 授权取消
                            ToastKit.show(this, R.string.string_auth_cancel)
                        }
                        LoginResponse.NEED_REGISTER -> { // 未注册
                            ToastKit.show(this, R.string.string_need_register)
                        }
                        LoginResponse.TOKEN_AUTH_FAILED -> { // token校验失效
                        }
                    }
                }
                .loginSdk(this, tokenEt.text.toString(), userIdEt.text.toString())
        }
    }
}
