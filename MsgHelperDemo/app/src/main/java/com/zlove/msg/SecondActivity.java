package com.zlove.msg;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zlove.util.Constant;
import com.zlove.util.McMsg;
import com.zlove.util.MsgHelper;

/**
 * Created by ZLOVE on 2016/8/26.
 */
public class SecondActivity extends Activity {

    private Button btnNotify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_second);

        btnNotify = (Button) findViewById(R.id.notify);
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String what = "I am a boy";
                McMsg mcMsg = McMsg.newInstance(Constant.NOTIFY_TAG, what);
                MsgHelper.getInstance().sendMsg(mcMsg);
            }
        });
    }
}
