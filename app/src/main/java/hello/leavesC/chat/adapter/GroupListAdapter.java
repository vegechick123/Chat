package hello.leavesC.chat.adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;

import java.util.List;

import hello.leavesC.chat.R;
import hello.leavesC.chat.model.GroupProfile;
import hello.leavesC.common.recycler.common.CommonRecyclerViewAdapter;
import hello.leavesC.common.recycler.common.CommonRecyclerViewHolder;


public class GroupListAdapter extends CommonRecyclerViewAdapter<GroupProfile> {

    public GroupListAdapter(Context context, List<GroupProfile> dataList) {
        super(context, dataList, R.layout.item_group);
    }

    @Override
    protected GroupProfile clone(GroupProfile data) {
        return new GroupProfile(data.getGroupDetailInfo(), data.getGroupBasicSelfInfo());
    }

    @Override
    protected boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @NonNull
    @Override
    protected Bundle getChangePayload(int oldItemPosition, int newItemPosition) {
        return new Bundle();
    }

    @Override
    protected void partialBindData(CommonRecyclerViewHolder holder, @NonNull Bundle bundle) {

    }

    @Override
    protected void entirelyBindData(CommonRecyclerViewHolder holder, GroupProfile data, int position) {
        holder.setText(R.id.tv_groupList_name, data.getName());
    }

}
