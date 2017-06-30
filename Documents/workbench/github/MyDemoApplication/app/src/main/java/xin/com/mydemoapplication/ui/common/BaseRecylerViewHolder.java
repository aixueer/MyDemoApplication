package xin.com.mydemoapplication.ui.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by wangzhenyu on 16/7/7.
 */
public abstract class BaseRecylerViewHolder<T> extends RecyclerView.ViewHolder {

    public Context mContext;

    public BaseRecylerViewHolder(View itemView, Context mContext) {
        super(itemView);
        this.mContext = mContext;
        ButterKnife.bind(this, itemView);
    }

    public abstract void setData(T object, int position);
}
