package com.nuance.sample.voconsample.ssdp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.nuance.sample.voconsample.receiver.IntentKey;
import com.nuance.sample.voconsample.receiver.ReceiverAction;
import com.nuance.sample.voconsample.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HuaweiSmartGateway {

    // 广播地址
    private static final String TAG = "HuaweiSmartGateway";
    private Context mContext;
    private LanSend lSend;
    private MyReceiver mReceiverResult;
    private boolean work = false;
    private Handler mHander = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 4:
                    Toast.makeText(mContext, (String) msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    try {
                        String smarthomeCMD = "{\"location\":\"\",\"action\":\"wet\",\"deviceName\":\"humidifier\"}";
                        JSONObject smarthomejs = new JSONObject();
                        smarthomejs.put("datatype", "smarthome_data");
                        smarthomejs.put("datacontent", smarthomeCMD.trim());
                        sendMsg(smarthomejs);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                   
                    break;
            }

        }
    };

    public HuaweiSmartGateway(Context contex) {
        this.mContext = contex;
    }

    public void init() {
        // 针对华为智能网关
        SSDPNotify();
        initServerSocket();
        registerResult();
    }

    public void destroy() {
        unRegisterBroadcastResult();
        mHander.removeMessages(5);
    }

    private void registerResult() {
        if (mReceiverResult == null) {
            mReceiverResult = new MyReceiver();
        }
        Logger.d(TAG, "注册---识别结果广播");
        IntentFilter filter = new IntentFilter();
        filter.addAction(ReceiverAction.ACTION_HEALTH_MANAGEMENT_DATA);
        filter.addAction(ReceiverAction.ACTION_SMARTHOME_CMD);
        mContext.registerReceiver(mReceiverResult, filter);
    }

    private void unRegisterBroadcastResult() {
        if (mReceiverResult != null) {
            Logger.d(TAG, "反注册---识别结果广播");
            mContext.unregisterReceiver(mReceiverResult);
        }
    }

    /**
     * 监听ssdp查询服务
     */
    public void SSDPNotify() {
        if (lSend == null) {
            try {
                lSend = new LanSend(mContext);
                lSend.join(); // 加入组播，并创建线程侦听
            } catch (Exception e) {
                System.out.println("*****加入组播失败*****");
            }
        }
    }

    /**
     * 初始化小优端ServerSocket
     */
    private ServerSocket mServerSocket;
    private Socket socket;

    public void initServerSocket() {
        if (mServerSocket == null) {
            new Thread() {
                public void run() {
                    try {
                        mServerSocket = new ServerSocket(18744);
                        while (true) {
                            Log.e(TAG, "111111111");
                            if (mDataInputStream != null) {
								mDataInputStream = null;
								Log.e(TAG, "mDataInputStream == null");
							}
                            if (out != null) {
								out = null;
								Log.e(TAG, "out == null");
							}
                            if (socket!=null) {
								socket = null;
								Log.e(TAG, "socket == null");
							}
                            work = false;
                            socket = mServerSocket.accept();
                            handle(socket);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }

    }

    private DataOutputStream out;
    private DataInputStream mDataInputStream;

    void handle(Socket socket) throws IOException {
        Log.e("myrobotSocket", "new socket");
        try {
        	work = true;
            out = new DataOutputStream(socket.getOutputStream());
            mDataInputStream = new DataInputStream(socket.getInputStream());
            while (work) {
                handleMsg();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "IOException", e);
//            out.close();
//            mDataInputStream.close();
//            socket.close();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "JSONException", e);
        } catch (Throwable e) {
            Log.e(TAG, "Failed to handle", e);
        }
    }

    private void handleMsg() throws IOException, JSONException {

         final String cmd = mDataInputStream.readUTF();
        JSONObject jsonO2 = new JSONObject(cmd);
        String type = jsonO2.getString("type");
        String action_cmd = jsonO2.getString("action_cmd");
        Log.e("myrobotSocket", "type : " + type + "  ::: cmd : " + action_cmd);
        if (type != null && action_cmd != null && type.equals("test")
                && action_cmd.equals("blog")) {
            JSONObject datajs = new JSONObject();
            datajs.put("datatype", "test");
            datajs.put("datacontent", "alive");
            this.sendMsg(datajs);
        }


        if ("001".equals(type)) {// 底盘动作指令结果
            String go_cmd = null;

            {
                if (action_cmd.equals("front")) {
                    go_cmd = "8a";
                } else if (action_cmd.equals("back")) {
                    go_cmd = "8b";
                } else if (action_cmd.equals("left")) {
                    go_cmd = "a4";
                } else if (action_cmd.equals("right")) {
                    go_cmd = "a5";
                }
            }
            if (go_cmd != null) {
                int action = Integer.valueOf(go_cmd, 16);
                //此处执行机器人的动作，调用对应的机器人动作代码
//                 MainApplication.getInstance().sendActionCmd((byte) action);
                if(myOnTTSListener!=null){
                    myOnTTSListener.OnMotion((byte) action);
                }
                Log.e("myrobotSocket", "底盘 : " + action);
            }
        } else if ("002".equals(type)) {// 头部、手部动作指令结果
            String go_cmd = null;
            {
                if (action_cmd.equals("up")) {
                    go_cmd = "0a";
                } else if (action_cmd.equals("down")) {
                    go_cmd = "0b";
                } else if (action_cmd.equals("left")) {
                    go_cmd = "07";
                } else if (action_cmd.equals("right")) {
                    go_cmd = "06";
                } else if (action_cmd.equals("reset")) {
                    go_cmd = "09";
                }
            }

            if (go_cmd != null) {
                int action = Integer.valueOf(go_cmd, 16);
                //此处执行机器人的动作，调用对应的机器人动作代码
//                 MainApplication.getInstance().sendActionCmd((byte) action);
                if(myOnTTSListener!=null){
                    myOnTTSListener.OnMotion((byte) action);
                }
                Log.e("myrobotSocket", "头部或手部 : " + action);
            }
        } else if ("003".equals(type)) {// 播放插件响应,返回结果
            JSONObject jsonCMD = new JSONObject(action_cmd);
            Log.e(TAG, " action_cmd = " + action_cmd);
            String rsp = jsonCMD.getString("msg");
            String content = jsonCMD.getString("content");
            if (rsp != null && content != null) {

                if (rsp.equals("result") && !content.isEmpty()) {// 控制智能家居返回的结果
                	if ("open".equals(content)) {
                		 //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//                		SendEventUtils.sendPlayTTS(mContext, "Sir, The door has been opened.", VoiceState.AccessRecognizer);
                        if(myOnTTSListener!=null){
                            myOnTTSListener.OnTTS("Sir, The door has been opened.");
                        }
					}  else if ("read".equals(content)) {
						 //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//						SendEventUtils.sendPlayTTS(mContext, "Sir, Has been for you to read mode.", VoiceState.AccessRecognizer);
                        if(myOnTTSListener!=null){
                            myOnTTSListener.OnTTS("Sir, Has been for you to read mode.");
                        }
					} else if (content != null && content.startsWith("dry:")) {
						content = content.substring("dry:".length());
						content = content + "，Some dry air, it's for you to open the humidifier";
						 //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//						SendEventUtils.sendPlayTTS(mContext, content, VoiceState.AccessRecognizer);
                        if(myOnTTSListener!=null){
                            myOnTTSListener.OnTTS(content);
                        }
					} else if (content != null && !content.isEmpty()) {
						 //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//						SendEventUtils.sendPlayTTS(mContext, content, VoiceState.AccessRecognizer);
                        if(myOnTTSListener!=null){
                            myOnTTSListener.OnTTS(content);
                        }
					}
                    if (content.startsWith("dry:")) {
                        mHander.sendEmptyMessageDelayed(5, 6000);
                        Log.e(TAG, " response dry " );
                    }
                } else if (rsp.equals("alarm") && !content.isEmpty()) {// 网关的警告事件处理
                    
                    if ("doorbell".equals(content)) {
                    	 //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//                    	SendEventUtils.sendPlayTTS(mContext, "Sir, the guest is arriving.", VoiceState.AccessRecognizer);
                        if(myOnTTSListener!=null){
                            myOnTTSListener.OnTTS("Sir, the guest is arriving.");
                        }
					} else if ("welcome".equals(content)) {
						 //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//						SendEventUtils.sendPlayTTS(mContext, "Hello, I'm the smart home assistant, xiaoyo. Welcome to the Bonn Demo House. Please come join me for a journey into our smart home!", VoiceState.AccessRecognizer);
                        if(myOnTTSListener!=null){
                            myOnTTSListener.OnTTS("Hello, I'm the smart home assistant, xiaoyo. Welcome to the Bonn Demo House. Please come join me for a journey into our smart home!");
                        }
                    } else if ("emergency".equals(content)) {
						 //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//						SendEventUtils.sendPlayTTS(mContext, "Sir, there is an emergency. Please look into it immediately.", VoiceState.AccessRecognizer);
                        if(myOnTTSListener!=null){
                            myOnTTSListener.OnTTS("Sir, there is an emergency. Please look into it immediately.");
                        }
					} else if ("window".equals(content)) {
						 //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//						SendEventUtils.sendPlayTTS(mContext, "Sir, The window is opened, do you need to check it?", VoiceState.AccessRecognizer);
                        if(myOnTTSListener!=null){
                            myOnTTSListener.OnTTS("Sir, The window is opened, do you need to check it?");
                        }
					} 
                    
                    
                } else if (rsp.equals("syncinfor") && !content.isEmpty()) {
//                    Intent i = new Intent(
//                    		ReceiverAction.ACTION_OPENLIFE_SYNC_INFOR);
//                    i.putExtra("sync_infor", content);
//                    mContext.sendBroadcast(i);
                }
            }

        }
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "onReceive  " + action);
            if (ReceiverAction.ACTION_HEALTH_MANAGEMENT_DATA.equals(action)) {// 上报健康管理数据
                if (out != null && socket != null) {
                    try {
                        String healthData = intent
                                .getStringExtra("health_data");
                        Log.e(TAG, "healthData =  " + healthData);
                        JSONObject datajs = new JSONObject();
                        JSONObject healthjs = new JSONObject(healthData.trim());
                        datajs.put("datatype", "health_data");
                        datajs.put("datacontent", healthjs);
                        String device = healthjs.getString("device").trim();
                        Log.e(TAG, "device =  " + device);
                        if (device.equals("BloodPressure")) {
                            sendMsg(datajs);
                        } else if (device.equals("Temperature")) {
                            sendMsg(datajs);
                            Log.e("myrobotSocket", "healthData =  "
                                    + healthData);
                            Log.e("myrobotSocket",
                                    "datajs =  " + datajs.toString());
                        } else if (device.equals("Weighting")) {
                            sendMsg(datajs);
                        }

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } 
            } else if (ReceiverAction.ACTION_SMARTHOME_CMD.equals(action)) {// 智能家居命令上报
                if (out != null && socket != null) {
                    try {
                        String smarthomeCMD = intent
                                .getStringExtra(IntentKey.KEY_SMARTHOME_CMD);
                        String voice = intent
                                .getStringExtra(IntentKey.KEY_VOICE);
                        String voiceContent = intent
                                .getStringExtra(IntentKey.KEY_VOICECONTENT);
                        Log.e(TAG, "smarthomeCMD =  " + smarthomeCMD);
                        JSONObject smarthomejs = new JSONObject();
                        smarthomejs.put("datatype", "smarthome_data");
                        smarthomejs.put("datacontent", smarthomeCMD.trim());
                        sendMsg(smarthomejs);
                        if (null!=voice&&voiceContent!=null&&!voice.isEmpty()&&!voiceContent.isEmpty()) {
                        	if ("tts".equals(voice)) {
                        		 //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//                        		SendEventUtils.sendPlayTTS(mContext, voiceContent, VoiceState.AccessRecognizer);
                                if(myOnTTSListener!=null){
                                    myOnTTSListener.OnTTS(voiceContent);
                                }
    						} else if ("sound".equals(voice)) {
//    							SendEventUtils.startRecognizer(mContext);
    						} 
						}
                        Log.e(TAG, "上报智能家居指令成功 ");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        Log.e(TAG, "网关不通，上报智能家居指令失败 ");
                        //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//                        SendEventUtils.sendPlayTTS(mContext, "Control failure.", VoiceState.AccessRecognizer);
                        if(myOnTTSListener!=null){
                            myOnTTSListener.OnTTS("Control failure.");
                        }
                    }
                } else {
                    Log.e(TAG, "socket=null，上报智能家居指令失败 ");
                    //此处执行机器人的语言处理，调用对应的机器人语言出来代码
//                    SendEventUtils.sendPlayTTS(mContext, "Control failure.", VoiceState.AccessRecognizer);
                    if(myOnTTSListener!=null){
                        myOnTTSListener.OnTTS("Control failure.");
                    }
                }
            }

        }

    };

    static final short head = 99;
    static final short end = 88;

    private synchronized void sendMsg(JSONObject datajs) throws IOException {
        if (datajs == null) {
            return;
        }
        if (out!=null) {
            String str=datajs.toString();
            if (str!=null) {
                out.writeUTF(datajs.toString());
                out.flush();
            }
            
        }
      
      
    }

    private OnTTSListener myOnTTSListener=null;
    public void setOnTTSListener(OnTTSListener mOnTTSListener){
        this.myOnTTSListener=mOnTTSListener;
    }
}