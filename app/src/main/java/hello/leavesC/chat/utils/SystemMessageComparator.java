package hello.leavesC.chat.utils;

import java.util.Comparator;

import hello.leavesC.chat.model.BaseMessage;


public class SystemMessageComparator implements Comparator<BaseMessage> {

    @Override
    public int compare(BaseMessage o1, BaseMessage o2) {
        long time1 = o1.getMessageTime();
        long time2 = o2.getMessageTime();
        return Long.compare(time2, time1);
    }

}
