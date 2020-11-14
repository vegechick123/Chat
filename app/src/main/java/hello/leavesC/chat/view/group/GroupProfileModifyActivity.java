package hello.leavesC.chat.view.group;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import hello.leavesC.chat.R;
import hello.leavesC.chat.cache.GroupCache;
import hello.leavesC.chat.model.GroupProfile;
import hello.leavesC.chat.view.base.BaseActivity;
import hello.leavesC.presenter.viewModel.GroupProfileViewModel;
import hello.leavesC.presenter.viewModel.base.BaseViewModel;


public class GroupProfileModifyActivity extends BaseActivity {

    public static final int ALTER_GROUP_NAME = 1;

    public static final int ALTER_GROUP_INTRODUCTION = 2;

    public static final int ALTER_GROUP_NOTIFICATION = 3;

    public static final String KEY_GROUP_ID = "groupId";

    public static final String KEY_CODE = "KeyCode";

    public String groupId;

    public int code;

    private EditText et_groupProfileAlter;

    private String origin;

    private GroupProfileViewModel groupProfileViewModel;

    public static void navigation(Context context, String groupId, int code) {
        Intent intent = new Intent(context, GroupProfileModifyActivity.class);
        intent.putExtra(KEY_GROUP_ID, groupId);
        intent.putExtra(KEY_CODE, code);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_profile_modify);
        groupId = getIntent().getStringExtra(KEY_GROUP_ID);
        code = getIntent().getIntExtra(KEY_CODE, 0);
        GroupProfile groupProfile = GroupCache.getInstance().getGroupProfile(groupId);
        if (groupProfile == null) {
            finish();
            return;
        }
        setToolbarBtnText("保存");
        et_groupProfileAlter = findViewById(R.id.et_groupProfileAlter);
        switch (code) {
            case ALTER_GROUP_NAME: {
                setToolbarTitle("修改群名");
                et_groupProfileAlter.setMaxLines(8);
                origin = groupProfile.getName();
                break;
            }
            case ALTER_GROUP_INTRODUCTION: {
                setToolbarTitle("修改群简介");
                et_groupProfileAlter.setMaxLines(30);
                origin = groupProfile.getIntroduction();
                break;
            }
            case ALTER_GROUP_NOTIFICATION: {
                setToolbarTitle("修改群公告");
                et_groupProfileAlter.setMaxLines(30);
                origin = groupProfile.getNotification();
                break;
            }
            default: {
                finish();
                return;
            }
        }
        et_groupProfileAlter.setText(origin);
        et_groupProfileAlter.setSelection(TextUtils.isEmpty(et_groupProfileAlter.getText()) ? 0 : et_groupProfileAlter.getText().length());
        et_groupProfileAlter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().equals(origin)) {
                    getToolbarBtn().setEnabled(false);
                } else {
                    getToolbarBtn().setEnabled(true);
                }
            }
        });
        setToolbarBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = et_groupProfileAlter.getText().toString().trim();
                switch (code) {
                    case ALTER_GROUP_NAME: {
                        if (TextUtils.isEmpty(result)) {
                            showToast("群名不能为空");
                            return;
                        }
                        groupProfileViewModel.modifyGroupName(groupId, result);
                        break;
                    }
                    case ALTER_GROUP_INTRODUCTION: {
                        groupProfileViewModel.modifyGroupIntroduction(groupId, result);
                        break;
                    }
                    case ALTER_GROUP_NOTIFICATION: {
                        groupProfileViewModel.modifyGroupNotification(groupId, result);
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected BaseViewModel initViewModel() {
        groupProfileViewModel = ViewModelProviders.of(this).get(GroupProfileViewModel.class);
        return groupProfileViewModel;
    }

}