package net.yboy.okosune.web.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.yboy.okosune.web.app.form.UserAlarmForm;
import net.yboy.okosune.web.app.form.UserDeviceTokenRegisterForm;
import net.yboy.okosune.web.app.form.UserRegisterForm;
import net.yboy.okosune.web.app.response.UserAlarmInfoRes;
import net.yboy.okosune.web.app.response.UserAlarmSettingRes;
import net.yboy.okosune.web.app.response.UserIdRegisterRes;
import net.yboy.okosune.web.domain.data.dto.UserAlarmDto;
import net.yboy.okosune.web.domain.service.UserRegisterService;
import net.yboy.okosune.web.domain.service.UserService;
import net.yboy.okosune.web.util.OkiteyoUtils;

@RestController
@RequestMapping(value="/v1/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserRegisterService userRegisterService;
	
	/**
	 * ユーザ登録
	 * @param encoding
	 * @return
	 */
	@RequestMapping(value="/regsiter", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public UserIdRegisterRes saveUserRegister(@RequestBody UserRegisterForm form) {
		UserIdRegisterRes res = new UserIdRegisterRes();
		
		Boolean executeUserId = userRegisterService.existUserId(form.getId());
		System.out.println(executeUserId);
		if (executeUserId) {
			res.setResult("bad");
			return res;
		}

		// 登録処理
		try {
			userRegisterService.saveUserid(form.getId());
			res.setResult("ok");
		} catch (ParseException e) {
			e.printStackTrace();
			res.setResult("bad");
		}
		return res;
	}
	
	/**
	 * デバイストークンの登録
	 * @param encoding
	 * @return
	 */
	@RequestMapping(value="/devicetoken", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public UserIdRegisterRes saveDeviceToken(@RequestHeader("uid") String uid, @RequestBody UserDeviceTokenRegisterForm form) {
		UserIdRegisterRes res = new UserIdRegisterRes();
		
		System.out.println("devicetoken"  + form.getDeviceToken());
		System.out.println("uid"  + uid);
		
		Boolean executeUserId = userRegisterService.existUserId(uid);
		System.out.println(executeUserId);
		if (!executeUserId) {
			res.setResult("bad");
			return res;
		}
		
		userRegisterService.saveDeviceToken(uid, form.getDeviceToken());
		res.setResult("ok");
        return  res;
	}
	
	/**
	 * アラーム情報の取得
	 * @param encoding
	 * @return
	 */
	@RequestMapping(value="/alarm", method=RequestMethod.GET)
	public UserIdRegisterRes saveAlarmData(@RequestHeader("uid") String uid) {
		UserIdRegisterRes res = new UserIdRegisterRes();
		Boolean executeUserId = userRegisterService.existUserId(uid);
		System.out.println(executeUserId);
		if (!executeUserId) {
			res.setResult("bad");
			return res;
		}
		
		UserAlarmDto alarmDto = userService.getUserAlarm(uid);
		
		if(alarmDto == null) {
			res.setResult("bad");
			return res;
		}
		
		UserAlarmInfoRes res2 = new UserAlarmInfoRes();
		UserAlarmSettingRes setting = new UserAlarmSettingRes();
		
		if (alarmDto.getAlarm() != null) {
			setting.setAlarm(alarmDto.getAlarm());			
		}
		
		if(alarmDto.getWakeUpTime() != null) {
			setting.setWakeUpTime( new SimpleDateFormat("kk:mm").format(alarmDto.getWakeUpTime()));
		}
		if(alarmDto.getArrivalStation() != null) {
			setting.setArrivalStation(alarmDto.getArrivalStation());			
		}
		if(alarmDto.getDepartureStation() != null) {
			setting.setDepartureStation(alarmDto.getDepartureStation());
		}
		if(alarmDto.getDepartureStationCode() != null) {
			setting.setDepartureStationCode(alarmDto.getDepartureStationCode());
		}
		if(alarmDto.getDepartureStationCode() != null) {
			setting.setArrivalStationCode(alarmDto.getDepartureStationCode());
		}
		if(alarmDto.getCompany() != null) {
			setting.setCompany(alarmDto.getCompany());
		}
		
		
		
		res2.setResult("OK");
		res2.setSetting(setting);
		
		return res2;
	}
	
	/**
	 * アラーム情報の登録
	 * @param encoding
	 * @return
	 */
	@RequestMapping(value="/alarm/register", method=RequestMethod.POST)
	public UserIdRegisterRes getAlarmRegster(@RequestHeader("uid") String uid, @RequestBody UserAlarmForm form) {
		UserIdRegisterRes res = new UserIdRegisterRes();
		Boolean executeUserId = userRegisterService.existUserId(uid);
		System.out.println(executeUserId);
		if (!executeUserId) {
			res.setResult("bad");
			return res;
		}
		
		try {
			userRegisterService.saveAlarmInfo(uid, form.getSetting());
			res.setResult("ok");
		} catch (ParseException e) {
			System.out.println(e);
			res.setResult("bat");
		}
		return res; 
	}
}
