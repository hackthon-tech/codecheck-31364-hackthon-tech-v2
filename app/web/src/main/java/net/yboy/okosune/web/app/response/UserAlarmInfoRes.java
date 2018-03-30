package net.yboy.okosune.web.app.response;

public class UserAlarmInfoRes extends UserIdRegisterRes {

	private String result;
	private UserAlarmSettingRes setting;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public UserAlarmSettingRes getSetting() {
		return setting;
	}

	public void setSetting(UserAlarmSettingRes setting) {
		this.setting = setting;
	}
	
}
