package com.yyx.study.video;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.yyx.study.R;
import com.yyx.study.util.DisplayManager;

public class VideoActivity extends Activity {
    private RelativeLayout videoRoot;
    private final static String
            VIDEO_URL = "http://flv3.bn.netease.com/videolib3/1707/12/UqWOA7815/SD/UqWOA7815-mobile.mp4";
    private VideoPlayState mCurrPlayState;
    private boolean mIsFirstLoad = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoRoot = (RelativeLayout) findViewById(R.id.video_root);
        findViewById(R.id.iv_video_item_play_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoPlayerHelper.getInstance().play(videoRoot, VIDEO_URL);
            }
        });
        VideoPlayerHelper.init(this);

//        initView();
    }

    //以宽高比16:9的比例设置播放器的尺寸
    private void initView() {
        int width = DisplayManager.screenWidthPixel(this);
        int height = (int) (width * 1.0f / 16 * 9 + 0.5f);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        videoRoot.setLayoutParams(params);
    }

    @Override
    protected void onDestroy() {
        VideoPlayerHelper.getInstance().stop();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (!VideoPlayerHelper.isFullScreen) {
            VideoPlayerHelper.getInstance().pause();
        }
        super.onPause();
    }
}
