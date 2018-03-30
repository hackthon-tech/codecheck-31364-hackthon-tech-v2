package net.yboy.okosune.web.domain.data.dto;


import java.util.Date;


public class UserAlarmDto {

	private String objectId;
	private String deviceToken;
	private Boolean alarm;
	private Date wakeUpTime;
	private String departureStation;
	private String arrivalStation;
	private String departureStationCode;
	private String arrivalStationCode;
	private String company;
	
	
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	public Boolean getAlarm() {
		return alarm;
	}
	public void setAlarm(Boolean alarm) {
		this.alarm = alarm;
	}
	public Date getWakeUpTime() {
		return wakeUpTime;
	}
	public void setWakeUpTime(Date wakeUpTime) {
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
