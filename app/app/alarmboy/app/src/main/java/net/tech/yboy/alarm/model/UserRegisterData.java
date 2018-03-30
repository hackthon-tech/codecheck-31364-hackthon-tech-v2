package net.tech.yboy.alarm.model;

import android.util.Log;

import net.tech.yboy.alarm.server.api.ApiUserRegister;
import net.tech.yboy.alarm.server.InstanceRetrofit;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * Created by manabu on 2018/03/20.
 */
public class UserRegisterData {

    public Single<Response<ApiUserRegister.ResPost>> register(String id) {
        ApiUserRegister.ReqPost req = new ApiUserRegister.ReqPost();
        req.id = id;
        return InstanceRetrofit.getRetrofit().create(ApiUserRegister.Api.class).register(req);
    }
}
