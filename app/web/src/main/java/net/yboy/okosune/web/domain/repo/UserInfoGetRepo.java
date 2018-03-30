package net.yboy.okosune.web.domain.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.yboy.okosune.web.domain.data.dto.UserAlarmDto;
import net.yboy.okosune.web.domain.data.mapper.UserMapper;


@Component
public class UserInfoGetRepo {

	@Autowired
	UserMapper mapper;
	
	public UserAlarmDto getAlarmInfo(String uid) {
		return mapper.getUserAlarmInfo(uid);
	}

}
