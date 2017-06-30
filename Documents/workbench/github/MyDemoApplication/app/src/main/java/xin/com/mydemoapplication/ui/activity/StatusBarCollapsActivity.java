package xin.com.mydemoapplication.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zxstatusbarlibrary.utils.StatusBarCompat;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import xin.com.mydemoapplication.R;
import xin.com.mydemoapplication.ui.common.BaseRecylerViewHolder;
import xin.com.mydemoapplication.ui.view.StretchSwipeRefreshLayout;

public class StatusBarCollapsActivity extends Activity {

    @Bind(R.id.img_special_bj)
    ImageView imgSpecialBj;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout collapsingToolBar;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @Bind(R.id.recylerView)
    RecyclerView recylerView;
    @Bind(R.id.coording_layout)
    CoordinatorLayout coordingLayout;
//    @Bind(R.id.swrefresh)
//    StretchSwipeRefreshLayout swrefresh;

    private List<String> mDatas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_collaps);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColorForCollapsingToolbar(this, appBarLayout, collapsingToolBar, toolbar, Color.BLACK);
        initData();
        initRecylerView();
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498650111952&di=d121f50201f142a0c9c7cdcc5ad0d920&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c1d9564da3d76ac7251c946a4b7e.png")
                .into(imgSpecialBj);

    }

    private void initRecylerView(){
        recylerView.setLayoutManager(new LinearLayoutManager(this));
        recylerView.setAdapter(new MyRecylerViewAdaper(this));
    }

    private void initData(){
        mDatas = new ArrayList<String>();
        for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }
    }

    class MyRecylerViewAdaper extends RecyclerView.Adapter<BaseRecylerViewHolder>{

        private Context mContext;

        public MyRecylerViewAdaper(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public BaseRecylerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyRecylerViewHolder(LayoutInflater.from(
                    StatusBarCollapsActivity.this).inflate(R.layout.item_recyler_text, parent,
                    false),mContext);
        }

        @Override
        public void onBindViewHolder(BaseRecylerViewHolder holder, int position) {
            holder.setData(mDatas.get(position),position);
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class MyRecylerViewHolder extends BaseRecylerViewHolder<String>{
            @Bind(R.id.tvText)
            TextView tvText;

            public MyRecylerViewHolder(View itemView, Context mContext) {
                super(itemView, mContext);
            }

            @Override
            public void setData(String object, int position) {
                tvText.setText(object);
            }
        }
    }

}
