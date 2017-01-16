package com.tanscorp.signal;

/**
 * Created by Ken on 11/24/16.
 */

public final class SignalFactory {
    private static UnoSignal SERVICE_START_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_SERVICESTART);
    private static UnoSignal SLEEP_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_END_SLEEP);
    private static UnoSignal START_WAKEUP_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_START_WAKEUP);//开始识别
    private static UnoSignal END_WAKEUP_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_END_WAKEUP);//停止识别，等待识别结果
    private static UnoSignal CHARGING_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_CHARGING);
    private static UnoSignal CHARGE_CANCEL_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_CHARGE_CANCEL);
    private static UnoSignal HEAD_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_HEAD);
    private static UnoSignal NECK_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_NECK);
    private static UnoSignal RIGHT_HAND_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_RIGHT_HAND);
    private static UnoSignal LEFT_HAND_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_LEFT_HAND);
    private static UnoSignal RIGHT_EAR_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_RIGHT_EAR);
    private static UnoSignal LEFT_EAR_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_LEFT_EAR);
    private static UnoSignal CHEST_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_CHEST);
    private static UnoSignal ARSE_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_ARSE);
    private static UnoSignal LOCAL_CHANGE_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_LOCAL_CHANGE);

    private static UnoSignal DIRECTIVE_SLEEP_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_DIRECTIVE, UnoSignal.DIRECTIVE_SLEEP);
    private static UnoSignal DIRECTIVE_END_SLEEP_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_DIRECTIVE, UnoSignal.DIRECTIVE_END_SLEEP);
    private static UnoSignal DIRECTIVE_RECOGNIZE_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_DIRECTIVE, UnoSignal.DIRECTIVE_RECOGNIZE);
    private static UnoSignal DIRECTIVE_RELEASE_RECOGNIZE_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_DIRECTIVE, UnoSignal.DIRECTIVE_RECOGNIZE_RELEASE);
    private static UnoSignal DIRECTIVE_DESTROY_RECOGNIZE_SIGNAL = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_DIRECTIVE, UnoSignal.DIRECTIVE_RECOGNIZE_DESTORY);

    public static UnoSignal createDirectiveSleepSignal() {
        return DIRECTIVE_SLEEP_SIGNAL;
    }
    public static UnoSignal createEndWakeupSignal() {
        return END_WAKEUP_SIGNAL;
    }
    public static UnoSignal createEndSleepSignal() {
        return DIRECTIVE_END_SLEEP_SIGNAL;
    }

    public static UnoSignal createDirectiveRecognizeSignal() {
        return DIRECTIVE_RECOGNIZE_SIGNAL;
    }

    public static UnoSignal createDirectiveReleaseRecognizeSignal() {
        return DIRECTIVE_RELEASE_RECOGNIZE_SIGNAL;
    }

    public static UnoSignal createDirectiveDestroyRecognizeSignal() {
        return DIRECTIVE_DESTROY_RECOGNIZE_SIGNAL;
    }

    //发送服务启动信号，通知activity页面服务启动成功
    public static UnoSignal createServiceStartSignal() {
        return SERVICE_START_SIGNAL;
    }

    //唤醒成功信号。
    public static UnoSignal createSleepSignal() {
        return SLEEP_SIGNAL;
    }

    //开始识别信号
    public static UnoSignal createStartWakeupSignal() {
        return START_WAKEUP_SIGNAL;
    }

    public static UnoSignal createChargingSignal() {
        return CHARGING_SIGNAL;
    }

    public static UnoSignal createChargeCancelSignal() {
        return CHARGE_CANCEL_SIGNAL;
    }

    public static UnoSignal createHeadSignal() {
        return HEAD_SIGNAL;
    }

    public static UnoSignal createNeckSignal() {
        return NECK_SIGNAL;
    }

    public static UnoSignal createRightHandSignal() {
        return RIGHT_HAND_SIGNAL;
    }

    public static UnoSignal createLeftHandSignal() {
        return LEFT_HAND_SIGNAL;
    }

    public static UnoSignal createRightEarSignal() {
        return RIGHT_EAR_SIGNAL;
    }

    public static UnoSignal createLeftEarSignal() {
        return LEFT_EAR_SIGNAL;
    }

    public static UnoSignal createChestSignal() {
        return CHEST_SIGNAL;
    }

    public static UnoSignal createArseSignal() {
        return ARSE_SIGNAL;
    }

    public static UnoSignal createLocalChangeSignal() {
        return LOCAL_CHANGE_SIGNAL;
    }

    public static UnoSignal createErrorEvent(Throwable e) {
        UnoSignal signal = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_EVENT, UnoSignal.EVENT_ERROR);
        signal.setData(e);
        return signal;
    }
}
