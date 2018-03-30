package net.tech.yboy.alarm.model;

import net.tech.yboy.alarm.server.InstanceRetrofit;
import net.tech.yboy.alarm.server.api.ApiAlarmDataRegister;
import net.tech.yboy.alarm.server.api.ApiUserRegister;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * Created by manabu on 2018/03/21.
 */

public class SettingRegisterData {

    public Single<Response<ApiAlarmDataRegister.ResPost>> register(
            String uid,
            boolean alarm,
            String departureStation,
            String arrivalStation,
            String wakeUpTime,
            String departureStationCode,
            String arrivalStationCode,
            String company) {
        ApiAlarmDataRegister.ReqPost req = new ApiAlarmDataRegister.ReqPost();
        req.setting = new ApiAlarmDataRegister.Setting();
        req.setting.alarm = alarm;
        req.setting.arrivalStation = arrivalStation;
        req.setting.departureStation = departureStation;
        req.setting.wakeUpTime = wakeUpTime;
        req.setting.arrivalStationCode = arrivalStationCode;
        req.setting.departureStationCode = departureStationCode;
        req.setting.company = company;
        return InstanceRetrofit.getRetrofit().create(ApiAlarmDataRegister.Api.class).register(uid, req);
    }
}
