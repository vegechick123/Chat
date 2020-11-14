package hello.leavesC.chat.view.conversation;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hello.leavesC.chat.R;
import hello.leavesC.chat.adapter.SystemMessageAdapter;
import hello.leavesC.chat.model.BaseMessage;
import hello.leavesC.chat.utils.SystemMessageComparator;
import hello.leavesC.chat.utils.MessageFactory;
import hello.leavesC.chat.view.base.BaseActivity;
import hello.leavesC.common.recycler.common.CommonItemDecoration;
import hello.leavesC.presenter.event.ChatActionEvent;
import hello.leavesC.presenter.viewModel.ChatViewModel;
import hello.leavesC.presenter.viewModel.base.BaseViewModel;


public class SystemMessageListActivity extends BaseActivity {

    private SystemMessageAdapter systemMessageAdapter;

    private List<BaseMessage> messageList;

    private static final String PEER = "peer";

    private ChatViewModel chatViewModel;

    private LinearLayoutManager linearLayoutManager;

    private static final String TAG = "SystemMessageListActivity";

    public static void navigation(Context context, String peer) {
        Intent intent = new Intent(context, SystemMessageListActivity.class);
        intent.putExtra(PEER, peer);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message_list);
        setToolbarTitle("系统消息");
        RecyclerView rv_systemMessageList = findViewById(R.id.rv_systemMessageList);
        linearLayoutManager = new LinearLayoutManager(this);
        rv_systemMessageList.setLayoutManager(linearLayoutManager);
        rv_systemMessageList.addItemDecoration(new CommonItemDecoration(ContextCompat.getDrawable(getContext(), R.drawable.divider), LinearLayoutManager.VERTICAL));
        messageList = new ArrayList<>();
        systemMessageAdapter = new SystemMessageAdapter(this, messageList);
        rv_systemMessageList.setAdapter(systemMessageAdapter);
        chatViewModel.start(getIntent().getStringExtra(PEER), TIMConversationType.System, this);
    }

    @Override
    protected BaseViewModel initViewModel() {
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        chatViewModel.getChatActionLiveData().observe(this, this::chatActionEvent);
        chatViewModel.getShowMessageLiveData().observe(this, this::showMessage);
        chatViewModel.getShowListMessageLiveData().observe(this, this::showMessage);
        return chatViewModel;
    }

    public void showMessage(TIMMessage message) {
        if (message == null) {

        } else {
            BaseMessage baseMessage = MessageFactory.getMessage(message);
            if (baseMessage != null) {
                messageList.add(baseMessage);
            }
        }
        Collections.sort(messageList, new SystemMessageComparator());
        systemMessageAdapter.setData(messageList);
        linearLayoutManager.scrollToPosition(0);
    }

    public void showMessage(List<TIMMessage> messageList) {
        if (messageList.size() == 0) {
            return;
        }
        List<BaseMessage> messages = new ArrayList<>();
        for (int i = 0; i < messageList.size(); i++) {
            BaseMessage baseMessage = MessageFactory.getMessage(messageList.get(i));
            if (baseMessage != null) {
                messages.add(baseMessage);
            }
        }
        this.messageList.addAll(0, messages);
        Collections.sort(this.messageList, new SystemMessageComparator());
        systemMessageAdapter.setData(this.messageList);
    }

    private void chatActionEvent(ChatActionEvent chatActionEvent) {
        switch (chatActionEvent.getAction()) {
            case ChatActionEvent.CLEAN_MESSAGE: {
                messageList.clear();
                systemMessageAdapter.setData(messageList);
                break;
            }
        }
    }

}