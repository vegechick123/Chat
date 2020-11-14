package hello.leavesC.chat.view;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import hello.leavesC.chat.R;
import hello.leavesC.chat.cache.FriendCache;
import hello.leavesC.chat.cache.GroupCache;
import hello.leavesC.chat.view.base.BaseActivity;
import hello.leavesC.chat.view.contacts.ContactsFragment;
import hello.leavesC.chat.view.conversation.ConversationFragment;
import hello.leavesC.chat.view.friendship.SearchUserActivity;
import hello.leavesC.chat.view.group.SelectFriendToCreateGroupActivity;
import hello.leavesC.chat.view.me.MeFragment;
import hello.leavesC.chat.view.open.OpenActivity;
import hello.leavesC.presenter.viewModel.base.BaseViewModel;
import hello.leavesC.sdk.Constants;
import tencent.tls.platform.TLSLoginHelper;


public class MainActivity extends BaseActivity {

    private List<Fragment> tabFragments;

    private FragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    @Override
    protected BaseViewModel initViewModel() {
        return null;
    }

    private void initData() {
        tabFragments = new ArrayList<>();
        ConversationFragment conversationFragment = new ConversationFragment();
        ContactsFragment contactsFragment = new ContactsFragment();
        MeFragment meFragment = new MeFragment();
        tabFragments.add(conversationFragment);
        tabFragments.add(contactsFragment);
        tabFragments.add(meFragment);
        final String[] titles = {"会话", "好友", "我"};
        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return tabFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return tabFragments.get(arg0);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        };
    }

    private void initView() {
        initToolbar(getString(R.string.app_name), false);
        ViewPager viewPager = findViewById(R.id.viewpager_main);
        TabLayout tabLayout = findViewById(R.id.tabLayout_main);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_addFriend:
                startActivity(SearchUserActivity.class);
                break;
            case R.id.menu_createGroup:
                startActivity(SelectFriendToCreateGroupActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FriendCache.getInstance().clear();
        GroupCache.getInstance().clear();
    }

    public void logout() {
        TLSLoginHelper loginHelper = TLSLoginHelper.getInstance().init(getApplicationContext(), Constants.SDK_APP_ID, Constants.ACCOUNT_TYPE, Constants.APP_VERSION);
        loginHelper.clearUserInfo(loginHelper.getLastUserInfo().identifier);
        startActivity(OpenActivity.class);
        finish();
    }

}