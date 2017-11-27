package com.umeng.soexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;

public class MainActivity extends Activity implements View.OnClickListener {

    private TextView tvTextView;

    private UMShareListener mShareListener;
    private ShareAction mShareAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTextView = (TextView) findViewById(R.id.tvTextView);
        tvTextView.setOnClickListener(this);

        mShareListener = new CustomShareListener(this);
        /*增加自定义按钮的分享面板*/
        mShareAction = new ShareAction(MainActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA).setShareboardclickCallback(new ShareBoardlistener() {
            @Override
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                if (snsPlatform.mShowWord.equals("umeng_sharebutton_copy")) {
                    Toast.makeText(MainActivity.this, "复制文本按钮", Toast.LENGTH_LONG).show();
                } else if (snsPlatform.mShowWord.equals("umeng_sharebutton_copyurl")) {
                    Toast.makeText(MainActivity.this, "复制链接按钮", Toast.LENGTH_LONG).show();

                } else if (share_media == SHARE_MEDIA.SMS) {
                    new ShareAction(MainActivity.this).withText("来自分享面板标题").setPlatform(share_media).setCallback(mShareListener).share();
                } else {
                    UMWeb web = new UMWeb("https://www.baidu.com/");
                    web.setTitle("来自分享面板标题");
                    web.setDescription("来自分享面板内容");
                    web.setThumb(new UMImage(MainActivity.this, R.mipmap.ic_launcher));
                    new ShareAction(MainActivity.this).withMedia(web).setPlatform(share_media).setCallback(mShareListener).share();
                }
            }
        });
    }

    private static class CustomShareListener implements UMShareListener {

        private WeakReference<MainActivity> mActivity;

        private CustomShareListener(MainActivity activity) {
            mActivity = new WeakReference(activity);
        }

        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {

            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(mActivity.get(), platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS && platform != SHARE_MEDIA.EMAIL && platform != SHARE_MEDIA.FLICKR && platform != SHARE_MEDIA.FOURSQUARE && platform != SHARE_MEDIA.TUMBLR && platform != SHARE_MEDIA.POCKET && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM && platform != SHARE_MEDIA.GOOGLEPLUS && platform != SHARE_MEDIA.YNOTE && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(mActivity.get(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                }

            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS && platform != SHARE_MEDIA.EMAIL && platform != SHARE_MEDIA.FLICKR && platform != SHARE_MEDIA.FOURSQUARE && platform != SHARE_MEDIA.TUMBLR && platform != SHARE_MEDIA.POCKET && platform != SHARE_MEDIA.PINTEREST

                    && platform != SHARE_MEDIA.INSTAGRAM && platform != SHARE_MEDIA.GOOGLEPLUS && platform != SHARE_MEDIA.YNOTE && platform != SHARE_MEDIA.EVERNOTE) {
                Toast.makeText(mActivity.get(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }

        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {

            Toast.makeText(mActivity.get(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvTextView:
                mShareAction.open();
                break;
        }
    }
}
