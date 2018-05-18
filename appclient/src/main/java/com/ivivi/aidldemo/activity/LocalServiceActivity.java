package com.ivivi.aidldemo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ivivi.aidldemo.MessageInterface;
import com.ivivi.aidldemo.R;
import com.ivivi.aidldemo.model.MessageInfo;
import com.ivivi.aidldemo.service.ClientService;

import java.util.List;

/**
 * Created by zhaokun on 2018/5/18.
 */

public class LocalServiceActivity extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private TextView tv_modify;

    private List<MessageInfo> messageInfos;
    private boolean mBound = false;
    private MessageInterface messageInterface = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(getLocalClassName(), "完成绑定service服务");
            messageInterface = MessageInterface.Stub.asInterface(iBinder);
            mBound = true;
            if (messageInterface != null) {
                try {
                    messageInfos = messageInterface.getMessageInfo();
//                    messageInterface.doSum(12,10);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(getLocalClassName(), "无法绑定service");
            mBound = false;
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_service);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        tv_modify = findViewById(R.id.tv_modify);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
//                Log.i(getLocalClassName(), content);
                if (!TextUtils.isEmpty(content)) {
                    addMessage(content);
                }
            }
        });

    }

    private void addMessage(String content) {
        //如果与服务端处于未连接状态，先尝试连接
        if (!mBound){
            attemptBindService();
            Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
            return;
        }
        if (messageInterface == null)
            return;
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setMessage(content);

        try {
            messageInfo = messageInterface.addMessageInfo(messageInfo);
            tv_modify.setText(messageInfo.getMessage());
            Log.e(getLocalClassName(), "客户端发送：" + messageInfo.getMessage());

            int i = messageInterface.doSum(100, 98);
            Toast.makeText(LocalServiceActivity.this,"计算结果："+i,Toast.LENGTH_LONG).show();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    //跨进程绑定服务
    private void attemptBindService() {
        Intent intent = new Intent(this, ClientService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attemptBindService();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBound){
            unbindService(serviceConnection);
            mBound = false;
        }
    }

}
