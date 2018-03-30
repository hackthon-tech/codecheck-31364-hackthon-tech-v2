package net.yboy.okosune.web.app.form;

import java.io.Serializable;

public class UserAlarmForm implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UserRegisterSettingForm setting;

	
	public UserRegisterSettingForm getSetting() {
		return setting;
	}

	public void setSetting(UserRegisterSettingForm setting) {
		this.setting = setting;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
