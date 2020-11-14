package hello.leavesC.chat.utils;

import java.util.Comparator;

import hello.leavesC.chat.model.BaseConversation;


public class ConversationComparator implements Comparator<BaseConversation> {

    @Override
    public int compare(BaseConversation o1, BaseConversation o2) {
        long time1 = o1.getLastMessageTime();
        long time2 = o2.getLastMessageTime();
        return Long.compare(time2, time1);
    }

}
