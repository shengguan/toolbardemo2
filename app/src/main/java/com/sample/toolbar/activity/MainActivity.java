package com.sample.toolbar.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sample.toolbar.R;
import com.sample.toolbar.fragment.DiscussionFragment;
import com.sample.toolbar.fragment.FriendsFragment;
import com.sample.toolbar.fragment.HomeFragment;
import com.sample.toolbar.fragment.MessageFragment;


public class MainActivity extends AppCompatActivity implements DrawerLayout.DrawerListener, View.OnClickListener {

    private DrawerLayout mDrawerLayout;

    private Toolbar mToolbar;

    private NavigationView mNavigationView;


    private ImageView mHeadView;

    private Context mContext;

    private HomeFragment mHomeFragment;

    private MessageFragment mMessageFragment;

    private DiscussionFragment mDiscusstionFragment;

    private FriendsFragment mFriendFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.)

        mContext = MainActivity.this;
        setContentView(R.layout.activity_main);

        initViews();

        initListener();


    }

    private void initListener() {

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerVisible(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });



        //mNavigationView 设置子item选中事件
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            MenuItem preMenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                //注意，不要将FragmentTransaction 设置为全局变量，会报java.lang.IllegalStateException: commit already called
                //该错误，是因为你的ft事务是全局的变量，只能commit一次  所以用两个局部ft事务去做commit即可
                FragmentManager fm = getSupportFragmentManager();

                FragmentTransaction transaction = fm.beginTransaction();

                if (preMenuItem != null) {
                    preMenuItem.setChecked(false);
                }
                item.setChecked(true);
                preMenuItem = item;

                hideAllFragment(transaction);

                switch (item.getItemId()) {
                    case R.id.nav_home:

                        if (mHomeFragment == null) {
                            mHomeFragment = new HomeFragment();
                            transaction.add(R.id.id_container, mHomeFragment);
                        } else {
                            transaction.show(mHomeFragment);
                        }

                        mToolbar.setTitle("HomeFragment");
                        break;
                    case R.id.nav_friends:
                        if (mFriendFragment == null) {
                            mFriendFragment = new FriendsFragment();
                            transaction.add(R.id.id_container, mFriendFragment);
                        } else {
                            transaction.show(mFriendFragment);
                        }

                        mToolbar.setTitle("FriendsFragment");
                        break;
                    case R.id.nav_discussion:

                        if (mDiscusstionFragment == null) {
                            mDiscusstionFragment = new DiscussionFragment();
                            transaction.add(R.id.id_container, mDiscusstionFragment);
                        } else {
                            transaction.show(mDiscusstionFragment);
                        }


                        mToolbar.setTitle("DiscussionFragment");
                        break;
                    case R.id.nav_messages:

                        if (mMessageFragment == null) {
                            mMessageFragment = new MessageFragment();
                            transaction.add(R.id.id_container, mMessageFragment);
                        } else {
                            transaction.show(mMessageFragment);
                        }

                        mToolbar.setTitle("MessageFragment");
                        break;

                }
                transaction.commit();

                //最后关闭抽屉
                mDrawerLayout.closeDrawers();
                return true;
            }
        });


    }
    //hide所有的fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {

        if (mHomeFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mFriendFragment != null) {
            fragmentTransaction.hide(mHomeFragment);
        }
        if (mDiscusstionFragment != null) {
            fragmentTransaction.hide(mDiscusstionFragment);
        }
        if (mMessageFragment != null) {
            fragmentTransaction.hide(mMessageFragment);
        }


    }

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);

        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);


        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        //设置某一个item、被点击
        mNavigationView.setCheckedItem(R.id.nav_home);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.id_container, new HomeFragment())
                .commit();

        //获取navigationbar中的headLayout中的控件方法
        View headerView = mNavigationView.getHeaderView(0);

        mHeadView = (ImageView) headerView.findViewById(R.id.header_img);

        mHeadView.setOnClickListener(this);


        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);

        actionBar.setHomeButtonEnabled(true);     //设置能够返回

        actionBar.setDisplayHomeAsUpEnabled(true);  //设置

        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("home");



        //初始化开关，并和drawer关联
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mToggle.syncState();//该方法会自动和actionBar关联

        actionBar.setTitle(getString(R.string.app_name));//设置标题
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.nav_home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.header_img:
                Toast.makeText(mContext, "点击换头像", Toast.LENGTH_SHORT).show();

//                startActivity(new Intent(this,PullZoomViewActivity.class));
//                startActivity(new Intent(this,ParallaxToolbarScrollViewActivity.class));


                startActivity(new Intent(this,DemoActivity.class));


                break;
        }
    }
}
