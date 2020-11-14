package hello.leavesC.chat.model;

import android.text.SpannableString;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.ext.message.TIMConversationExt;

import java.util.List;

import hello.leavesC.chat.cache.FriendCache;
import hello.leavesC.chat.cache.GroupCache;
import hello.leavesC.chat.utils.MessageFactory;


public class ChatConversation extends BaseConversation {

    private static final String TAG = "ChatConversation";

    public ChatConversation(TIMConversation timConversation) {
        super(timConversation);
    }

    @Override
    protected void init() {
        peer = timConversation.getPeer();
        conversationType = timConversation.getType();
        switch (conversationType) {
            case C2C: {
                FriendProfile friendProfile = FriendCache.getInstance().getProfile(peer);
                name = friendProfile == null ? peer : friendProfile.getName();
                avatarUrl = friendProfile == null ? "" : friendProfile.getAvatarUrl();
                break;
            }
            case Group: {
                GroupProfile groupProfile = GroupCache.getInstance().getGroupProfile(peer);
                name = groupProfile == null ? peer : groupProfile.getName();
                avatarUrl = groupProfile == null ? "" : groupProfile.getAvatarUrl();
                break;
            }
            default: {
                name = peer;
                avatarUrl = "";
                break;
            }
        }
        TIMConversationExt conversationExt = new TIMConversationExt(timConversation);
        List<TIMMessage> lastMessageList = conversationExt.getLastMsgs(1);
        if (lastMessageList.size() > 0) {
            lastMessage = MessageFactory.getMessage(lastMessageList.get(0));
        } else {
            lastMessage = null;
        }
        if (conversationExt.hasDraft()) {
            if (lastMessage != null && lastMessage.getMessageTime() > conversationExt.getDraft().getTimestamp()) {
                lastMessageSummary = lastMessage.getMessageSummary();
                lastMessageTime = lastMessage.getMessage().timestamp();
            } else {
                TextMessage textMessage = new TextMessage(conversationExt.getDraft());
                lastMessageSummary = new SpannableString("[草稿] " + textMessage.getMessageSummary());
                lastMessageTime = conversationExt.getDraft().getTimestamp();
            }
        } else {
            lastMessageSummary = lastMessage == null ? new SpannableString("") : lastMessage.getMessageSummary();
            lastMessageTime = lastMessage == null ? 0 : lastMessage.getMessage().timestamp();
        }
        unreadMessageNumber = timConversation == null ? 0 : conversationExt.getUnreadMessageNum();
    }

    @Override
    public void setLastMessage(BaseMessage lastMessage) {
        this.lastMessage = lastMessage;
        TIMConversationExt conversationExt = new TIMConversationExt(timConversation);
        if (conversationExt.hasDraft()) {
            if (lastMessage != null && lastMessage.getMessageTime() > conversationExt.getDraft().getTimestamp()) {
                lastMessageSummary = lastMessage.getMessageSummary();
                lastMessageTime = lastMessage.getMessage().timestamp();
            } else {
                TextMessage textMessage = new TextMessage(conversationExt.getDraft());
                lastMessageSummary = new SpannableString("[草稿] " + textMessage.getMessageSummary());
                lastMessageTime = conversationExt.getDraft().getTimestamp();
            }
        } else {
            lastMessageSummary = lastMessage == null ? new SpannableString("") : lastMessage.getMessageSummary();
            lastMessageTime = lastMessage == null ? 0 : lastMessage.getMessage().timestamp();
        }
        unreadMessageNumber = timConversation == null ? 0 : conversationExt.getUnreadMessageNum();
    }

}
