package com.nuance.sample.voconsample.ssdp;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by florent.noel on 6/14/13.
 */
public class SSDPUtils {
    private static String TAG = SSDPUtils.class.getName();

    public static final String ADDRESS = "239.255.255.250";

    public static final int PORT = 1900;

    public static final int MAX_REPLY_TIME = 5;
    public static final int MSG_TIMEOUT = MAX_REPLY_TIME * 1000 + 1000;

    public static final String STRING_MSEARCH = "M-SEARCH * HTTP/1.1";
    public static final String NOTIFY_MSEARCH = "NOTIFY * HTTP/1.1";

    // public static final String STRING_RootDevice = "ST: upnp:rootdevice";
    public static final String STRING_AllDevice = "ST: ssdp:all";

    public static final String NEWLINE = "\r\n";
    public static final String MAN = "Man:\"ssdp:discover\"";

    public static String LOCATION_TEXT = "LOCATION: http://";

    public static String buildSSDPSearchString() {
        StringBuilder content = new StringBuilder();

        content.append(STRING_MSEARCH).append(NEWLINE);
        content.append("Host: " + ADDRESS + ":" + PORT).append(NEWLINE);
        content.append(MAN).append(NEWLINE);
        content.append("MX: " + MAX_REPLY_TIME).append(NEWLINE);
        content.append(STRING_AllDevice).append(NEWLINE);
        content.append(NEWLINE);

        Log.e(TAG, content.toString());

        return content.toString();
    }

    /**
     * 方法描述：获取本地userId方法
     * 
     * @param String
     *            app_name
     * @return
     * @see RobotUtil
     */
    public static String getUserId(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (!wifi.isWifiEnabled()) {
            wifi.setWifiEnabled(true);
        }
        WifiInfo info = wifi.getConnectionInfo();
        String mac = info.getMacAddress();
        if (mac == null) {
            if (!wifi.isWifiEnabled()) {
                wifi.setWifiEnabled(true);
            }
            long starttime = System.currentTimeMillis();
            while (true && System.currentTimeMillis() - starttime < 5000) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                info = wifi.getConnectionInfo();
                mac = info.getMacAddress();
                if (mac != null) {
                    break;

                }
            }
            if (mac == null) {
                Log.e("getUserId", "getUserId失败 ");
                return null;
            }

        }
        Log.e("getUserId", "getUserId " + mac);
        String str = mac.replace(":", "");
        // StringBuffer a = new StringBuffer(mac);
        // a.deleteCharAt(2).deleteCharAt(4).deleteCharAt(6).deleteCharAt(8)
        // .deleteCharAt(10);
        // mac = a.toString();
        return str;
    }

    /**
     * NOTIFY * HTTP/1.1 ST: urn: schemas-upnp-org:device: HuaweiSmartSwitch:1
     * HOST: 239.255.255.250:1900 EXT: CACHE-CONTROL: max-age=1800 LOCATION:
     * http://127.0.0.1:8008/ssdp/device-desc.xml CONFIGID.UPNP.ORG: 7339
     * BOOTID.UPNP.ORG: 7339 USN: uuid: Upnp-HW-SWITCH-01-
     * 1_0-508151285::urn:schemas-upnp-org:device:HuaweiSmartSwitch
     */

    public static String buildSSDPAliveString(String mac) {
        StringBuilder content = new StringBuilder();

        // content.append("HTTP/1.1 200 OK").append(NEWLINE);
        content.append(NOTIFY_MSEARCH).append(NEWLINE);
        content.append("ST: urn:schemas-upnp-org:device:CanbotRobot:1").append(
                NEWLINE);
        content.append("Host: " + ADDRESS + ":" + PORT).append(NEWLINE);
        content.append("Cache-Control: max-age=1800").append(NEWLINE);
        content.append("CONFIGID.UPNP.ORG: 7339").append(NEWLINE);
        content.append("BOOTID.UPNP.ORG: 7339").append(NEWLINE);
        content.append(
                "USN: uuid:Upnp-KL-U03S-1_0-" + mac
                        + "::urn:schemas-upnp-org:device:CanbotRobot").append(
                NEWLINE);
        content.append(NEWLINE);

        /**
         * NOTIFY * HTTP/1.1 ST: urn: schemas-upnp-org:device:
         * HuaweiSmartSwitch:1 HOST: 239.255.255.250:1900 EXT: CACHE-CONTROL:
         * max-age=1800 LOCATION: http://127.0.0.1:8008/ssdp/device-desc.xml
         * CONFIGID.UPNP.ORG: 7339 BOOTID.UPNP.ORG: 7339 USN: uuid:
         * Upnp-HW-SWITCH
         * -01-1_0-508151285::urn:schemas-upnp-org:device:HuaweiSmartSwitch
         */

        Log.e(TAG, content.toString());

        return content.toString();
    }

    public static String buildSSDPByebyeString() {
        StringBuilder content = new StringBuilder();

        content.append(NOTIFY_MSEARCH).append(NEWLINE);
        content.append("Host: " + ADDRESS + ":" + PORT).append(NEWLINE);
        content.append("NT: someunique:idscheme3").append(NEWLINE);
        content.append("NTS: ssdp:byebye").append(NEWLINE);
        content.append("USN: someunique:idscheme3").append(NEWLINE);
        content.append(NEWLINE);

        Log.e(TAG, content.toString());

        return content.toString();
    }

    public static String parseIP(String msearchAnswer) {
        String ip = "0.0.0.0";

        // find the index of "LOCATION: http://"
        int loactionLinePos = msearchAnswer.indexOf(LOCATION_TEXT);

        if (loactionLinePos != -1) {
            // position the index right after "LOCATION: http://"
            loactionLinePos += LOCATION_TEXT.length();

            // find the next semi-colon (would be the one that separate IP from
            // PORT nr)
            int locColon = msearchAnswer.indexOf(":", loactionLinePos);
            // grab IP
            ip = msearchAnswer.substring(loactionLinePos, locColon);
        }
        return ip;
    }
}
