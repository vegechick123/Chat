package hello.leavesC.chat.model;

import android.text.SpannableString;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.ext.message.TIMConversationExt;

import java.util.List;

import hello.leavesC.chat.utils.MessageFactory;


public class SystemConversation extends BaseConversation {

    private static final String TAG = "SystemConversation";

    public SystemConversation(TIMConversation timConversation) {
        super(timConversation);
    }

    @Override
    protected void init() {
        peer = timConversation.getPeer();
        conversationType = timConversation.getType();
        name = "系统消息";
        TIMConversationExt conversationExt = new TIMConversationExt(timConversation);
        List<TIMMessage> lastMessageList = conversationExt.getLastMsgs(1);
        if (lastMessageList.size() > 0) {
            lastMessage = MessageFactory.getMessage(lastMessageList.get(0));
        } else {
            lastMessage = null;
        }
        lastMessageSummary = lastMessage == null ? new SpannableString("") : lastMessage.getMessageSummary();
        lastMessageTime = lastMessage == null ? 0 : lastMessage.getMessage().timestamp();
        unreadMessageNumber = timConversation == null ? 0 : conversationExt.getUnreadMessageNum();
    }

    @Override
    public void setLastMessage(BaseMessage lastMessage) {
        this.lastMessage = lastMessage;
        TIMConversationExt conversationExt = new TIMConversationExt(timConversation);
        lastMessageSummary = lastMessage == null ? new SpannableString("") : lastMessage.getMessageSummary();
        lastMessageTime = lastMessage == null ? 0 : lastMessage.getMessage().timestamp();
        unreadMessageNumber = timConversation == null ? 0 : conversationExt.getUnreadMessageNum();
    }

}
