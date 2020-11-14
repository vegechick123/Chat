package hello.leavesC.chat.view.contacts;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import hello.leavesC.chat.R;
import hello.leavesC.chat.cache.FriendCache;
import hello.leavesC.chat.view.base.BaseActivity;
import hello.leavesC.presenter.viewModel.ModifyFriendProfileViewModel;
import hello.leavesC.presenter.viewModel.base.BaseViewModel;


public class AlterFriendRemarkActivity extends BaseActivity {

    private static final String IDENTIFIER = "identifier";

    private String identifier;

    private String original;

    private ModifyFriendProfileViewModel profileViewModel;

    public static void navigation(Context context, String identifier) {
        Intent intent = new Intent(context, AlterFriendRemarkActivity.class);
        intent.putExtra(IDENTIFIER, identifier);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alter_friend_remark);
        identifier = getIntent().getStringExtra(IDENTIFIER);
        original = FriendCache.getInstance().getProfile(identifier).getRemark();
        initView();
    }

    @Override
    protected BaseViewModel initViewModel() {
        profileViewModel = ViewModelProviders.of(this).get(ModifyFriendProfileViewModel.class);
        return profileViewModel;
    }

    private void initView() {
        setToolbarTitle("设置备注");
        EditText et_alterFriendRemark = findViewById(R.id.et_alterFriendRemark);
        setToolbarBtnClickListener(v -> {
            String remark = et_alterFriendRemark.getText().toString().trim();
            profileViewModel.modifyFriendRemark(identifier, remark);
        });
        et_alterFriendRemark.setText(original);
        et_alterFriendRemark.setSelection(TextUtils.isEmpty(et_alterFriendRemark.getText()) ? 0 : et_alterFriendRemark.getText().length());
        et_alterFriendRemark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().equals(original)) {
                    getToolbarBtn().setEnabled(false);
                } else {
                    getToolbarBtn().setEnabled(true);
                }
            }
        });
    }

}