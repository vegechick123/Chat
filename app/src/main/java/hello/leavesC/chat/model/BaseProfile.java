package hello.leavesC.chat.model;

import androidx.annotation.DrawableRes;


abstract class BaseProfile {

    abstract public String getIdentifier();

    abstract public String getName();

    @DrawableRes
    abstract public int getDefaultAvatarResource();

    abstract public String getAvatarUrl();

}
