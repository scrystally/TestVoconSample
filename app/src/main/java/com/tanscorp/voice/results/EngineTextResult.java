package com.tanscorp.voice.results;


import com.tanscorp.voice.interfaces.EngineResult;

import java.util.List;

/**
 * Created by Ken on 2016/8/25.
 */
public class EngineTextResult extends EngineResult {
    public EngineTextResult(String text) {
        super(text);
    }

    //
    private List<EngineTextResult> results;


    public EngineTextResult(String text, String spokenText) {
        super(text, spokenText);
    }


    public List<EngineTextResult> getResults() {
        return results;
    }

    public void setResults(List<EngineTextResult> results) {
        this.results = results;
    }
}
