package xin.com.mydemoapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xin.com.mydemoapplication.ui.activity.FullscreenActivity;
import xin.com.mydemoapplication.ui.activity.StatusBarActivity;
import xin.com.mydemoapplication.ui.activity.StatusBarCollapsActivity;
import xin.com.mydemoapplication.ui.activity.StatusBarColorActivity;


public class MainActivity extends AppCompatActivity {

    @Bind(R.id.btn1)
    Button btn1;
    @Bind(R.id.btn2)
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn1, R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                nofity();
                break;
            case R.id.btn2:
                startActivity(new Intent(this, StatusBarActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, FullscreenActivity.class));
                break;
            case R.id.btn4:
                startActivity(new Intent(this, StatusBarColorActivity.class));
                break;
            case R.id.btn5:
                startActivity(new Intent(this, StatusBarCollapsActivity.class));
                break;
        }
    }

    public void nofity() {
        //大视图图片通知
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        //设置显示的时间
        mBuilder.setWhen(System.currentTimeMillis());

        mBuilder.setContentTitle("aaaaaaaaaa😄");
        mBuilder.setContentText("bbbbb😢");
        mBuilder.setTicker("ccccc");
        mBuilder.setAutoCancel(true);
        mBuilder.setSmallIcon(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? R.drawable.home_heart_red : R.drawable.icon_login_head);
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.home_heart_red));
        mBuilder.setColor(0xff888888);
        mBuilder.setSound(Uri.parse("android.resource://" + getPackageName() + "/raw/sound_push.ogg"));

        //通过BigPictureStyle显示大图，这种方式与自定义布局方式显示大图的区别是下滑动画和位置的区别。还有这种方式小米手机不显示setSummaryText设置的值
        NotificationCompat.BigPictureStyle pictureStyle = new NotificationCompat.BigPictureStyle();
        pictureStyle.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.original_car));
        pictureStyle.setBigContentTitle("Custom notification");
        pictureStyle.setSummaryText("This is a BigPictureStyle layout");
        pictureStyle.bigLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.home_heart_red));
        //设置样式
        mBuilder.setStyle(pictureStyle);


        Intent intent = new Intent(this, ImageActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 1, intent, 0);
        //设置点击大图后跳转
        mBuilder.setContentIntent(pIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);

//        // 通过自定义布局方式显示大图
//        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.customer_notitfication_layout);
//        contentView.setImageViewResource(R.id.icon, R.drawable.home_heart_red);
//        contentView.setTextViewText(R.id.title, "Custom notification 😓");
//        contentView.setTextViewText(R.id.text, "This is a custom layout");
//        contentView.setImageViewBitmap(R.id.ivBigPic, BitmapFactory.decodeResource(
//                getResources(),
//                R.drawable.ccar));
//        mBuilder.setCustomBigContentView(contentView);// 1、通知先展示默认aaaa，bbb。下拉之后展示自定义布局
//
        Notification notification = mBuilder.build();

        //2、通过自定义布局notification.bigContentView展示大图
//        if(android.os.Build.VERSION.SDK_INT >= 16) {
//            notification.bigContentView = contentView;// 展开视图
//        }
//        notification.contentView = contentView;// 默认视图，这样赋值都是展示自定义布局，但是同样要下拉出全部

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(2, notification);
    }



    /**
     * //通过自定义布局方式显示大图。项目使用
     getBitmap("", context, new ImageLoader.CallBack() {
    @Override public void onBack(Bitmap b) {
    RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.customer_notitfication_layout);
    contentView.setImageViewResource(R.id.icon, R.mipmap.ic_launcher);
    contentView.setTextViewText(R.id.title, bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE));
    contentView.setTextViewText(R.id.text, alert);
    contentView.setImageViewBitmap(R.id.ivBigPic, b);
    // 1、通知先展示默认aaaa，bbb。下拉之后展示自定义布局
    //                      mBuilder.setCustomBigContentView(contentView);

    Notification notification =  mBuilder.build();

    // 2、通过自定义布局notification.bigContentView展示大图
    if(android.os.Build.VERSION.SDK_INT >= 16) {
    notification.bigContentView = contentView;// 展开视图
    }
    notification.contentView = contentView;// 默认视图，这样赋值都是展示自定义布局，但是同样要下拉出全部

    NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    nm.notify(notifactionId, notification);
    }
    });
     */


//    getBitmap(jsonObject.optString("image"), context, new ImageLoader.CallBack() {
//        @Override
//        public void onBack(Bitmap b) {
//            // 通过自定义布局方式显示大图
//            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.customer_notitfication_layout);
//            contentView.setImageViewResource(R.id.icon, R.drawable.jpush_notification);
//            contentView.setTextViewText(R.id.title, "fdfd");
//            contentView.setTextViewText(R.id.text, "hhhh");
//            contentView.setImageViewBitmap(R.id.ivBigPic, b);
////                            mBuilder.setCustomBigContentView(contentView);// 1、通知先展示默认aaaa，bbb。下拉之后展示自定义布局
//            Notification notification =  mBuilder.build();
//            if(android.os.Build.VERSION.SDK_INT >= 16) {
//                notification.bigContentView = contentView;// 展开视图
//            }
////                             notification.contentView = contentView;// 默认视图，这样赋值都是展示自定义布局，但是同样要下拉出全部
//            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//            nm.notify(notifactionId, notification);
//        }
//    });


}
