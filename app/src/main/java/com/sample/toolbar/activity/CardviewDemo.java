package com.sample.toolbar.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.sample.toolbar.R;
import com.sample.toolbar.adapter.CardDemoAdapter;
import com.sample.toolbar.adapter.DividerItemDecoration;
import com.sample.toolbar.domain.Person;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/4/20.
 */
public class CardviewDemo extends AppCompatActivity implements CardDemoAdapter.OnItemClickListener {


    private Toolbar mToolbar;

    private CollapsingToolbarLayout mMCollapsingToolbarLayout;

    private RecyclerView mRecyclerView;

    private ArrayList<Person> mDatas;

    private CardDemoAdapter mCardDemoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cardview);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.card_recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mCardDemoAdapter = new CardDemoAdapter(this, mDatas);

        mCardDemoAdapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter(mCardDemoAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));


        mToolbar = (Toolbar) findViewById(R.id.card_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setIcon(R.mipmap.ic_headset);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mMCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.card_collapsing_toolbar_layout);
        mMCollapsingToolbarLayout.setTitle("CollapsingToolbarLayout");
//        通过CollapsingToolbarLayout修改字体颜色
        mMCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mMCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色

        mMCollapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后的字体居中

        mMCollapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER);//设置还没收缩时字体居中

//        mMCollapsingToolbarLayout.setStatusBarScrim(getResources().getDrawable(R.mipmap.ic_discuss));
//
//        mMCollapsingToolbarLayout.setTitleEnabled(false);
    }

    protected void initData() {
        int[] imgs = {R.mipmap.alex, R.mipmap.chris, R.mipmap.claire, R.mipmap.colt, R.mipmap.joanna,
                R.mipmap.kathryn};
        mDatas = new ArrayList<Person>();
        Person person;
        for (int i = 0; i < 100; i++) {
            person = new Person("title" + i, R.mipmap.alex, "content" + i);

            mDatas.add(person);
        }

    }

    @Override
    public void onItemClickListener(View view, int position) {

        Toast.makeText(this, "相应点击事件" + position, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemLongClickListener(View view, int position) {
        Toast.makeText(this, "相应长安事件", Toast.LENGTH_SHORT).show();
    }


}
