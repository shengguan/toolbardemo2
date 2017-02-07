package com.sample.toolbar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sample.toolbar.R;
import com.sample.toolbar.domain.Person;

import java.util.ArrayList;


/**
 * Created by Administrator on 2016/4/12.
 */
public class CardDemoAdapter extends RecyclerView.Adapter<CardDemoAdapter.ListHolder> {

    private Context mContext;

    private ArrayList<Person> mDatas;

    private LayoutInflater mInflater;

    private OnItemClickListener mOnItemClickListener;



    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);
        void onItemLongClickListener(View view, int position);
    }


    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CardDemoAdapter(Context context, ArrayList<Person> datas) {
        mContext = context;
        mDatas = datas;

        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.cardview_item, parent, false);

        ListHolder holder = new ListHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ListHolder holder, final int position) {
        holder.setData(position);

        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClickListener(holder.itemView,position);
                }
            });


            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    mOnItemClickListener.onItemLongClickListener(holder.itemView,position);
                    return true;
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    class ListHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        TextView mTitle;

        TextView mContent;

        public ListHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.card_item_img);

            mTitle = (TextView) itemView.findViewById(R.id.card_item_title);

            mContent = (TextView) itemView.findViewById(R.id.card_item_content);


        }

        public void setData(int position) {
            mImageView.setBackgroundResource(mDatas.get(position).getPicResId());

            mTitle.setText(mDatas.get(position).getTitle());

            mContent.setText(mDatas.get(position).getContent());

        }
    }
}
