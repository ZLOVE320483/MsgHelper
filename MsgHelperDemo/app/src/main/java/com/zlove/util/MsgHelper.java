package com.zlove.util;

import android.util.SparseArray;

import java.lang.reflect.Field;

/**
 * Created by ZLOVE on 2016/8/26.
 */
public class MsgHelper {

    private static MsgHelper instance = new MsgHelper();

    public static MsgHelper getInstance() {
        return instance;
    }

    private SparseArray<McMsgQueuesHandler> msgQueuesHandlerMap;

    private MsgHelper() {
        msgQueuesHandlerMap = new SparseArray<>();
    }


    public void sendMsg(int cmd) {
        sendMsg(McMsg.newInstance(cmd));
    }

    McMsgQueuesHandler getListener(int cmd) {
        McMsgQueuesHandler msgQueueHandle = msgQueuesHandlerMap.get(cmd);
        if (msgQueueHandle == null) {
            msgQueueHandle = new McMsgQueuesHandler(cmd);
            msgQueuesHandlerMap.put(cmd, msgQueueHandle);
        }
        return msgQueueHandle;
    }

    public void sendMsg(McMsg mm) {
        final McMsg temp = mm;
        if (mm != null) {
            ThreadWorker.execute(new Runnable() {
                public void run() {
                    McMsgQueuesHandler msgQueueHandle = getListener(temp.msgCmd);
                    msgQueueHandle.handleMsg(temp);
                }
            });
        }
    }

    public void registerMsg(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                OnMsg onMsg = field.getAnnotation(OnMsg.class);
                if (onMsg != null) {
                    int[] msgs = onMsg.msg();
                    field.setAccessible(true);
                    try {
                        Object value = field.get(obj);
                        if (value instanceof OnMsgCallback) {
                            OnMsgCallback callback = (OnMsgCallback) value;
                            registMsg(msgs, callback, onMsg);
                        } else {
                            System.out.println(field.getName() + " in " + obj + " is not MsgCallback instance.");
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param cmds   message type to be annotated
     *
     * @param callback onMsgCallback to be register
     *
     * @param onMsg     total @interface of OnMsg
     */
    public void registMsg(int[] cmds, OnMsgCallback callback, OnMsg onMsg) {
        for (int cmd : cmds) {
            McMsgQueuesHandler msgQueueHandle = getListener(cmd);
            MsgCallbackImpl wrap = new MsgCallbackImpl(cmd, onMsg, callback);
            msgQueueHandle.addCallback(wrap);
            if (onMsg.useLastMsg()) {
                msgQueueHandle.doLastMsg();
            }
        }
    }

    public void unRegistMsg(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                OnMsg onMsg = field.getAnnotation(OnMsg.class);
                if (onMsg != null) {

                    int[] msgs = onMsg.msg();

                    field.setAccessible(true);
                    try {
                        Object value = field.get(obj);
                        if (value instanceof OnMsgCallback) {
                            OnMsgCallback callback = (OnMsgCallback) value;
                            unRegistMsg(msgs, callback);
                        } else {
                            System.out.println(field.getName() + " in " + obj + " is not MsgCallback instance.");
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void unRegistMsg(int[] cmds, OnMsgCallback callback) {
        for (int cmd : cmds) {
            McMsgQueuesHandler msgQueueHandle = getListener(cmd);
            msgQueueHandle.remove(callback);
        }
    }

    public void clearMsgs() {
        for (int i = 0; i < msgQueuesHandlerMap.size(); i++) {
            int key = msgQueuesHandlerMap.keyAt(i);
            McMsgQueuesHandler hanlde = msgQueuesHandlerMap.get(key);
            if (hanlde != null) {
                hanlde.clearLastMsg();
            }
        }
    }

    public void clearCallbacks() {
        msgQueuesHandlerMap.clear();
    }
}
