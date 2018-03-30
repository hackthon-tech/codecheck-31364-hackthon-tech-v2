package net.tech.yboy.alarm.service;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import net.tech.yboy.alarm.R;
import net.tech.yboy.alarm.model.GoogleHomeVoiceData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by manabu on 2018/03/21.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // 遅延情報が届いている
        new GoogleHomeVoiceData().get(getApplicationContext(), "おはようございます。本日は電車の遅延があるみたいです。起きて支度をしましょう。", true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    return;
                }, throwable -> {
                    return;
                });

    }
}