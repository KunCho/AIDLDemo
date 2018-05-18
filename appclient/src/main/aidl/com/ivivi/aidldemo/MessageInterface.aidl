// MessageInterface.aidl
package com.ivivi.aidldemo;

// Declare any non-default types here with import statements
import com.ivivi.aidldemo.model.MessageInfo;

interface MessageInterface {
    //返回值前不需要加任何任何东西
    List<MessageInfo>getMessageInfo();

    MessageInfo addMessageInfo(inout MessageInfo messageInfo);

    int doSum(int a,int b);
}
