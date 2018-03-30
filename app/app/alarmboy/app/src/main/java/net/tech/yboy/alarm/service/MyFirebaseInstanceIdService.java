package net.tech.yboy.alarm.service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import net.tech.yboy.alarm.model.DeviceTokenRegisterData;
import net.tech.yboy.alarm.util.PrefUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by manabu on 2018/03/21.
 */

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        PrefUtil.setDeviceToken(getApplicationContext(), refreshedToken);
        PrefUtil.setSendDeviceToken(getApplicationContext(), false);

        if (!PrefUtil.isRegister(getApplicationContext())) {
            return;
        }

        new DeviceTokenRegisterData().register(
                PrefUtil.getDeviceToken(getApplicationContext()), refreshedToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.body() == null || (response.body() != null && response.body().error != null)) {
                        return;
                    }
                    PrefUtil.setDeviceToken(getApplicationContext(), null);
                    PrefUtil.setSendDeviceToken(getApplicationContext(), true);
                    return;
                }, throwable -> {
                    return;
                });

    }
}