package hello.leavesC.chat;

import android.app.Application;

import hello.leavesC.chat.business.InitBusiness;


public class ChatApplication extends Application {

    public static String identifier = "";

    @Override
    public void onCreate() {
        super.onCreate();
        InitBusiness.init(this);
    }

}