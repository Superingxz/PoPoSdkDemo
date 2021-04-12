package os.imlive.demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import os.imlive.base.http.response.LoginResponse;
import os.imlive.floating.FloatLiveManager;
import os.imlive.floating.listener.LoginListener;
import os.imlive.framework.utils.ToastUtils;

/**
 * <pre>
 *     author: myz
 *     email : moyaozhi@imdawei.com
 *     time  : 2021/4/12 12:16
 *     desc  :
 * </pre>
 */
public class SdkSimpleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdk_demo);
        TextView loginTv = findViewById(R.id.login);
        loginTv.setOnClickListener(v -> {
            FloatLiveManager.getInstance()
                    .getLoginInstance(this)
                    .setLoginListener(new LoginListener() {
                        @Override
                        public void onLoginState(int state, String msg) {
                            switch (state) {
                                case LoginResponse.FAILED: // 登陆失败
                                    ToastUtils.show(msg);
                                    break;
                                case LoginResponse.AUTH_FAILED: // 授权失败
                                    ToastUtils.show("授权失败");
                                    break;
                                case LoginResponse.AUTH_CANCEL: // 授权取消
                                    ToastUtils.show("授权取消");
                                    break;
                                case LoginResponse.NEED_REGISTER: // 未注册
                                    ToastUtils.show("未注册");
                                    break;
                            }
                        }
                    })
                    .loginSdk(this);
        });
    }
}
