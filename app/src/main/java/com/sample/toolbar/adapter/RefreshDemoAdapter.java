package com.sample.toolbar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.toolbar.R;
import com.sample.toolbar.domain.Person;

import java.util.List;

/**
 * Created by Administrator on 2016/6/12.
 */
public class RefreshDemoAdapter extends BaseAdapter {


    private List<Person> mDatas;

    private Context mContext;

    private LayoutInflater mInflater;

    public RefreshDemoAdapter(List<Person> datas, Context context) {
        this.mDatas = datas;
        this.mContext = context;

        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mDatas!=null?mDatas.size():0;
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView==null){

            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.recycleview_item,null);

            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.item_img);
            viewHolder.mTtile = (TextView) convertView.findViewById(R.id.item_title);
            viewHolder.mContent = (TextView) convertView.findViewById(R.id.item_content);

            convertView.setTag(viewHolder);



        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mImageView.setImageResource(mDatas.get(position).getPicResId());

        viewHolder.mTtile.setText(mDatas.get(position).getTitle());

        viewHolder.mContent.setText(mDatas.get(position).getContent());

        return convertView;
    }



    public class ViewHolder{
        ImageView mImageView;
        TextView mTtile;
        TextView mContent;
    }
}
