package net.yboy.okosune.web.domain.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.yboy.okosune.web.domain.data.dto.UserAlarmDto;
import net.yboy.okosune.web.domain.data.mapper.UserMapper;
import net.yboy.okosune.web.domain.model.UserModel;

@Component
public class UserRepo {
	
	@Autowired
	UserMapper mapper;
	

	public void saveUserIdRegister(String id) {
		return;
	}
	
	public UserModel getUsermodel() {
		UserAlarmDto userMaster = mapper.findByState();
		UserModel model = new UserModel();
		model.objectId = userMaster.getObjectId();
		return model;
	}


}
