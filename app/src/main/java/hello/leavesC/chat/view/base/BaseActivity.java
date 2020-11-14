package hello.leavesC.chat.view.base;

import android.annotation.SuppressLint;
import androidx.lifecycle.ViewModel;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.ArrayList;
import java.util.List;

import hello.leavesC.chat.R;
import hello.leavesC.common.dialog.LoadingDialog;
import hello.leavesC.common.dialog.MessageDialog;
import hello.leavesC.presenter.event.base.BaseActionEvent;
import hello.leavesC.presenter.viewModel.base.IViewModelAction;


@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {

    private LoadingDialog loadingDialog;

    private MessageDialog messageDialog;

    private Button btnToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        initViewModelEvent();
    }

    protected abstract ViewModel initViewModel();

    protected List<ViewModel> initViewModelList() {
        return null;
    }

    private void initViewModelEvent() {
        List<ViewModel> viewModelList = initViewModelList();
        if (viewModelList != null && viewModelList.size() > 0) {
            observeEvent(viewModelList);
        } else {
            ViewModel viewModel = initViewModel();
            if (viewModel != null) {
                List<ViewModel> modelList = new ArrayList<>();
                modelList.add(viewModel);
                observeEvent(modelList);
            }
        }
    }

    private void observeEvent(List<ViewModel> viewModelList) {
        for (ViewModel viewModel : viewModelList) {
            if (viewModel instanceof IViewModelAction) {
                IViewModelAction viewModelAction = (IViewModelAction) viewModel;
                viewModelAction.getActionLiveData().observe(this, baseActionEvent -> {
                    if (baseActionEvent != null) {
                        switch (baseActionEvent.getAction()) {
                            case BaseActionEvent.SHOW_LOADING_DIALOG: {
                                showLoadingDialog(baseActionEvent.getMessage());
                                break;
                            }
                            case BaseActionEvent.DISMISS_LOADING_DIALOG: {
                                dismissLoadingDialog();
                                break;
                            }
                            case BaseActionEvent.SHOW_TOAST: {
                                showToast(baseActionEvent.getMessage());
                                break;
                            }
                            case BaseActionEvent.FINISH: {
                                finish();
                                break;
                            }
                            case BaseActionEvent.FINISH_WITH_RESULT_OK: {
                                setResult(RESULT_OK);
                                finish();
                                break;
                            }
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissLoadingDialog();
        dismissMessageDialog();
    }

    protected void initToolbar(String toolbarTitle, boolean displayHomeAsUpEnabled) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(toolbarTitle);
                if (displayHomeAsUpEnabled) {
                    actionBar.setDisplayHomeAsUpEnabled(true);
                    toolbar.setNavigationOnClickListener(v -> onToolbarBack());
                }
            }
        }
    }

    protected void setToolbarTitle(String toolbarTitle) {
        initToolbar(toolbarTitle, true);
    }

    public Button getToolbarBtn() {
        if (btnToolbar == null) {
            btnToolbar = findViewById(R.id.btn_toolbarSure);
        }
        return btnToolbar;
    }

    public void setToolbarBtnClickListener(View.OnClickListener clickListener) {
        getToolbarBtn().setOnClickListener(clickListener);
    }

    protected void setToolbarBtnText(String content) {
        if (btnToolbar == null) {
            btnToolbar = findViewById(R.id.btn_toolbarSure);
        }
        btnToolbar.setText(content);
    }

    protected void onToolbarBack() {
        finish();
    }

    protected void showLoadingDialog(String hintText, boolean cancelable, boolean canceledOnTouchOutside) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.show(hintText, cancelable, canceledOnTouchOutside);
    }

    protected void showLoadingDialog(String hintText) {
        showLoadingDialog(hintText, false, false);
    }

    protected void showMessageDialog(String title, String message, DialogInterface.OnClickListener positiveCallback) {
        if (messageDialog == null) {
            messageDialog = new MessageDialog();
        }
        messageDialog.show(title, message, positiveCallback, getSupportFragmentManager());
    }

    protected void showConfirmMessageDialog(String title, String message, DialogInterface.OnClickListener positiveCallback) {
        if (messageDialog == null) {
            messageDialog = new MessageDialog();
        }
        messageDialog.showConfirm(title, message, positiveCallback, getSupportFragmentManager());
    }

    protected void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    protected void dismissMessageDialog() {
        if (messageDialog != null && loadingDialog.isShowing()) {
            messageDialog.dismiss();
        }
    }

    protected BaseActivity getContext() {
        return BaseActivity.this;
    }

    protected void startActivity(Class cl) {
        startActivity(new Intent(this, cl));
    }

    public void startActivityForResult(Class cl, int requestCode) {
        startActivityForResult(new Intent(this, cl), requestCode);
    }

    protected void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    protected boolean isFinishingOrDestroyed() {
        return isFinishing() || isDestroyed();
    }

}