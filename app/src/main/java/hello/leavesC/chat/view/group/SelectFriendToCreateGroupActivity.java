package hello.leavesC.chat.view.group;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hello.leavesC.chat.R;
import hello.leavesC.chat.adapter.SelectFriendAdapter;
import hello.leavesC.chat.cache.FriendCache;
import hello.leavesC.chat.cache.GroupCache;
import hello.leavesC.chat.model.FriendProfile;
import hello.leavesC.chat.view.base.BaseActivity;
import hello.leavesC.common.common.LetterIndexView;
import hello.leavesC.common.recycler.common.CommonItemDecoration;
import hello.leavesC.common.recycler.common.CommonRecyclerViewHolder;
import hello.leavesC.presenter.viewModel.GroupProfileViewModel;
import hello.leavesC.presenter.viewModel.base.BaseViewModel;


public class SelectFriendToCreateGroupActivity extends BaseActivity {

    private List<FriendProfile> friendProfileList;

    private SelectFriendAdapter selectFriendAdapter;

    private List<String> peerList;

    private GroupProfileViewModel groupProfileViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_friend);
        setToolbarTitle("至少选择两位好友");
        peerList = new ArrayList<>();
        friendProfileList = new ArrayList<>();
        friendProfileList.addAll(FriendCache.getInstance().getFriendProfileList());
        selectFriendAdapter = new SelectFriendAdapter(getContext(), friendProfileList);
        selectFriendAdapter.setClickListener(new CommonRecyclerViewHolder.OnClickListener() {
            @Override
            public void onClick(int position) {
                FriendProfile friendProfile = friendProfileList.get(position);
                friendProfile.setSelected(!friendProfile.isSelected());
                if (friendProfile.isSelected()) {
                    peerList.add(friendProfile.getIdentifier());
                } else {
                    peerList.remove(friendProfile.getIdentifier());
                }
                selectFriendAdapter.setData(friendProfileList);
                getToolbarBtn().setEnabled(peerList.size() > 1);
            }
        });
        RecyclerView rv_selectFriend = findViewById(R.id.rv_selectFriend);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_selectFriend.setLayoutManager(linearLayoutManager);
        rv_selectFriend.setAdapter(selectFriendAdapter);
        rv_selectFriend.addItemDecoration(new CommonItemDecoration(ContextCompat.getDrawable(getContext(), R.drawable.divider), LinearLayoutManager.VERTICAL));
        LetterIndexView liv_letters = findViewById(R.id.liv_selectFriend_letters);
        TextView tv_selectFriend_hint = findViewById(R.id.tv_selectFriend_hint);
        liv_letters.bindIndexView(tv_selectFriend_hint, linearLayoutManager, new HashMap<>());
        setToolbarBtnClickListener(v -> groupProfileViewModel.createGroup("群聊", GroupCache.PRIVATE_GROUP, peerList));
    }

    @Override
    protected BaseViewModel initViewModel() {
        groupProfileViewModel = ViewModelProviders.of(this).get(GroupProfileViewModel.class);
        return groupProfileViewModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (FriendProfile friendProfile : friendProfileList) {
            friendProfile.setSelected(false);
        }
    }

}