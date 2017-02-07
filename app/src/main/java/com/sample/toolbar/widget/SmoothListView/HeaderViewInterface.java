package com.sample.toolbar.widget.SmoothListView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.ListView;

import java.util.List;

public abstract class HeaderViewInterface<T> {

    protected Activity mContext;
    protected LayoutInflater mInflate;
    protected T mEntity;

    public HeaderViewInterface(Activity context) {
        this.mContext = context;
        mInflate = LayoutInflater.from(context);
    }

    /**
     * 填充 view 并设置数据
     * @param t            要填充的数据
     * @param listView     要填充的listView
     * @return
     */
    public boolean fillView(T t, ListView listView) {
        if (t == null) {
            return false;
        }
        if ((t instanceof List) && ((List) t).size() == 0) {
            return false;
        }
        this.mEntity = t;
        getView(t, listView);
        return true;
    }

    /**
     * 获取一个view作为headView并加入到listView中
     * @param t         泛型
     * @param listView  一个listview
     */
    protected abstract void getView(T t, ListView listView);

}
