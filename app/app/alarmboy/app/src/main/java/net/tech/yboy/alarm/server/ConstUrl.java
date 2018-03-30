package net.tech.yboy.alarm.server;

/**
 * Created by manabu on 2018/03/20.
 */

public class ConstUrl {
    public static final String API_HOST = "http://localhost:8080/";
    public static final String API_USER_REGISTER = "/alarm-api/v1/user/regsiter";
    public static final String API_DEVICE_TOKEN_REGISTER = "/alarm-api/v1/user/devicetoken";
    public static final String API_ALARM_DATA = "/alarm-api/v1/user/alarm";
    public static final String API_ALARM_DATA_REGISTER = "/alarm-api/v1/user/alarm/register";

    public static final String EKI_API_HOST = "https://api.ekispert.jp/";
    public static final String EKI_API_STATION_LIGHT = "/v1/json/station/light?key={apikey}";
    public static final String EKI_API_STATION_INFO = "/v1/json/station/info?key={apikey}&type=rail";
}
