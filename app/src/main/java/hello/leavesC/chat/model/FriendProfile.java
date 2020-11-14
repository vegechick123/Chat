package hello.leavesC.chat.model;

import com.tencent.imsdk.TIMFriendGenderType;
import com.tencent.imsdk.TIMUserProfile;

import hello.leavesC.chat.R;
import hello.leavesC.chat.utils.LetterUtil;


public class FriendProfile extends BaseProfile {

    private TIMUserProfile userProfile;

    private boolean isSelected;

    public FriendProfile(TIMUserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String getIdentifier() {
        return userProfile.getIdentifier();
    }

    @Override
    public String getName() {
        if (!"".equals(getRemark())) {
            return getRemark();
        } else if (!"".equals(getNickname())) {
            return getNickname();
        }
        return getIdentifier();
    }

    @Override
    public int getDefaultAvatarResource() {
        return R.drawable.new_friend;
    }

    @Override
    public String getAvatarUrl() {
        return userProfile.getFaceUrl();
    }

    public String getRemark() {
        return userProfile.getRemark();
    }

    public String getNickname() {
        return userProfile.getNickName();
    }

    public String getSelfSignature() {
        return userProfile.getSelfSignature();
    }

    public TIMFriendGenderType getGender() {
        return userProfile.getGender();
    }

    public String getLocation() {
        return userProfile.getLocation();
    }

    public String getNameHeaderLetter() {
        return String.valueOf(LetterUtil.getHeaderLetter(getName()));
    }

    public TIMUserProfile getUserProfile() {
        return userProfile;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}