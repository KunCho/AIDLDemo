##### 关于Android 开发中AIDL 用比较通俗性的表述

AIDL 就是客户端调用服务端现有的方法，完成某些操作。假如是这样，现在有一个项目中提供了比较成熟的计算的方法，而现在我想开发一款软件其中一个模块想用到一个计算类，而我又不想重新写了，那么就可以通过AIDL 实现。
Android 系统进程间不能共享内存，两个不同应用之间需要进行通讯的话就要使用AIDL。

比如公司的一个项目要更新，产品的需求是依附于当前项目开发一个插件，但是呢这个插件功能以及界面比较复杂，不能和当前项目在一个进程中，同时呢，还要用到当前项目中已经写好了的一些东西，那么因为新开发的依附于当前项目的插件和当前项目不是一个进程，因此不能共享内存，就出现了问题，于是，需要提供一些机制在不同进程之间进行数据通信，这个机制就是 AIDL 了。
AIDL 中代码方法传参时除了Java 基本类型以及String、CharSequence 之外的类型，都需要在前面加上定向tag，具体加什么，根据需求而定。

AIDL 中的定向tag (in out inout)

AIDL中的定向 tag 表示了在跨进程通信中数据的流向，其中 in 表示数据只能由客户端流向服务端， out 表示数据只能由服务端流向客户端，而 inout 则表示数据可在服务端与客户端之间双向流通。其中，数据流向是针对在客户端中的那个传入方法的对象而言的。in 为定向 tag 的话表现为服务端将会接收到一个那个对象的完整数据，但是客户端的那个对象不会因为服务端对传参的修改而发生变动；out 的话表现为服务端将会接收到那个对象的的空对象，但是在服务端对接收到的空对象有任何修改之后客户端将会同步变动；inout 为定向 tag 的情况下，服务端将会接收到客户端传来对象的完整信息，并且客户端将会同步服务端对该对象的任何变动。

使用AIDL 来通过bindService()进行线程间通信的步骤：

- 服务端创建一个AIDL 文件，将暴露给客户端的接口在里面声明；
- 在service 中实现这些接口；
- 客户端绑定服务端，并将onServiceConnected() 得到的IBinder 转为AIDL 生成的IInterface 实例；
- 通过得到的实例调用其暴露的方法。