package net.tech.yboy.alarm.server.api;

import net.tech.yboy.alarm.server.ConstUrl;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by manabu on 2018/03/22.
 */

public class EkiApiStationLightSingle {

    public interface Api {
        @GET(ConstUrl.EKI_API_STATION_LIGHT)
        Single<Response<ResGet>> get(@Query("name") String name);
    }

    public static class Prefecture {
        public String Name;
        public String code;
    }

    public static class Station {
        public String Name;
        public String code;
        public String Type;
        public String Yomi;
    }

    public static class Point {
        public Station Station;
    }

    public static class ResultSet {
        public String apiVersion;
        public String engineVersion;
        public Point Point;
    }

    public static class ResGet {
        public ResultSet ResultSet;
        public Prefecture Prefecture;
    }
}
