package net.yboy.okosune.web.app.response;

import java.io.Serializable;

public class UserAlarmSettingRes implements Serializable{

	private static final long serialVersionUID = 1L;

	private Boolean alarm; // アラームの有無
	private String wakeUpTime; // 出発駅時間
	private String departureStation; // 出発駅名
	private String arrivalStation; // 到着駅名
	private String departureStationCode; // 出発駅コード
	private String arrivalStationCode; // 到着駅コード
	private String company; // 路線情報
	
	public Boolean getAlarm() {
		return alarm;
	}
	public void setAlarm(Boolean alarm) {
		this.alarm = alarm;
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
	
}
