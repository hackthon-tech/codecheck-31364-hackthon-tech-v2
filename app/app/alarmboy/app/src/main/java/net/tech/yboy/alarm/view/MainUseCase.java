package net.tech.yboy.alarm.view;

import android.content.Context;

public interface MainUseCase {

    interface View extends BaseViewUserCase {
        void showProgress();
        void hideProgress();
        void showMessage(String message);
        void updateRegisterStatus(String status);
        void updateSettingStatus(String status);
    }

    interface Presenter {
        void status(Context context);
        void register(Context context);
        void googleHome(Context context);
    }
}
