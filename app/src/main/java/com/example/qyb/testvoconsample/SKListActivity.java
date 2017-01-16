package com.example.qyb.testvoconsample;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ctrl.VoiceCtrlManager;
import com.nuance.sample.voconsample.receiver.IntentKey;
import com.nuance.sample.voconsample.receiver.ReceiverAction;
import com.nuance.sample.voconsample.ssdp.HuaweiSmartGateway;
import com.nuance.sample.voconsample.ssdp.OnTTSListener;
import com.nuance.sample.voconsample.util.Logger;
import com.nuance.sample.voconsample.util.PanelKeyUtil;
import com.tanscorp.signal.SignalFactory;
import com.tanscorp.signal.UnoSignal;
import com.tanscorp.voice.results.EngineTextResult;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 
 * ���ضԻ�����
 * 
 *
 */
public class SKListActivity extends Activity implements OnTTSListener{
    private static final String TAG = "SKListActivity";
    private ViewPager viewpager;
    private ArrayList<LinearLayout> arrayList;
    private PagerAdapter pagerAdapter;
    private int pageLast;
    private int currentPosition, mPosition;
    private int pageCount;
    private ImageView merge;
    private static final int PAGECHANGE = 1;
    private static final int STARTRECODER = 2;
    private static final int STARTRECOGNIZER = 3;
    Bitmap[] bitmap = null;
    StringBuffer micPath = new StringBuffer();
    String path_content = "u5_voicestartanim";
    private int i;
    int rdImgCount;
    int rgImgCount;
	private SKListActivity mContext;
    /**
     * ����
     */
    public static String[] text = { "Play the Main bed room light", "Play the light", "Open the light of Garage","Open the light Living room",
    		"Turn on the light of Guest room", "Turn on the light ","Open the door", "Open Kitchen light", "Open Garage light",
    		"Turn up light the Living room", "Play the lights Kitchen", "Turn up light the Garage","Switch on the light the Kitchen",
    		"Switch on the light the Garage", "Switch on the light the Main bed room", "Switch on Kitchen light","Turn off the light the Kitchen",
    		"Turn off Kitchen light", "Turn off Garage lights", "Turn down the light the Living room","Turn down the light the Kitchen",
            "Turn up light the Kitchen" };
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_personality);

        RelativeLayout relativeLayout=(RelativeLayout)this.findViewById(R.id.activity_personality);
        voiceCtrlManager=new VoiceCtrlManager(this);
        voiceCtrlManager.setViews(relativeLayout);

        Logger.v(TAG, "rdImgCount = "+rdImgCount+", rgImgCount = "+rgImgCount);
        mContext = this;
        merge = (ImageView) findViewById(R.id.imgview);
        initView();

        mSmartHomeHandler.sendEmptyMessageDelayed(1, 6*1000);
    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        arrayList = new ArrayList<LinearLayout>();
        pageLast = text.length % 5;
        pageCount = text.length / 5;
        if (pageLast > 0) {
            pageCount++;
        }

        for (int i = 0; i < pageCount; i++) {
            LinearLayout ll = new LinearLayout(this);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            ll.setOrientation(LinearLayout.VERTICAL);
            ll.setGravity(Gravity.CENTER_VERTICAL);
            ll.setLayoutParams(params);
            arrayList.add(ll);
        }
        pagerAdapter = new PagerAdapter() {
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return arrayList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // arrayList.get(position).setBackground(null);//����ͼƬ
                container.removeView(arrayList.get(position));
                arrayList.get(position).removeAllViews();
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                currentPosition = position + 1;
                mPosition = position;
                Log.i("pageLast", "" + pageLast + "pageCount" + pageCount);
                if (pageLast > 0) {
                    if (position == (pageCount - 1)) {
                        for (int i = 0; i < pageLast; i++) {
                            parseView(i, position);
                        }
                    } else {
                        for (int i = 0; i < 5; i++) {

                            parseView(i, position);
                        }
                    }

                } else {
                    for (int i = 0; i < 5; i++) {
                        Log.i("position", "2");
                        parseView(i, position);
                    }
                }
                container.addView(arrayList.get(position), 0);
                return arrayList.get(position);
            }
        };
        viewpager.setAdapter(pagerAdapter);
        mHandler.sendEmptyMessageDelayed(1, 10000);

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case PAGECHANGE:
                    if (mPosition < (pageCount)) {
                        viewpager.setCurrentItem(currentPosition);
                    } else {
                        mHandler.removeMessages(PAGECHANGE);
                    }
                    mHandler.sendEmptyMessageDelayed(PAGECHANGE, 10000);
                    break;
                default:
                    break;
            }
        };
    };

	private AnimationDrawable mAnimDrawableMic;


    public void parseView(int i, int position) {
        View view = View.inflate(SKListActivity.this, R.layout.text_pager,null);
        TextView tv = (TextView) view.findViewById(R.id.text);
        Log.i("page:", "" + (5 * position + i));
        tv.setText(text[5 * position + i]);
        arrayList.get(position).addView(view);
    }

    private VoiceCtrlManager voiceCtrlManager=null;
    private  boolean isStopScan = false;
    @Override
    protected void onResume() {
        super.onResume();
        HermesEventBus.getDefault().register(this);
        isStopScan = false;
        postEvent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isStopScan = true;
        postDestroyRecognizeEvent();
        voiceCtrlManager.resetAniMic();
        HermesEventBus.getDefault().unregister(this);
        HermesEventBus.getDefault().post(SignalFactory.createDirectiveReleaseRecognizeSignal());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  onEvent(UnoSignal signal){
        if (UnoSignal.VERSION_1 == signal.getVersion()) {
            final int type = signal.getKind();
            if (UnoSignal.KIND_EVENT== type) {
                final int subType = signal.getType();
                switch (subType) {
                    case UnoSignal.EVENT_START_WAKEUP:
                        voiceCtrlManager.startAnimMic();
                        break;
                    //语音结束，等待识别结果
                    case UnoSignal.EVENT_END_WAKEUP:
                        if(!isStopScan){
                            voiceCtrlManager.startAnimMicRecognize();
                        }
                        break;
                    case UnoSignal.EVENT_SPOKEN_VOICE:
                        voiceCtrlManager.resetAniMic();
                        if(signal != null) {
                            Toast.makeText(this, "Speak: " + signal.getTextResult(), Toast.LENGTH_SHORT).show();
                            if (signal.getEngineResults() != null) {
                                for (EngineTextResult r : signal.getEngineResults())
                                    if(processResult(r.getText()))
                                        return;
                            } else {
                                String resultText = signal.getTextResult();
                                processResult(resultText);
                            }
                        }
//						postEvent();
                        break;
                    case UnoSignal.EVENT_VOICE_TEXT_TO_SPEECH:

//                        Toast.makeText(MainActivity.this,signal.getTextResult(),Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
            }
        }
    }

    private void postEvent(){
        UnoSignal unoSignal = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_DIRECTIVE, UnoSignal.DIRECTIVE_RECOGNIZE);
        HermesEventBus.getDefault().post(unoSignal);
    }

    private void postVoiceTTLEvent(String speakStr){
        UnoSignal unoSignal = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_DIRECTIVE, UnoSignal.DIRECT_VOICE_TEXT_TO_SPEECH);
//		unoSignal.setTextResult("now the temperature is 28 , and the humidity is 50% , thank you ");
        unoSignal.setTextResult(speakStr);
        HermesEventBus.getDefault().post(unoSignal);
    }

    private void postMotionEvent(Byte mData){
        UnoSignal unoSignal = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_DIRECTIVE, UnoSignal.DIRECTIVE_SENSOR_CMD);
        unoSignal.setCmd(mData);
        HermesEventBus.getDefault().post(unoSignal);
    }

    private void postDestroyRecognizeEvent(){
        UnoSignal unoSignal = new UnoSignal(UnoSignal.VERSION_1, UnoSignal.KIND_DIRECTIVE, UnoSignal.DIRECTIVE_RECOGNIZE_RELEASE);
        HermesEventBus.getDefault().post(unoSignal);
    }

    public boolean processResult(String result) {
        boolean rValue=false;
        result = result.toLowerCase();
        processResultString(result);
        /*if ((result.contains("Play the Main bed room light"))) {
            processResultString(result);
            rValue = true;
        }else if ((result.contains("Play the light"))) {
            processResultString(result);

            rValue = true;
        }else if ((result.contains("Open the light of Garage"))) {
            processResultString(result);

            rValue = true;
        }else if ((result.contains("Open the light Living room"))) {
            processResultString(result);

            rValue = true;
        }else if(result.contains("Turn on the light of Guest room")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Turn on the light")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Open the door")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Open Kitchen light")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Open Garage light")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Turn up light the Living room")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Play the lights Kitchen")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Turn up light the Garage")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Switch on the light the Kitchen")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Switch on the light the Garage")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Switch on the light the Garage")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Switch on Kitchen light")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Turn off the light the Kitchen")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Turn off Kitchen light")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Turn off Garage lights")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Turn down the light the Living room")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Turn down the light the Kitchen")){
            processResultString(result);

            rValue=true;
        }else if(result.contains("Turn up light the Kitchen")){
            processResultString(result);

            rValue=true;
        }*/

        postEvent();

        return rValue;
    }

    public void processResultString(String resultText){
        String data = null;
        if ((data = PanelKeyUtil.parseEnTextMessage(resultText)) != null) {
            Log.e(TAG, "data=" + data);
            if (data.contains("#")) {
                String[] strs = data.split("#");
                if (strs.length == 3) {
                    Log.e(TAG, "strs[0]=" + strs[0] + ",strs[1]=" + strs[1] + ",strs[2]=" + strs[2]);
                    Intent intent = new Intent(ReceiverAction.ACTION_SMARTHOME_CMD);
                    intent.putExtra(IntentKey.KEY_VOICE, strs[0]);
                    intent.putExtra(IntentKey.KEY_VOICECONTENT, strs[1]);
                    intent.putExtra(IntentKey.KEY_SMARTHOME_CMD, strs[2]);
                    mContext.sendBroadcast(intent);
                    return;
                }
            }
        }
    }

    public HuaweiSmartGateway huaweiSmartGateway;

    @SuppressLint("HandlerLeak")
    private Handler mSmartHomeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initSmartHome();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 初始化华为智能网关类
     */
    private void initSmartHome() {
        huaweiSmartGateway = new HuaweiSmartGateway(getApplicationContext());
        huaweiSmartGateway.setOnTTSListener(this);
        huaweiSmartGateway.init();
        Logger.d(TAG, "初始化华为智能网关类");
    }

    @Override
    public void OnTTS(String ttsStr) {
        postVoiceTTLEvent(ttsStr);
    }

    @Override
    public void OnMotion(Byte mByte) {
        postMotionEvent(mByte);
    }
}
