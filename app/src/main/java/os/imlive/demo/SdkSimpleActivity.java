package os.imlive.demo;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;

import os.imlive.base.http.response.LoginResponse;
import os.imlive.sdk.FloatLiveManager;

/**
 * <pre>
 *     author: myz
 *     email : moyaozhi@imdawei.com
 *     time  : 2021/4/12 12:16
 *     desc  : SDKDemo
 * </pre>
 */
public class SdkSimpleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdk_demo);
        // 登录SDK前需要调用
        FloatLiveManager.getInstance().getLoginInstance(this)
                .getThirdAdviceParams();
        initViews();
    }

    private void initViews() {
        AppCompatImageView backImg = findViewById(R.id.backImg);
        AppCompatEditText tokenEt = findViewById(R.id.tokenEt);
        AppCompatEditText userIdEt = findViewById(R.id.userIdEt);
        TextView loginTv = findViewById(R.id.login);
        backImg.setOnClickListener(v -> {
            finish();
        });
        loginTv.setOnClickListener(v -> {
            if (TextUtils.isEmpty(tokenEt.getText().toString())) {
                ToastKit.show(this, R.string.value_token_input);
                return;
            }
            if (TextUtils.isEmpty(userIdEt.getText().toString())) {
                ToastKit.show(this, R.string.value_user_id_input);
                return;
            }
            FloatLiveManager.getInstance()
                    .getLoginInstance(this)
                    .setLoginListener((state, msg) -> {
                        switch (state) {
                            case LoginResponse.FAILED: // 登陆失败
                                ToastKit.show(SdkSimpleActivity.this, msg);
                                break;
                            case LoginResponse.AUTH_FAILED: // 授权失败
                                ToastKit.show(SdkSimpleActivity.this, R.string.string_auth_failed);
                                break;
                            case LoginResponse.AUTH_CANCEL: // 授权取消
                                ToastKit.show(SdkSimpleActivity.this, R.string.string_auth_cancel);
                                break;
                            case LoginResponse.NEED_REGISTER: // 未注册
                                ToastKit.show(SdkSimpleActivity.this, R.string.string_need_register);
                                break;
                            case LoginResponse.TOKEN_AUTH_FAILED: // token校验失效
                                break;
                        }
                    })
                    .loginSdk(this, tokenEt.getText().toString(), userIdEt.getText().toString());
        });
    }
}
