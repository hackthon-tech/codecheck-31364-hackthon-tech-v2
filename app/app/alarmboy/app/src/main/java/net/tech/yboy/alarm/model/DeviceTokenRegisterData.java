package net.tech.yboy.alarm.model;

import android.util.Log;

import net.tech.yboy.alarm.server.api.ApiDeviceTokenRegister;
import net.tech.yboy.alarm.server.InstanceRetrofit;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * Created by manabu on 2018/03/20.
 */
public class DeviceTokenRegisterData {

    public Single<Response<ApiDeviceTokenRegister.ResPost>> register(String uid, String deviceToken) {
        ApiDeviceTokenRegister.ReqPost req = new ApiDeviceTokenRegister.ReqPost();
        req.deviceToken = deviceToken;
        return InstanceRetrofit.getRetrofit().create(ApiDeviceTokenRegister.Api.class).register(uid, req);
    }
}
