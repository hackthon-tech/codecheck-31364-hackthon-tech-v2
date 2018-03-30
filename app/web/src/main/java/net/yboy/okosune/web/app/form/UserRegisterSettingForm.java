package net.yboy.okosune.web.app.form;

import java.io.Serializable;

public class UserRegisterSettingForm implements Serializable{

	// jsonの形
	/*
	  "alarm" : false,
	  "wakeUpWeek" : "1,2,4", 
	  "homeFormStationMinute" : "10",
	  "departureTime" : "8:00",
	  "arrivalTime" : "9:00",
	  "departureStation" : "駅名",
	  "arrivalStation" : "駅名",
	  "departureStationCode" : "2222",
	  "arrivalStationCode" : "2222",
	  "company" : "京王線,小田急線"
	*/

	private static final long serialVersionUID = 1L;

	private Boolean alarm; // アラームの有無
	private Integer homeFormStationMinute; // 家から駅までの時間
	private String wakeUpTime; // 起きる時間
	private String departureStation; // 出発駅名
	private String arrivalStation; // 到着駅名
	private String departureStationCode; // 出発駅コード
	private String arrivalStationCode; // 到着駅コード
	private String company; // 路線名
	
	public Boolean getAlarm() {
		return alarm;
	}
	public void setAlarm(Boolean alarm) {
		this.alarm = alarm;
	}
	public Integer getHomeFormStationMinute() {
		return homeFormStationMinute;
	}
	public void setHomeFormStationMinute(Integer homeFormStationMinute) {
		this.homeFormStationMinute = homeFormStationMinute;
	}
	public String getWakeUpTime() {
		return wakeUpTime;
	}
	public void setWakeUpTime(String wakeUpTime) {
		this.wakeUpTime = wakeUpTime;
	}
	public String getDepartureStation() {
		return departureStation;
	}
	public void setDepartureStation(String departureStation) {
		this.departureStation = departureStation;
	}
	public String getArrivalStation() {
		return arrivalStation;
	}
	public void setArrivalStation(String arrivalStation) {
		this.arrivalStation = arrivalStation;
	}
	public String getDepartureStationCode() {
		return departureStationCode;
	}
	public void setDepartureStationCode(String departureStationCode) {
		this.departureStationCode = departureStationCode;
	}
	public String getArrivalStationCode() {
		return arrivalStationCode;
	}
	public void setArrivalStationCode(String arrivalStationCode) {
		this.arrivalStationCode = arrivalStationCode;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
