package com.nuance.sample.voconsample.receiver;

/**
 * ͨ��Intent ���ݲ���ʱ��key
 * 
 * @author xiaowei
 *
 */
public class IntentKey {
	/**
	 * 待播放的tts
	 */
	public static final String ttsText = "ttsText";

	/**
	 * 播放完tts后进入声控状态
	 */
	public static final String voiceState = "voiceState";

	/**
	 * 识别结果
	 */
	public static final String recognizerResult = "recognizerResult";

	/**
	 * 语义返回结果的serviceType
	 */
	public static final String serviceType = "serviceType";
	
	
	/**
	 * ��������Ƶ��·��
	 */
	public static final String SOUND_PATH = "sound_Path";

	/**
	 * ��������Ƶ������
	 */
	public static final String MSG_TYPE = "msg_type";
	/**
	 * ��������Ƶ������
	 */
	public static final String COMPLETED_DO = "completed_do";
	/**
	 * ��������
	 */
	public static final String HEALTH_DATA = "health_data";
	/**
	 * �����ַ������
	 */
	public static final String VOICE_RESULT = "voice_result";

	/**
     * ͷ����Ӧ��
     */
    public static final String SENSOR_HEAD = "sensor_head";
    
    /**
     * ���Ը�Ӧ��
     */
    public static final String SENSOR_HEAD_BACK = "sensor_head_back";
    /**
     * �����Ӧ��
     */
    public static final String SENSOR_LEFT_EAR = "sensor_left_ear";
    /**
     * �Ҷ���Ӧ��
     */
    public static final String SENSOR_RIGHT_EAR = "sensor_right_ear";
    /**
     * ���Ӹ�Ӧ��
     */
    public static final String SENSOR_NECK = "sensor_neck";
    /**
     * ���Ӹ�Ӧ��
     */
    public static final String SENSOR_BELLY = "sensor_belly";
    /**
     * ƨƨ��Ӧ��
     */
    public static final String SENSOR_BUTT = "sensor_butt";
    
    /**
     * ������
     */
    public static final String START_VOICE = "start_voice";
    
	public static  final String REMOTE_SERVICE_PACAKAGE="com.uurobot.video";
	public static final String REMOTE_SERVICE="com.uurobot.video.service.FaceRecongnizeService";
	public static final String EYE_NORMAL_ANIMA="eye4.mp4";
	public static final String KEY_VOICE="voice";
	public static final String KEY_VOICECONTENT="voicecontent";
	public static final String KEY_SMARTHOME_CMD="smarthome_cmd";


}
