package xin.com.mydemoapplication.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zxstatusbarlibrary.utils.StatusBarCompat;

import butterknife.Bind;
import butterknife.ButterKnife;
import xin.com.mydemoapplication.R;

public class StatusBarColorActivity extends Activity {

    @Bind(R.id.ivAdvert)
    ImageView ivAdvert;
    @Bind(R.id.rlAdvert)
    FrameLayout rlAdvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar_color);
        ButterKnife.bind(this);

        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498650111952&di=d121f50201f142a0c9c7cdcc5ad0d920&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c1d9564da3d76ac7251c946a4b7e.png")
                .into(ivAdvert);
//        StatusBarCompat.setStatusBarColor(this, Color.BLUE);
        StatusBarCompat.setStatusBarColor(this, Color.BLUE);
    }
}
