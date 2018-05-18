package com.ivivi.aidldemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ivivi.aidldemo.MessageInterface;
import com.ivivi.aidldemo.model.MessageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaokun on 2018/5/18.
 */

public class ClientService extends Service {
    private final  String TAG = this.getClass().getSimpleName();

    private List<MessageInfo> messageInfos = new ArrayList<>();

    private MessageInterface.Stub messageInterface = new MessageInterface.Stub() {
        @Override
        public List<MessageInfo> getMessageInfo() throws RemoteException {
            synchronized (this){
                if (messageInfos != null){
                    return  messageInfos;
                }
                return  new ArrayList<>();
            }
        }

        @Override
        public MessageInfo addMessageInfo(MessageInfo messageInfo) throws RemoteException {
            synchronized (this){
                if (messageInfos == null){
                    messageInfos = new ArrayList<>();

                }
                if (messageInfo == null){
                    messageInfo = new MessageInfo();
                }
                if (!messageInfos.contains(messageInfo)){
                    messageInfos.add(messageInfo);
                }
                Log.e(TAG, "客户传来了数据" + messageInfos.toString());
                MessageInfo mInfo = new MessageInfo();
                mInfo.setMessage("服务端做了修改："+messageInfo.getMessage()+" time:"+(System.currentTimeMillis()));
//                mInfo.setContent("服务端做了修改:" + message.getContent() +"     time:" + + (System.currentTimeMillis()));
                return mInfo;
            }
//            return null;
        }

        @Override
        public int doSum(int a, int b) throws RemoteException {
            synchronized (this){
                Log.e(TAG,"服务端计算的结果:"+(a+b));
                return a+b;
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return messageInterface;
    }

    @Override
    public void onCreate() {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessage("第一条数据");
        messageInfo.setNum(12);
        super.onCreate();
    }
}
