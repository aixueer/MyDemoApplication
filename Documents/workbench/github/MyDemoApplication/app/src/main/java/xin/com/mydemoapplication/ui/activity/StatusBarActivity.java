package xin.com.mydemoapplication.ui.activity;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
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

/**
 * 相当于启动图广告。
 * 1、将背景添加到主题中，可以优化启动速度
 * 2、设置加载图片成功之后，顶部状态栏透明，图片顶上去需要设置
 * 继承 Theme.AppCompat.Light.NoActionBar的主题。否则会有actionbar
 * 3、使用StatusBarCompat工具类
 * 如果在application中设置了继承了Theme.AppCompat.Light.NoActionBar的主题，则可以不在activity单独设置。如果有单独需求可单独设置
 */
public class StatusBarActivity extends Activity {

    @Bind(R.id.ivAdvert)
    ImageView ivAdvert;

    // 适配带有虚拟返回键的手机，否则设置了透明状态栏之后，虚拟返回键遮挡slogan。如果有侧滑返回应该就不用处理了，侧滑返回的代码里处理了。
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        TypedArray a = getTheme().obtainStyledAttributes(new int[]{
                android.R.attr.windowBackground
        });
        int background = a.getResourceId(0, 0);
        a.recycle();
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        decorChild.setBackgroundResource(background);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_bar);
        ButterKnife.bind(this);

        // https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498650111952&di=d121f50201f142a0c9c7cdcc5ad0d920&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c1d9564da3d76ac7251c946a4b7e.png
        // http://c3.xinstatic.com/f1/20170627/1146/5951d504eea80277636.gif
        Glide.with(this)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498650111952&di=d121f50201f142a0c9c7cdcc5ad0d920&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01c1d9564da3d76ac7251c946a4b7e.png")
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        StatusBarCompat.translucentStatusBar(StatusBarActivity.this,true);
                        return false;
                    }

                })
                .into(ivAdvert);
//        ivAdvert.setImageResource(R.drawable.bg_default);
//        StatusBarCompat.translucentStatusBar(StatusBarActivity.this,true);

    }
}
