package net.tech.yboy.alarm.model;

import net.tech.yboy.alarm.server.InstanceRetrofit;
import net.tech.yboy.alarm.server.api.ApiAlarmData;
import net.tech.yboy.alarm.server.api.EkiApiStationLight;
import net.tech.yboy.alarm.server.api.EkiApiStationLightSingle;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * Created by manabu on 2018/03/21.
 */

public class EkiStationLightData {

    public Single<Response<EkiApiStationLight.ResGet>> get(
            String name) {
        return InstanceRetrofit.getEkiRetrofit().create(EkiApiStationLight.Api.class).get(name);
    }

    public Single<Response<EkiApiStationLightSingle.ResGet>> singleGet(
            String name) {
        return InstanceRetrofit.getEkiRetrofit().create(EkiApiStationLightSingle.Api.class).get(name);
    }

}
