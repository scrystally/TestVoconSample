package com.nuance.sample.voconsample.util;

import android.annotation.SuppressLint;

import org.json.JSONException;
import org.json.JSONObject;


public class PanelKeyUtil {
	private static final String TAG = "PanelKeyUtil";
	static String[] TURNONS = new String[] {"play", "turn on", "turn up", "open", "switch on"};
	static String[] TURNOFFS = new String[] {"stop", "turn off", "turn down", "close", "switch off"};
	static String[] DEVICES = new String[] {"power panel", "light", "venting machine", "radio", "tv","ac", "alarm", "door"};
	static String[] LOCATIONS = new String[] {"living room", "main bed room", "guest room", "kitchen", "garage","veranda","bed room"};



	/**
	 * 句子末尾标点符号的判断
	 */
	public static boolean containPunctuation(String result) {
		if (result != null) {
			if (result.endsWith(".") || result.endsWith("。") || result.endsWith("?") || result.endsWith("？")
					|| result.endsWith("”") || result.endsWith("!") || result.endsWith("！")) {
				return true;
			}
		}
		return false;
	}




	/**
	 * 匹配命令词,包含任意关键词就行
	 *
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean matchString(String src, String dest[]) {
		if (src == null || src.isEmpty() || dest == null || dest.length == 0) {
			return false;
		}
		src = src.trim();
		int len = dest.length;
		if (src == null || dest == null || len == 0) {
			return false;
		}
		if (src != null && src.equals("")) {
			return false;
		}
		src = src.trim();
		for (int i = 0; i < len; i++) {
			String mDest = dest[i].trim();
			if (src.contains(mDest)) {
				return true;
			} else {
				continue;
			}
		}
		return false;
	}

	/**
	 * 匹配命令词，必须包含所有的关键词才算匹配成功
	 *
	 * matchKeyStr("大会是什么时候开始", new String[][] { { "大会" }, { "什么时候" }, { "开始" }
	 * }
	 *
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean matchKeyStr(String src, String dest[]) {
		if (src == null || src.isEmpty() || dest == null || dest.length == 0) {
			return false;
		}
		src = src.trim();
		int len = dest.length;
		for (int i = 0; i < len; i++) {
			String mDest = dest[i].trim();
			if (!src.contains(mDest)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 匹配命令词，有部分关键词可以被多个代替
	 *
	 * matchKeyStr("大会是什么时候开始", new String[][] { { "大会" }, { "几点","什么时候" }, {
	 * "开始" } }
	 *
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean matchKeysStr(String src, String dest[][]) {
		if (src == null || src.isEmpty() || dest == null || dest.length == 0) {
			return false;
		}
		src = src.trim();
		int count = 0;
		int index = 0;
		for (int i = 0, len = dest.length; i < len; i++) {
			int length = dest[i].length;
			if (length == 0) {
				continue;
			}
			if (length > 1) {
				count++;
				for (int j = 0; j < length; j++) {
					String temp = dest[i][j];
					if (src.contains(temp)) {
						index++;
						break;
					}
				}
			} else {
				String temp = dest[i][0];
				if (!src.contains(temp)) {
					return false;
				}
			}
		}
		if (count == index) {
			return true;
		}
		return false;
	}

	/**
	 * 匹配命令词，可以匹配表达多个意思的句子，但是同一个句子必须全部关键词匹配
	 *
	 * matchKeyStrs("你叫什么名字", new String[][]{{"你"，“名字”},{"你","谁"}})
	 * matchKeyStrs("你是谁", new String[][]{{"你"，“名字”},{"你","谁"}})
	 *
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean matchKeyStrs(String src, String dest[][]) {
		if (src == null || src.isEmpty() || dest == null || dest.length == 0) {
			return false;
		}
		src = src.trim();
		for (int i = 0, len = dest.length; i < len; i++) {
			int length = dest[i].length;
			if (length == 0) {
				continue;
			}
			if (length > 1) {
				int index = 0;
				for (int j = 0; j < length; j++) {
					String temp = dest[i][j];
					if (src.contains(temp)) {
						index++;
					}
					if (length == index) {
						return true;
					}
				}
			} else {
				String temp = dest[i][0];
				if (src.contains(temp)) {
					return true;
				}
			}
		}
		return false;
	}



	/**
	 * 处理德国展英文信息
	 * @param mText
	 * @return
	 */
	@SuppressLint("DefaultLocale")
	public static String parseEnTextMessage(String mText) {
		if (null==mText||mText.isEmpty()) {
			return null;
		}
		String operate = null;
		String position = null;
		String device = null;
		String voice = "none";
		String voiceContent = "";
		String mcaseText = (mText.trim()).toLowerCase();

		for (String string : DEVICES) {
			if (mcaseText.contains(string)) {
				device = string;
				break;
			}
		}
		for (String string : LOCATIONS) {
			if (mcaseText.contains(string)) {
				position = string;
				break;
			}
		}
		for (String string : TURNONS) {
			if (mcaseText.contains(string)) {
				operate = "turnOn";
				break;
			}
		}
		for (String string : TURNOFFS) {
			if (mcaseText.contains(string)) {
				operate = "turnOff";
				break;
			}
		}


		if (mcaseText.contains("open the door")) {
			operate = "open";
			device = "door";
			position = "";
		} else if ((mcaseText.contains("red")||mcaseText.contains("lat")||mcaseText.contains("let"))
				&&(mcaseText.contains("light")||mcaseText.contains("night")||mcaseText.contains("right"))) {
			operate = "SwitchRedColor";
			device = "light";
			voice = "tts";
			voiceContent = "Ok, no problem!";
		} else if (mcaseText.contains("become yellow")||mcaseText.contains("to read")) {
			operate = "SwitchYellowColor";
			device = "light";
			voice = "tts";
			voiceContent = "Ok, no problem!";
		} else if (mcaseText.contains("hot in the")||mcaseText.contains("how in the")||mcaseText.contains("her in the")) {
			operate = "down";
			device = "temperature";
		} else if (mcaseText.contains("cold in the")) {
			operate = "up";
			device = "temperature";
		} else if (mcaseText.contains("humiture")) {
			operate = "query";
			device = "humiture";
		} else {

			if (mcaseText.contains("on")) {
				operate = "turnOn";
			}
			if (mcaseText.contains("of")) {
				operate = "turnOff";
			}

			if (mcaseText.contains("light")||mcaseText.contains("night")||mcaseText.contains("right")) {
				device = "light";
				if (mcaseText.contains("yellow")) {
					operate = "SwitchYellowColor";
					voice = "tts";
					voiceContent = "Ok, no problem!";
				} else if (mcaseText.contains("red")) {
					operate = "SwitchRedColor";
					voice = "tts";
					voiceContent = "Ok, no problem!";
				} else if (mcaseText.contains("green")) {
					operate = "SwitchGreenColor";
					voice = "tts";
					voiceContent = "Ok, no problem!";
				} else if (mcaseText.contains("crimson")) {
					operate = "SwitchCrimsonColor";
					voice = "tts";
					voiceContent = "Ok, no problem!";
				} else if (mcaseText.contains("purple")) {
					operate = "SwitchPurpleColor";
					voice = "tts";
					voiceContent = "Ok, no problem!";
				} else if (mcaseText.contains("white")) {
					operate = "SwitchWhiteColor";
					voice = "tts";
					voiceContent = "Ok, no problem!";
				} else if (mcaseText.contains("blue")) {
					operate = "SwitchBlueColor";
					voice = "tts";
					voiceContent = "Ok, no problem!";
				}
			}

		}


		if (operate != null && device != null) {
			Logger.e(TAG, "operate = "+operate+", position = "+position+", device = "+device);
			// 上报智能家居指令
			JSONObject robotClassData = new JSONObject();
			try {
				robotClassData.put("action", operate);
				robotClassData.put("deviceName", device);
				robotClassData.put("location",position);
				return voice+"#"+voiceContent+"#"+robotClassData.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return null;


	}



}
