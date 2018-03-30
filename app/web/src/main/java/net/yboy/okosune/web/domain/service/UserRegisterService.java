package net.yboy.okosune.web.domain.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.yboy.okosune.web.app.form.UserRegisterSettingForm;
import net.yboy.okosune.web.domain.repo.UserIdRegisterRepo;

@Service
public class UserRegisterService {

	@Autowired
	UserIdRegisterRepo userRepo;
	
	/**
	 * ユーザIDの有無判定
	 * @param id ユーザID
	 * @return ユーザIDの有無判定 true 存在しない,　false
	 */
	public Boolean existUserId(String id) {
		int count = userRepo.existUserId(id);
		System.out.println("existUserId : " + count);
		if (count > 0) return true;
		return false;
	}
	
	
	/**
	 * ユーザIDを登録
	 * @param id ユーザID
	 * @throws ParseException 
	 */
	public void saveUserid(String id) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String firestTime = "1990/01/01";
		userRepo.saveUserIdRegister(id, format.parse(firestTime));
	}
	
	/**
	 * デバイストークンを登録
	 * @param uid
	 * @param deviceToken
	 */
	public void saveDeviceToken(String uid, String deviceToken) {
		userRepo.saveDeviceToken(uid, deviceToken);
	}


	
	/**
	 * アラーム情報の登録
	 * @throws ParseException 
	 */
	public void saveAlarmInfo(String uid, UserRegisterSettingForm user) throws ParseException  {
		
//		private Boolean alarm; // アラームの有無
//		private Integer homeFormStationMinute; // 家から駅までの時間
//		private String wakeUpTime; // 起きる時間
//		private String departureStation; // 出発駅名
//		private String arrivalStation; // 到着駅名
//		private String departureStationCode; // 出発駅コード
//		private String arrivalStationCode; // 到着駅コード
//		private String company; // 路線名
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		String wakeUptime = "1990/01/01 "+ user.getWakeUpTime();
		
		userRepo.saveAlarmInforregister(uid, user.getAlarm(), format.parse(wakeUptime), user.getDepartureStation(), user.getArrivalStation(),
				user.getDepartureStationCode(), user.getArrivalStationCode(), user.getCompany());
	}
}
