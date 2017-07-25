package com.yyx.study.main;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.yyx.study.R;
import com.yyx.study.view.AutoNewLineLayout;

/**
 * FlowLayout 流式布局
 */
public class AutoLayoutActivity extends Activity {

    AutoNewLineLayout autoNewLineLayout;


    String[] tags = {"诛仙", "青云志" , "老九门", "花千骨", "琅琊榜", "伪装者", "仙剑奇侠传",
            "诛仙", "青云志" , "老九门", "花千骨", "琅琊榜", "伪装者", "仙剑奇侠传仙剑奇侠传仙剑奇侠传仙剑奇侠传仙剑奇侠传仙剑奇侠传"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_layout);
        autoNewLineLayout = (AutoNewLineLayout) findViewById(R.id.anl_tags);

        addTags();
    }

    private void addTags() {
        for (int i = 0; i < tags.length; i++) {
            TextView tv = new TextView(this);
            tv.setBackgroundResource(R.drawable.radius_border_teal_solod_white);
            tv.setText(tags[i]);
            autoNewLineLayout.addView(tv);
        }
    }

}
