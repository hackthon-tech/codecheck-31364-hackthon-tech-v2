package net.tech.yboy.alarm.model;

import net.tech.yboy.alarm.server.InstanceRetrofit;
import net.tech.yboy.alarm.server.api.ApiAlarmData;
import net.tech.yboy.alarm.server.api.ApiAlarmDataRegister;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * Created by manabu on 2018/03/21.
 */

public class SettingData {

    public Single<Response<ApiAlarmData.ResGet>> get(
            String uid) {
        return InstanceRetrofit.getRetrofit().create(ApiAlarmData.Api.class).get(uid);
    }
}
