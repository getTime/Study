package com.yyx.study.video;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.yyx.study.R;
import com.yyx.study.receiver.HomeWatcherReceiver;



public class FullScreenPlayVideoActivity extends AppCompatActivity
        implements VideoPlayerView.ExitFullScreenListener {

    private VideoPlayState mCurrPlayState;
    private ViewGroup mParent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_play_video);
        mParent = (ViewGroup) findViewById(R.id.root);
        VideoPlayerHelper.init(this);
        VideoPlayerHelper.getInstance().fullScreen(mParent, this);
    }

    @Override
    protected void onPause() {
        unregisterHomeKeyReceiver(this);
        super.onPause();
        mCurrPlayState = VideoPlayerHelper.getInstance().getVideoPlayState();
        VideoPlayerHelper.getInstance().pause();
    }

    @Override
    protected void onResume() {
        registerHomeKeyReceiver(this);
        super.onResume();

        if (mCurrPlayState == VideoPlayState.PLAY) {
            VideoPlayerHelper.getInstance().play();
        }
    }

    @Override
    public void onBackPressed() {
        VideoPlayerHelper.getInstance().exitFullScreen(mCurrPlayState);
        this.finish();
    }

    @Override
    public void exitFullScreen() {
        finish();
    }

    private static HomeWatcherReceiver mHomeKeyReceiver = null;

    private static void registerHomeKeyReceiver(Context context) {
        Log.i("registerHome", "registerHomeKeyReceiver");
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        context.registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    private static void unregisterHomeKeyReceiver(Context context) {
        Log.i("registerHome", "unregisterHomeKeyReceiver");
        if (null != mHomeKeyReceiver) {
            context.unregisterReceiver(mHomeKeyReceiver);
        }
    }
}
