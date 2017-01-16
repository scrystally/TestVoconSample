package com.nuance.sample.voconsample.receiver;

/**
 * 骞挎挱鐩稿叧鐨刟ction
 * 
 * @author xiaowei
 *
 */
public class ReceiverAction {
	/**
	 * 鍩烘湰action
	 */
	private static final String BASEACITION = "com.nuance.sample.voconsample.";

	/**
	 * 骞挎挱---璇嗗埆缁撴灉
	 */
	public static final String RECOGNIZER_RESULT = BASEACITION + ".recognizer_result";

	/**
	 * 骞挎挱---鍞ら啋鎴愬姛
	 */
	public static final String WAKE_SUCCESS = BASEACITION + ".wake_success";
	/**
	 * 骞挎挱---杩涘叆寰呭敜閱�
	 */
	public static final String GOTO_WAKE_UP = BASEACITION + ".goto.wake_up";

	/**
	 * 骞挎挱---寮�濮嬪綍闊宠瘑鍒�
	 */
	public static final String START_RECORD = BASEACITION + ".start_record";

	/**
	 * 骞挎挱---鍋滄褰曢煶
	 */
	public static final String STOP_RECORD = BASEACITION + ".stop_record";

	/**
	 * 鎾斁闊抽缁撴潫
	 */
	public static final String PLAY_SOUND_END = BASEACITION + "action.play_sound.end";

	/**
	 * 锟斤拷息锟斤拷锟斤拷----锟斤拷锟斤拷锟斤拷锟斤拷识锟斤拷慕锟斤拷
	 */
	public static final String MSG_VOICE_RECOGNIZER_RESULT = BASEACITION + "action.msg_voice.recognizer.result";

	/**
	 * 锟斤拷息锟斤拷锟斤拷----锟斤拷锟杰伙拷锟斤拷识锟斤拷慕锟斤拷
	 */
	public static final String MSG_VOICE_RECOGNIZER_WAKEUP = BASEACITION + "action.msg_voice.recognizer.wakeup";

	/**
	 * 锟斤拷息锟斤拷锟斤拷----锟斤拷锟斤拷识锟斤拷慕锟斤拷
	 */
	public static final String FACE_RECOGNIZER_RESULT = BASEACITION + "action.face.recognizer.result";
	/**
	 * 锟斤拷锟杰家撅拷指锟筋处锟斤拷锟斤拷锟�
	 */
	public static final String ACTION_SMARTHOME_CMD_HANDLE_END = "smarthome.cmd.handle.end";
	/**
	 * 锟斤拷锟杰家撅拷指锟筋处锟斤拷锟斤拷锟�
	 */
	public static final String ACTION_SMARTHOME_CMD_SEND_SUCCESS = "smarthome.cmd.send.success";
	/**
	 * OpenLife同锟斤拷锟斤拷息
	 */
	public static final String ACTION_OPENLIFE_SYNC_INFOR = "openlife.sync.infor";

	/**
	 * 锟较憋拷锟斤拷锟斤拷锟斤拷锟斤拷指锟斤拷
	 */
	public static final String ACTION_HEALTH_MANAGEMENT_DATA = "cn.canbot.health.management.data";

	/**
	 * 锟较憋拷锟斤拷锟杰家撅拷指锟斤拷
	 */
	public static final String ACTION_SMARTHOME_CMD = "cn.canbot.smarthome.cmd";

	/**
	 * 锟斤拷锟杰家居讹拷锟斤拷指锟筋集
	 */
	// public static final String[] ACTIONS = { "锟斤拷", "锟截憋拷", "锟斤拷询" };
	public static final String[] ACTIONS = { "turnOff", "turnOn", "SwitchGreenColor" };

	/**
	 * 锟秸碉拷锟斤拷锟斤拷锟教硷拷锟斤拷锟斤拷锟斤拷---锟斤拷3288锟较固硷拷apk锟斤拷锟酵癸拷锟斤拷锟斤拷
	 */
	public static final String ACTION_UPDATE_FIREWARE_REQUEST = "com.action.download.completed";

	/**
	 * 锟斤拷锟秸碉拷pad锟斤拷锟斤拷锟斤拷要锟斤拷锟铰碉拷锟斤拷锟斤拷时锟斤拷锟斤拷锟酵该广播通知锟教硷拷锟斤拷锟斤拷
	 */
	public static final String ACTION_UPDATE_FIREWARE_OK = "com.action.confirm.update";

	// public static final String[] LOCATIONS = {"锟斤拷锟斤拷","锟斤拷锟斤拷","锟斤拷台","锟斤拷锟斤拷"};
	// public static final String[] DEVICES =
	// {"锟斤拷","锟秸碉拷","锟斤拷湿锟斤拷","锟斤拷锟斤拷","锟斤拷锟斤拷","锟斤拷水锟斤拷","锟斤拷锟斤拷","锟斤拷锟斤拷"};

	/**
	 * Map Service执锟斤拷时锟斤拷锟斤拷锟斤拷锟秸点发锟斤拷锟斤拷锟侥广播
	 */
	public static final String ACTION_REACH_DESTINATION = "com.uurobot.slamservice.ACTION.SLAM_ACTION_EXECUTE_RESULT_BROADCAST";
	/**
	 * 锟斤拷锟斤拷锟街伙拷锟剿脚憋拷锟斤拷息
	 */
	public static final String ACTION_MOBILE_SCRIPT_MSG = "com.uurobot.script.msg";
	/**
	 * 锟斤拷应锟斤拷锟斤拷息锟姐播
	 */
	public static final String MSG_SENSOR = BASEACITION + "action.sensor";

	/**
	 * 锟斤拷应锟斤拷锟斤拷息锟姐播
	 */
	public static final String MSG_FINGER_RESULT = BASEACITION + "action.finger.result";

}
