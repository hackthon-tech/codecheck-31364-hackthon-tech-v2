package net.tech.yboy.alarm.presenter;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;

import net.tech.yboy.alarm.model.DeviceTokenRegisterData;
import net.tech.yboy.alarm.model.GoogleHomeVoiceData;
import net.tech.yboy.alarm.model.UserRegisterData;
import net.tech.yboy.alarm.util.PrefUtil;
import net.tech.yboy.alarm.util.UidUtil;
import net.tech.yboy.alarm.view.MainUseCase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by manabu on 2018/03/20.
 */
public class UserRegisterPresenter implements MainUseCase.Presenter {

    private MainUseCase.View mView;

    private UserRegisterData mUserRegisterData;

    private DeviceTokenRegisterData mDeviceTokenRegisterData;

    private GoogleHomeVoiceData mGoogleHomeVoiceData;

    public UserRegisterPresenter(MainUseCase.View view) {
        mView = view;
        mUserRegisterData = new UserRegisterData();
        mDeviceTokenRegisterData = new DeviceTokenRegisterData();
        mGoogleHomeVoiceData = new GoogleHomeVoiceData();
    }

    @Override
    public void status(Context context) {

        boolean isRegister = PrefUtil.isRegister(context);
        boolean isDevice = PrefUtil.isSendDeviceToken(context);

        if (!isRegister || !isDevice) {
            if (!isRegister) {
                mView.updateRegisterStatus("登録ボタンを押してユーザ登録をしてください");
                return;
            }
            if (!isDevice) {
                mView.updateRegisterStatus("登録ボタンを押してデバイストークンを登録してください");
                return;
            }
        } else {
            boolean isSetting = PrefUtil.isSetting(context);
            if (!isSetting) {
                mView.updateSettingStatus("設定ボタンから設定をおこなってください");
                return;
            }

            boolean isSettingNotify = PrefUtil.isSettingNotify(context);
            if (!isSettingNotify) {
                mView.updateSettingStatus("遅延情報の受取がOFFになっています");
                return;
            }

            mView.updateSettingStatus("お出かけ前に遅延情報がある場合、GoogleHomeからお知らせします");
            return;
        }
    }

    @Override
    public void register(Context context) {

        boolean isRegister = PrefUtil.isRegister(context);
        boolean isDevice = PrefUtil.isSendDeviceToken(context);

        if (isRegister && isDevice) {
            status(context);
            return;
        }

        mView.showProgress();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }

                if (!isRegister) {
                    register(context, isDevice, UidUtil.createUid());
                    return;
                }

                deviceToken(context, false);
                return;
            }
        }).start();

    }

    @Override
    public void googleHome(Context context) {

        mView.showProgress();

        mGoogleHomeVoiceData.get(context, "接続は問題ありません", false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(mView::hideProgress)
                .subscribe(response -> {
                    mView.showMessage("GoogleHomeの再生に成功しました");
                    return;
                }, throwable -> {
                    mView.showMessage("GoogleHomeの再生に失敗しました");
                    return;
                });

    }

    private void register(final Context context, boolean isDevice, final String id) {

        if (id == null) {
            mView.hideProgress();
            mView.showMessage("初期登録に失敗しました");
            return;
        }

        mUserRegisterData.register(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(mView::hideProgress)
                .subscribe(response -> {
                    if (response.body() == null || (response.body() != null && response.body().error != null)){
                        status(context);
                        mView.showMessage("初期登録に失敗しました");
                        return;
                    }

                    PrefUtil.setRegister(context, true);
                    PrefUtil.setUid(context, id);

                    if (isDevice) {
                        status(context);
                        mView.showMessage("初期登録に成功しました");
                        return;
                    } else {
                        deviceToken(context, true);
                    }
                }, throwable -> {
                    mView.showMessage("初期登録に失敗しました");
                    return;
                });
    }

    private void deviceToken(final Context context, boolean init) {
        String deviceToken = PrefUtil.getDeviceToken(context);
        if (deviceToken == null) {
            deviceToken = FirebaseInstanceId.getInstance().getToken();
            if (deviceToken == null) {
                mView.showMessage("デバイストークンの登録に失敗しました");
                mView.hideProgress();
                return;
            }
        }

        mDeviceTokenRegisterData.register(mView.getToken(), deviceToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(mView::hideProgress)
                .subscribe(response -> {
                    if (response.body() == null || (response.body() != null && response.body().error != null)) {
                        status(context);
                        if (init) {
                            mView.showMessage("初期登録に失敗しました");
                        } else {
                            mView.showMessage("デバイストークンの登録に失敗しました");
                        }
                        return;
                    }

                    PrefUtil.setDeviceToken(context, null);
                    PrefUtil.setSendDeviceToken(context, true);

                    status(context);
                    if (init) {
                        mView.showMessage("初期登録に成功しました");
                    } else {
                        mView.showMessage("デバイストークンの登録に成功しました");
                    }
                    return;
                }, throwable -> {
                    status(context);
                    if (init) {
                        mView.showMessage("初期登録に失敗しました");
                    } else {
                        mView.showMessage("デバイストークンの登録に失敗しました");
                    }
                    return;
                });

    }

}
