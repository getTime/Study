package com.yyx.study.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yyx.study.R;
import com.yyx.study.video.FullScreenPlayVideoActivity;
import com.yyx.study.video.VideoActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPlayVideo(View view) {
        startActivity(new Intent(this, VideoActivity.class));
    }

    public void onAutoLayout(View view) {
        startActivity(new Intent(this, AutoLayoutActivity.class));
    }
}

