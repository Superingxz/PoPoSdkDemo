package os.imlive.demo

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sdk_demo.*
import os.imlive.base.data.model.manager.UserManager
import os.imlive.base.http.response.LoginResponse
import os.imlive.base.widget.dialog.CommDialog
import os.imlive.sdk.FloatLiveManager
import os.imlive.sdk.util.FloatLiveTools

/**
 * <pre>
 *     author: myz
 *     email : moyaozhi@imdawei.com
 *     time  : 2021/4/12 12:16
 *     desc  : SDKDemo
 * </pre>
 */
class DemoKtActivity : AppCompatActivity() {
    private var commDialog: CommDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sdk_demo)
        initViews()
    }

    private fun initViews() {
        tokenEt.setText(UserManager.getInstance().myThirdToken)
        if (UserManager.getInstance().thirdUid != 0L) {
            thirdUidEt.setText(UserManager.getInstance().thirdUid.toString())
        }
        backImg.setOnClickListener {
            finish()
        }
        login.setOnClickListener {
            if (TextUtils.isEmpty(tokenEt.text.toString())) {
                ToastKit.show(this, R.string.sdk_input_token)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(thirdUidEt.text.toString())) {
                ToastKit.show(this, R.string.sdk_input_user_id)
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
                            ToastKit.show(this, R.string.sdk_string_auth_failed)
                        }
                        LoginResponse.AUTH_CANCEL -> { // 授权取消
                            ToastKit.show(this, R.string.sdk_string_auth_cancel)
                        }
                        LoginResponse.NEED_REGISTER -> { // 未注册
                            ToastKit.show(this, R.string.sdk_string_need_register)
                        }
                        LoginResponse.TOKEN_AUTH_FAILED -> { // token校验失效
                        }
                    }
                }
                .auth(this, tokenEt.text.toString(), thirdUidEt.text.toString().toLong())
        }
        clearCacheTv.setOnClickListener {
            if (commDialog == null) {
                commDialog = CommDialog(this)
            }
            commDialog?.showDialogComm(
                {
                    FloatLiveTools.clearCache()
                    ToastKit.show(this, R.string.clear_cache_success)
                    commDialog?.dismiss()
                },
                R.string.clear_cache_confirm, null, R.string.cancel, R.string.sure, R.string.remind
            )
        }
        FloatLiveManager.getInstance().setRechargeCallback { context ->
            ToastKit.show(context, "调起充值页面")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        FloatLiveManager.getInstance().unregister()
    }
}
