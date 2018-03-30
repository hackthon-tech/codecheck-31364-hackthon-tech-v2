package net.tech.yboy.alarm.server.api;

import net.tech.yboy.alarm.server.ConstUrl;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by manabu on 2018/03/22.
 */

public class EkiApiStationInfoSingle {

    public interface Api {
        @GET(ConstUrl.EKI_API_STATION_INFO)
        Single<Response<ResGet>> get(@Query("code") String code);
    }

    public static class Corporation {
        public String Name;
    }

    public static class Information {
        public Corporation Corporation;
    }

    public static class ResultSet {
        public String apiVersion;
        public String engineVersion;
        public Information Information;
        public String Type;
    }

    public static class ResGet {
        public EkiApiStationInfoSingle.ResultSet ResultSet;
    }
}
