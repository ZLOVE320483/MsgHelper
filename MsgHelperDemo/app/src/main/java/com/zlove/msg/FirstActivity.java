package com.zlove.msg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.zlove.util.Constant;
import com.zlove.util.MsgHelper;
import com.zlove.util.OnMsg;
import com.zlove.util.OnMsgCallback;

/**
 * Created by ZLOVE on 2016/8/26.
 */
public class FirstActivity extends Activity {

    TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_first);
        tvContent = (TextView) findViewById(R.id.content);
        MsgHelper.getInstance().registerMsg(this);

        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstActivity.this, SecondActivity.class));
            }
        });
    }

    @OnMsg(msg = {Constant.NOTIFY_TAG}, useLastMsg = false)
    OnMsgCallback callback = new OnMsgCallback() {
        @Override
        public boolean handleMsg(Message msg) {
            if (msg != null) {
                switch (msg.what) {
                    case Constant.NOTIFY_TAG:
                        String content = (String) msg.obj;
                        tvContent.setText(content);
                        break;
                }
            }
            return false;
        }
    };
}
