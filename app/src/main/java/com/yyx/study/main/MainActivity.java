package com.yyx.study.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yyx.study.R;
import com.yyx.study.video.FullScreenPlayVideoActivity;
import com.yyx.study.video.VideoActivity;

public class MainActivity extends Activity implements TextWatcher, View.OnKeyListener {
    private EditText etContent;
    /**
     * 记录下上次的文本长度
     */
    private int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etContent = (EditText) findViewById(R.id.et_content);
        etContent.addTextChangedListener(this);
        etContent.setOnKeyListener(this);
    }

    public void onPlayVideo(View view) {
        startActivity(new Intent(this, VideoActivity.class));
    }

    public void onAutoLayout(View view) {
        startActivity(new Intent(this, AutoLayoutActivity.class));
    }

    /**
     * 核心步骤
     * 主要通过SpannableString来实现标签分组
     **/
    private void onSetSpan() {
        String content = etContent.getText().toString();
        SpannableString spannable = new SpannableString(content);
        //通过空格来区分标签
        String[] m = content.split(" ");
        int start = 0;
        int end;

        for (String str : m) {
            end = start + str.length();
            spannable.setSpan(new BackgroundColorSpan(Color.BLUE), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(Color.WHITE), start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        etContent.setText(spannable);
        //设置完成后 需要把焦点移动到最后一位
        etContent.setSelection(spannable.length());
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        length = s.length();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (length == s.length() || length == 0) {
            return;
        }
        String content = s.toString();
        int length = content.length();
        if (length == 0) {
            return;
        }
        String last = content.substring(length - 1, length);
        if (" ".equals(last)) {
            onSetSpan();
        }
    }

    /**
     * 监听返回键，按照标签组删除
     *
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        String content = etContent.getText().toString();
        if (content.length() > 0) {
            String last = content.substring(content.length() - 1, content.length());
            if (keyCode == KeyEvent.KEYCODE_DEL && last.equals(" ")) {
                String[] m = content.split(" ");
                String lastTag = m[m.length-1];
                //content.length()-1 为了去除空格
                content = content.substring(0, content.length()-1 - lastTag.length());
                etContent.setText(content);
            } else if (keyCode == KeyEvent.KEYCODE_DEL && !last.equals(" ")) {
                content = content.substring(0, content.length() - 1);
                etContent.setText(content);
            }
        }
        etContent.setSelection(content.length());
        return true;
    }
}

