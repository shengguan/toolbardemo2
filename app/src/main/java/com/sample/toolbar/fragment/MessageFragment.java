package com.sample.toolbar.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.sample.toolbar.activity.CardviewDemo;
import com.sample.toolbar.R;
import com.sample.toolbar.adapter.DividerItemDecoration;
import com.sample.toolbar.adapter.ListAdapter;
import com.sample.toolbar.domain.Person;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/4/12.
 */
public class MessageFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, ListAdapter.OnItemClickListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView mRecyclerView;

    private ListAdapter mListAdapter;

    private ArrayList<Person> mDatas ;

    private Context mContext;

    private FloatingActionButton mFab;

    @Override
    protected void initData(Bundle savedInstanceState) {
        int[] imgs = {R.mipmap.alex,R.mipmap.chris, R.mipmap.claire,R.mipmap.colt,R.mipmap.joanna,
        R.mipmap.kathryn};
        mDatas = new ArrayList<Person>();
        Person person ;
        for (int i = 0; i < 100; i++) {
            person = new Person("title"+i,R.mipmap.alex,"content"+i);

            mDatas.add(person);
        }

    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mContext = getActivity();

        View view = inflater.inflate(R.layout.activity_home,container,false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.home_sr);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.home_ryv);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setProgressBackgroundColor(R.color.refresh_bg);


        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        mListAdapter = new ListAdapter(getActivity(),mDatas);

        mListAdapter.setOnItemClickListener(this);

        mRecyclerView.setLayoutManager(manager);

        mRecyclerView.setAdapter(mListAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL_LIST));

        mFab = (FloatingActionButton) view.findViewById(R.id.home_fab);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, CardviewDemo.class));
            }
        });



        return view;
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:
                    mListAdapter.notifyDataSetChanged();

                    mSwipeRefreshLayout.setRefreshing(false);

                    break;
            }
        }
    };



    @Override
    public void onRefresh() {


        new Thread(){

            @Override
            public void run() {
                super.run();

                Person person;
                for (int i = 100; i < 200; i++) {
                    person = new Person("title"+i,R.mipmap.alex,"content"+i);

                    mDatas.add(person);
                }

                handler.sendEmptyMessage(1);

            }
        }.start();


    }

    @Override
    public void onItemClickListener(View view, int position) {

        Toast.makeText(mContext,"相应点击事件",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemLongClickListener(View view, int position) {
        Toast.makeText(mContext,"相应长安事件",Toast.LENGTH_SHORT).show();
    }
}
