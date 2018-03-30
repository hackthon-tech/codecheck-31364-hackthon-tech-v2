package net.tech.yboy.alarm.view;

import android.content.Context;

/**
 * Created by manabu on 2018/03/21.
 */

public interface SettingUseCase {

    interface View extends BaseViewUserCase {
        void showProgress();
        void hideProgress();
        void showMessage(String message);
        void failMessage(String message);
        void update(boolean alarm, String departureStation, String arrivalStation, String wakeUpTime, String departureStationCode, String arrivalStationCode, String company);
        void registerSuccess();
    }

    interface Presenter {
        void get(Context context);
        void register(Context context, boolean alarm, String departureStation, String arrivalStation, String wakeUpTime, String departureStationCode, String arrivalStationCode, String company);
    }
}
