package com.zlove.util;

import android.os.Message;

/**
 * Created by ZLOVE on 2016/8/26.
 */
public class MsgCallbackImpl implements OnMsgCallback {

    int msgCmd;
    OnMsg onMsg;
    OnMsgCallback callback;

    public MsgCallbackImpl(int msgCmd, OnMsg onMsg, OnMsgCallback callback) {
        super();
        this.msgCmd = msgCmd;
        this.onMsg = onMsg;
        this.callback = callback;
    }

    public boolean isRunInUI() {
        return onMsg.ui();
    }

    @Override
    public boolean handleMsg(Message msg) {
        return callback.handleMsg(msg);
    }
}
