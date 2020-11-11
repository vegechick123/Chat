package hello.leavesC.chat.view.open;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import hello.leavesC.chat.ChatApplication;
import hello.leavesC.chat.R;
import hello.leavesC.chat.view.MainActivity;
import hello.leavesC.chat.view.base.BaseActivity;
import hello.leavesC.presenter.viewModel.LoginViewModel;

/**
 * 作者：叶应是叶
 * 时间：2017/11/29 21:16
 * 说明：登录界面
 */
public class LoginActivity extends BaseActivity {

    public static final String IDENTIFIER = "identifier";

    private LoginViewModel loginViewModel;

    public static void navToLogin(Context context, String identifier) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(IDENTIFIER, identifier);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        setToolbarTitle("登录");
        EditText et_loginIdentifier = findViewById(R.id.et_loginIdentifier);
        EditText et_loginPassword = findViewById(R.id.et_loginPassword);
        String identifier = getIntent().getStringExtra(IDENTIFIER);
        if (TextUtils.isEmpty(identifier)) {
            identifier = loginViewModel.getLastUserIdentifier();
        }
        et_loginIdentifier.setText(identifier);
        if (!TextUtils.isEmpty(et_loginIdentifier.getText())) {
            et_loginPassword.requestFocus();
        }
        findViewById(R.id.btn_login).setOnClickListener(v -> {
            String identifier1 = et_loginIdentifier.getText().toString();
            String password = et_loginPassword.getText().toString();
            loginViewModel.login(identifier1, password);
        });
    }

    @Override
    protected ViewModel initViewModel() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getLoginEventLiveData().observe(this, loginEvent -> {
            assert loginEvent != null;
            ChatApplication.identifier = loginEvent.getIdentifier();
            startActivity(MainActivity.class);
            finish();
        });
        return loginViewModel;
    }

}