package com.zlove.util;

import android.os.Message;

/**
 * Created by ZLOVE on 2016/8/26.
 */
public class McMsg {

    public long time;
    public final int msgCmd;
    public Message message;

    private McMsg(int cmd) {
        msgCmd= cmd;
    }

    @Override
    public String toString() {
        return "McMsg{" +
                "time=" + time +
                ", msgCmd=" + msgCmd +
                ", message=" + message +
                '}';
    }

    public static McMsg newInstance(int cmd) {
        return newInstance(cmd, null);
    }

    public static McMsg newInstance(int cmd, Object data) {
        McMsg mm = new McMsg(cmd);
        mm.time = System.currentTimeMillis();
        Message msg = new Message();
        msg.obj = data;
        msg.what = cmd;
        mm.message = msg;
        return mm;
    }
}
