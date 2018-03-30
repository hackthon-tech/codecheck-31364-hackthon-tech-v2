package net.yboy.okosune.web.domain.repo;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.yboy.okosune.web.domain.data.mapper.UserMapper;
import net.yboy.okosune.web.domain.model.UserModel;

@Component
public class UserIdRegisterRepo {

	@Autowired
	UserMapper mapper;

	/**
	 * ユーザの判定
	 * 
	 * @param id
	 * @return
	 */
	public Integer existUserId(String id) {
		return mapper.selectUserId(id);
	}

	/**
	 * デバイストークンの登録
	 * 
	 * @param uid
	 * @param deviceToken
	 */
	public void saveDeviceToken(String uid, String deviceToken) {
		mapper.updateDeviceToken(uid, deviceToken);
	}

	/**
	 * ユーザIDの登録
	 * @param id
	 */
	public void saveUserIdRegister(String id, Date startTime) {
		mapper.insertUserId(id, startTime);
	}

	/**
	 * アラーム情報を登録
	 * @param uid
	 * @param alarm
	 * @param wakeUpTime
	 * @param departureStation
	 * @param arrivalStation
	 * @param departureStationCode
	 * @param arrivalStationCode
	 * @param company
	 */
	public void saveAlarmInforregister(String uid,
			Boolean alarm,
			Date wakeUpTime,
			String departureStation,
			String arrivalStation,
			String departureStationCode,
			String arrivalStationCode,
			String company			
			) {
		
		
		mapper.updateAlarmInfo(uid, alarm, wakeUpTime, departureStation, arrivalStation, departureStationCode, arrivalStationCode, company);
	}
}
