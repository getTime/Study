package com.yyx.study.main;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.yyx.study.R;
import com.yyx.study.edittext.LvEtActivity;
import com.yyx.study.video.FullScreenPlayVideoActivity;
import com.yyx.study.video.VideoActivity;

public class MainActivity extends Activity implements TextWatcher, View.OnKeyListener {
    private EditText etContent;
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
    public void onLvEt(View view){
        startActivity(new Intent(this, LvEtActivity.class));
    }

    /**
     * 请求权限
     *
     * @param view
     */
    public void onRequestPermissions(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            //有权限
            doNext("相机权限已经开启");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA);
        }
    }

    private static final int CAMERA = 2;

    private void doNext(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //有权限
                doNext("谢谢授权");
            } else {
                showExplainDialog();
                // Permission Denied
                Toast.makeText(MainActivity.this, "您没有授权该权限，请在设置中打开授权", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 解释为什么要申请权限
     */
    private void showExplainDialog() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("再次申请权限")
                    .setMessage("你拒绝我，没法玩啊！")
                    .setPositiveButton("授权", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA);
                        }
                    })
                    .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA);
        }
    }


    //<------------------------------------edittext 分割数据------------------------------------------------>
    /**
     * 记录下上次的文本长度
     */
    private int length = 0;


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
        if (keyCode == KeyEvent.KEYCODE_DEL
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (content.length() > 0) {
                String last = content.substring(content.length() - 1, content.length());
                if (!" ".equals(last)) {
                    return false;
                }
                String[] m = content.split(" ");
                String lastTag = m[m.length - 1];
                //content.length()-1 为了去除空格
                content = content.substring(0, content.length() - 1 - lastTag.length());
                etContent.setText(content);
                etContent.setSelection(content.length());
                return true;
            }
        }
        return false;
    }
}

