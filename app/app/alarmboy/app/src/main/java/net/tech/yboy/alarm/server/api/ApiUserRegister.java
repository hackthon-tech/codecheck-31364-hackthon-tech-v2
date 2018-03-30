package net.tech.yboy.alarm.server.api;

import net.tech.yboy.alarm.server.ConstUrl;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by manabu on 2018/03/20.
 */

public class ApiUserRegister {

    public interface Api {
        @POST(ConstUrl.API_USER_REGISTER)
        Single<Response<ResPost>> register(@Body ReqPost body);
    }

    public static class ReqPost {
        public String id;
    }

    public static class ResPost {
        public String result;
        public String error;
    }
}

