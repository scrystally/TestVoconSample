
package com.ctrl;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.qyb.testvoconsample.R;


/**
 * Created by QYB on 2016/12/26.
 */

public class VoiceCtrlManager {


    private ImageView imageMic;
    private RelativeLayout mRelativeLayout;
//    private ImageView imgShowFull;
    private Context mContext;
    private AnimationDrawable animationMic;
    private AnimationDrawable animationMicRecognize;
    private AnimationDrawable animationMicRecover;

    public VoiceCtrlManager(Context context){
        this.mContext = context;
    }

    public void setViews(RelativeLayout paramRelativeLayout) {
        this.mRelativeLayout = paramRelativeLayout;
        mRelativeLayout.clearAnimation();
        imageMic = new ImageView(this.mContext);
        imageMic.setBackgroundResource(R.drawable.listdialogue_anim);
//        imageMic.setOnClickListener(onClickListener);
        RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        localLayoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        localLayoutParams1.bottomMargin = 10;
        localLayoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        paramRelativeLayout.addView(this.imageMic, localLayoutParams1);

    }

    public void startAnimMic() {
        if (imageMic == null)
            return;
        this.imageMic.setBackgroundResource(R.drawable.listdialogue_anim);
        this.animationMic = ((AnimationDrawable) this.imageMic.getBackground());
        this.animationMic.start();
    }

    public void pauseAnimMic() {
        if (null != imageMic) {
            this.animationMic = ((AnimationDrawable) this.imageMic.getBackground());
            this.animationMic.selectDrawable(0);
            this.animationMic.stop();
        }
    }

    public void startAnimMicRecognize() {
        this.imageMic.setBackgroundResource(R.drawable.listdialogueshibie_anim);
        this.animationMicRecognize = ((AnimationDrawable) this.imageMic.getBackground());
        this.animationMicRecognize.start();
    }

    public void resetAniMic() {
        this.imageMic.setBackgroundResource(R.drawable.listdialoguehuifu_anim);
        this.animationMicRecover = ((AnimationDrawable) this.imageMic.getBackground());
           this.animationMicRecover.start();
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startAnimMic();
        }
    };
}
