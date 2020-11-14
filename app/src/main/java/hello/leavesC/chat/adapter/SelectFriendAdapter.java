package hello.leavesC.chat.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;

import java.util.List;

import hello.leavesC.chat.R;
import hello.leavesC.chat.model.FriendProfile;
import hello.leavesC.common.recycler.common.CommonRecyclerViewHolder;


public class SelectFriendAdapter extends FriendAdapter {

    public SelectFriendAdapter(Context context, List<FriendProfile> dataList) {
        super(context, dataList, R.layout.item_friend_select);
    }

    @Override
    protected boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        FriendProfile oldFriendProfile = dataList.get(oldItemPosition);
        FriendProfile newFriendProfile = newDataList.get(newItemPosition);
        return super.areContentsTheSame(oldItemPosition, newItemPosition)
                && (oldFriendProfile.isSelected() == newFriendProfile.isSelected());
    }

    @NonNull
    @Override
    protected Bundle getChangePayload(int oldItemPosition, int newItemPosition) {
        Bundle bundle = super.getChangePayload(oldItemPosition, newItemPosition);
        FriendProfile oldFriendProfile = dataList.get(oldItemPosition);
        FriendProfile newFriendProfile = newDataList.get(newItemPosition);
        if (oldFriendProfile.isSelected() != newFriendProfile.isSelected()) {
            bundle.putBoolean("IsSelected", newFriendProfile.isSelected());
        }
        return bundle;
    }

    @Override
    protected void partialBindData(CommonRecyclerViewHolder holder, @NonNull Bundle bundle) {
        for (String key : bundle.keySet()) {
            switch (key) {
                case "AvatarUrl": {
                    break;
                }
                case "Name": {
                    holder.setText(R.id.tv_selectFriend_name, bundle.getString(key));
                    break;
                }
                case "IsSelected": {
                    holder.setImageResource(R.id.iv_selectFriend_tag, bundle.getBoolean(key) ? R.drawable.selected : R.drawable.unselected);
                    break;
                }
            }
        }
    }

    @Override
    protected void entirelyBindData(CommonRecyclerViewHolder holder, FriendProfile data, int position) {
        holder.setText(R.id.tv_selectFriend_name, data.getName())
                .setImageResource(R.id.iv_selectFriend_tag, data.isSelected()
                        ? R.drawable.selected : R.drawable.unselected);
    }

}
