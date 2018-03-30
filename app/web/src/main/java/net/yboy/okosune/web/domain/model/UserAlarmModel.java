package net.yboy.okosune.web.domain.model;

import java.util.Date;


public class UserAlarmModel {
	
	private Boolean alarm; // アラームの有無
	private String wakeUpWeek; // 設定する曜日
	private Integer homeFormStationMinute;  // 家から駅までの距離
	private Date departureTime; // 出発駅時間
	private Date arrivalTime; //到着駅時間
	private String departureStation; // 出発駅名
	private String arrivalStation; // 到着駅名
	
	public Boolean getAlarm() {
		return alarm;
	}
	public void setAlarm(Boolean alarm) {
		this.alarm = alarm;
	}
	public String getWakeUpWeek() {
		return wakeUpWeek;
	}
	public void setWakeUpWeek(String wakeUpWeek) {
		this.wakeUpWeek = wakeUpWeek;
	}
	public Integer getHomeFormStationMinute() {
		return homeFormStationMinute;
	}
	public void setHomeFormStationMinute(Integer homeFormStationMinute) {
		this.homeFormStationMinute = homeFormStationMinute;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
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

}
