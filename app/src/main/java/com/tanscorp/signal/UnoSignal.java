package com.tanscorp.signal;


import com.tanscorp.voice.results.EngineTextResult;

import java.util.List;

/**
 * Created by Ken on 11/22/16.
 */

public class UnoSignal {
    public static final String SUBTYPE = "kind";
    public static final int EVENT_SERVICESTART = 111;

    public static final int VERSION_1 = 1;
    public static final int KIND_EVENT = 1; // Signal send from service
    public static final int KIND_DIRECTIVE = 2; // Signal send to service
    public static final int EVENT_STATE = 1;
    public static final int EVENT_ERROR = 2;
    public static final int EVENT_BATTERY = 20;
    public static final int EVENT_CHARGING = 21;
    public static final int EVENT_CHARGE_CANCEL = 22;
    public static final int EVENT_HEAD = 51;
    public static final int EVENT_NECK = 52;
    public static final int EVENT_RIGHT_HAND = 53;
    public static final int EVENT_LEFT_HAND = 54;
    public static final int EVENT_RIGHT_EAR = 55;
    public static final int EVENT_LEFT_EAR = 56;
    public static final int EVENT_CHEST = 57;
    public static final int EVENT_ARSE = 58;
    /**系统设置发生变更，如语言*/
    public static final int EVENT_LOCAL_CHANGE = 60;

    public static final int DIRECTIVE_HEAD = 10;
    public static final int DIRECTIVE_HAND = 11;
    public static final int DIRECTIVE_WHEEL = 21;
    private final int version;
    //语音相关
    public static final int DIRECTIVE_SLEEP = 1; //进入唤醒
    public static final int DIRECTIVE_END_SLEEP = 2; //关闭唤醒监听，主要用在按钮点击小房子
    public static final int DIRECTIVE_RECOGNIZE = 3;//开始识别
    public static final int DIRECTIVE_RECOGNIZE_RELEASE = 4;//关闭识别监听
    public static final int EVENT_VOICE = 5;  //识别结果
    public static final int EVENT_SPOKEN_VOICE = 6;  //识别结果对应对话结果
    public static final int EVENT_END_SLEEP = 61; //唤醒成功
    public static final int EVENT_START_WAKEUP = 62; //开始识别
    public static final int EVENT_END_WAKEUP = 65; //停止识别，等待识别结果
    public static final int DIRECTIVE_SENSOR_CMD = 63;//运动指令
    public static final int DIRECTIVE_UNCATCH_SENSOR = 64;//进入指哪打哪不处理Sensor触摸
    public static final int DIRECTIVE_RECOGNIZE_DESTORY=70;//销毁语音引擎
    public static final int DIRECTIVE_GO_WAKEUP = 71;//启动到唤醒页面

    public static final int DIRECT_VOICE_TEXT_TO_SPEECH = 101;//语音合成指令请求
    public static final int EVENT_VOICE_TEXT_TO_SPEECH = 201;//语音合成处理结果

    private final int kind;
    private final int type;
    private int value;
    private Object data;
    private List<EngineTextResult> engineResults;
    private String textResult;
    private  byte cmd;//运动信号传过来的指令。

    public byte getCmd() {
        return cmd;
    }

    public void setCmd(byte cmd) {
        this.cmd = cmd;
    }

    public String getTextResult() {
        return textResult;
    }

    public void setTextResult(String textResult) {
        this.textResult = textResult;
    }

    public UnoSignal(int version, int kind, int type) {
        this.version = version;
        this.kind = kind;
        this.type = type;
    }

    public List<EngineTextResult> getEngineResults() {
        return engineResults;
    }

    public void setEngineResults(List<EngineTextResult> engineResults) {
        this.engineResults = engineResults;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getVersion() {
        return version;
    }

    public int getKind() {
        return kind;
    }

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public Object getData() {
        return data;
    }
}
