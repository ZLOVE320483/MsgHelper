package com.zlove.util;

import com.zlove.app.MsgApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZLOVE on 2016/8/26.
 */
public class McMsgQueuesHandler {

    private int msgCmd;
    private List<MsgCallbackImpl> callbacks;
    private McMsg lastMsg;

    public McMsgQueuesHandler(int cmd) {
        this.msgCmd = cmd;
        callbacks = new ArrayList<>();
    }

    public boolean doLastMsg() {
        if (lastMsg != null) {
            handleMsg(lastMsg);
            return true;
        }
        return false;
    }

    public boolean handleMsg(final McMsg msg) {
        if (msg == null) {
            return false;
        }

        for (final MsgCallbackImpl callback : callbacks) {
            if (callback.isRunInUI()) {
                MsgApplication.getInstance().post(new Runnable() {
                    public void run() {
                        callback.handleMsg(msg.message);
                    }
                });
            } else {
                callback.handleMsg(msg.message);
            }
        }
        lastMsg = msg;
        return false;
    }

    public int getMsgCmd() {
        return msgCmd;
    }

    /**
     * 添加回调
     * @param callback
     * @return 添加成功返回true，如果存在，则添加失败，返回false
     */
    public boolean addCallback(MsgCallbackImpl callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
            return true;
        }
        return false;
    }

    public void remove(OnMsgCallback callback) {
        if (callback == null) {
            return;
        }
        for (int i = callbacks.size() - 1; i >= 0; i--) {
            MsgCallbackImpl impl = callbacks.get(i);
            if (impl.callback.equals(callback)) {
                callbacks.remove(i);
            }
        }
    }

    public void clearCallback() {
        callbacks.clear();
    }

    public void clearLastMsg() {
        lastMsg = null;
    }

    public boolean contains(OnMsgCallback callback) {
        return callbacks.contains(callback);
    }

    public List<MsgCallbackImpl> getCallbacks() {
        return callbacks;
    }
}
