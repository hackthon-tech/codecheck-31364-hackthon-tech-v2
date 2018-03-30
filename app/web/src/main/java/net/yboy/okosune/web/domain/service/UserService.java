package net.yboy.okosune.web.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.yboy.okosune.web.domain.data.dto.UserAlarmDto;
import net.yboy.okosune.web.domain.model.UserModel;
import net.yboy.okosune.web.domain.repo.UserRepo;
import net.yboy.okosune.web.domain.repo.UserInfoGetRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserInfoGetRepo userGetRepo;
	


	public UserModel getUser() {
		return userRepo.getUsermodel();
	}

	public UserAlarmDto getUserAlarm(String uid) {
		return userGetRepo.getAlarmInfo(uid);
	}
}
