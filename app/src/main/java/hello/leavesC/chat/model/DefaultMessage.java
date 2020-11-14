package hello.leavesC.chat.model;

import android.text.SpannableString;

import com.tencent.imsdk.TIMMessage;


public class DefaultMessage extends BaseMessage {

    public DefaultMessage(TIMMessage message) {
        super(message);
    }

    @Override
    public SpannableString getMessageSummary() {
        return new SpannableString("[未知消息]");
    }

}