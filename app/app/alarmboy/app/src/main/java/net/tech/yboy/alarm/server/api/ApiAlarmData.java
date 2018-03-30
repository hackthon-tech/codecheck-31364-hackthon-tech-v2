package net.tech.yboy.alarm.server.api;

import net.tech.yboy.alarm.server.ConstUrl;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by manabu on 2018/03/20.
 */

public class ApiAlarmData {

    public interface Api {
        @GET(ConstUrl.API_ALARM_DATA)
        Single<Response<ResGet>> get(@Header("uid") String uid);
    }

    public static class Setting {
        public boolean alarm;
        public String wakeUpTime;
        public String departureStation;
        public String arrivalStation;
        public String departureStationCode;
        public String arrivalStationCode;
        public String company;
    }

    public static class ResGet {
        public String result;
        public String error;
        public Setting setting;
    }
}

