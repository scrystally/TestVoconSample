package com.tanscorp.voice.interfaces;

/**
 * Created by Ken on 2016/8/24.
 */
public abstract class EngineResult {
	private String mText = "";


	public EngineResult() {
	}

	public EngineResult(String text) {
		this.mText = text;
	}

	public String getText() {
		return mText;
	}

	private String spokenText;

	public EngineResult(String text, String spokenText) {
		this.mText = text;
		this.spokenText = spokenText;
	}

	public String getSpokenText() {
		return spokenText;
	}

	public void setSpokenText(String spokenText) {
		this.spokenText = spokenText;
	}

}
