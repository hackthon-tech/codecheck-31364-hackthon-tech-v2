package net.tech.yboy.alarm.presenter;

import android.content.Context;
import android.util.Log;

import net.tech.yboy.alarm.model.DeviceTokenRegisterData;
import net.tech.yboy.alarm.model.SettingData;
import net.tech.yboy.alarm.model.SettingRegisterData;
import net.tech.yboy.alarm.model.UserRegisterData;
import net.tech.yboy.alarm.server.api.ApiAlarmData;
import net.tech.yboy.alarm.util.PrefUtil;
import net.tech.yboy.alarm.util.UidUtil;
import net.tech.yboy.alarm.view.MainUseCase;
import net.tech.yboy.alarm.view.SettingUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by manabu on 2018/03/21.
 */

public class UserSettingPresenter implements SettingUseCase.Presenter {

    private SettingUseCase.View mView;

    private SettingData mSettingData;

    private SettingRegisterData mSettingRegisterData;

    public UserSettingPresenter(SettingUseCase.View view) {
        mView = view;
        mSettingData = new SettingData();
        mSettingRegisterData = new SettingRegisterData();
    }

    @Override
    public void get(Context context) {

        mView.showProgress();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                getSetting(context);
            }
        }).start();
    }

    private void getSetting(Context context) {

        mSettingData.get(mView.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(mView::hideProgress)
                .subscribe(response -> {
                    if (response.body() == null || (response.body() != null && response.body().error != null)) {
                        mView.failMessage("設定取得に失敗しました");
                        return;
                    }

                    ApiAlarmData.ResGet res = response.body();

                    if (res.setting == null) {
                        mView.failMessage("設定取得に失敗しました");
                        return;
                    }

                    mView.update(
                            res.setting.alarm,
                            res.setting.departureStation,
                            res.setting.arrivalStation,
                            res.setting.wakeUpTime,
                            res.setting.departureStationCode,
                            res.setting.arrivalStationCode,
                            res.setting.company);
                   return;
                }, throwable -> {
                    mView.failMessage("設定取得に失敗しました");
                    return;
                });
    }


    @Override
    public void register(Context context, boolean alarm, String departureStation, String arrivalStation, String wakeUpTime, String departureStationCode, String arrivalStationCode, String company) {

        mSettingRegisterData.register(mView.getToken(), alarm, departureStation, arrivalStation, wakeUpTime, departureStationCode, arrivalStationCode, company)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(mView::hideProgress)
                .subscribe(response -> {
                    if (response.body() == null || (response.body() != null && response.body().error != null)) {
                        mView.failMessage("設定更新に失敗しました");
                        return;
                    }

                    mView.registerSuccess();
                    return;
                }, throwable -> {
                    mView.failMessage("設定更新に失敗しました");
                    return;
                });
    }
}
