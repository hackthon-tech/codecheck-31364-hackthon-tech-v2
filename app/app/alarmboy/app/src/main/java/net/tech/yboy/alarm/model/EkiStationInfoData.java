package net.tech.yboy.alarm.model;

import net.tech.yboy.alarm.server.InstanceRetrofit;
import net.tech.yboy.alarm.server.api.EkiApiStationInfo;
import net.tech.yboy.alarm.server.api.EkiApiStationInfoSingle;
import net.tech.yboy.alarm.server.api.EkiApiStationLight;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * Created by manabu on 2018/03/21.
 */

public class EkiStationInfoData {

    public Single<Response<EkiApiStationInfo.ResGet>> get(
            String code) {
        return InstanceRetrofit.getEkiRetrofit().create(EkiApiStationInfo.Api.class).get(code);
    }

    public Single<Response<EkiApiStationInfoSingle.ResGet>> singleGet(
            String code) {
        return InstanceRetrofit.getEkiRetrofit().create(EkiApiStationInfoSingle.Api.class).get(code);
    }

}
