package net.tech.yboy.alarm.util;

import android.content.Context;
import android.content.SharedPreferences;

import net.tech.yboy.alarm.AppApplication;

import java.util.HashSet;

/**
 * Created by manabu on 2018/03/21.
 */

public class PrefUtil {
    public static final String PREF_INFO = "info";
    // 初回表示
    public static final String PARAM_INIT = "param_init";
    // 初期登録
    public static final String PARAM_REGISTER = "param_register";
    public static final String PARAM_UID = "param_uid";
    // 初回設定
    public static final String PARAM_SETTING = "param_setting";
    public static final String PARAM_SETTING_NOTIFY = "param_setting_notify";
    // デバイストークン
    public static final String PARAM_DEVICE_TOKEN = "param_device_token";
    public static final String PARAM_SEND_DEVICE_TOKEN = "param_send_device_token";

    public static boolean setUid(Context context, String uid){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PARAM_UID, uid);
        return editor.commit();
    }
    public static String getUid(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        return pref.getString(PARAM_UID, null);
    }

    public static boolean setInit(Context context, boolean init){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(PARAM_INIT, init);
        return editor.commit();
    }
    public static boolean isInit(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        return pref.getBoolean(PARAM_INIT, false);
    }

    public static boolean setRegister(Context context, boolean register){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(PARAM_REGISTER, register);
        return editor.commit();
    }
    public static boolean isRegister(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        return pref.getBoolean(PARAM_REGISTER, false);
    }

    public static boolean setSetting(Context context, boolean setting){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(PARAM_SETTING, setting);
        return editor.commit();
    }
    public static boolean isSetting(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        return pref.getBoolean(PARAM_SETTING, false);
    }

    public static boolean setSettingNotify(Context context, boolean setting){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(PARAM_SETTING_NOTIFY, setting);
        return editor.commit();
    }
    public static boolean isSettingNotify(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        return pref.getBoolean(PARAM_SETTING_NOTIFY, false);
    }

    public static boolean setSendDeviceToken(Context context, boolean token){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(PARAM_SEND_DEVICE_TOKEN, token);
        return editor.commit();
    }
    public static boolean isSendDeviceToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        return pref.getBoolean(PARAM_SEND_DEVICE_TOKEN, false);
    }

    public static boolean setDeviceToken(Context context, String token){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PARAM_DEVICE_TOKEN, token);
        return editor.commit();
    }
    public static String getDeviceToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREF_INFO, Context.MODE_PRIVATE);
        return pref.getString(PARAM_DEVICE_TOKEN, null);
    }
}