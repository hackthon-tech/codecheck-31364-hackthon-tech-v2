package net.yboy.okosune.web.domain.data.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import net.yboy.okosune.web.domain.data.dto.UserAlarmDto;

@Mapper
public interface UserMapper {
	// サンプル
	@Select("SELECT * FROM userMaster")
    UserAlarmDto findByState();
	
	// ユーザ情報取得
	@Select("SELECT * FROM userMaster WHERE objectId = #{uid}")
    UserAlarmDto getUserAlarmInfo(@Param("uid") String uid);
	
	// ユーザ情報の確認
	@Select("SELECT count(objectId) FROM userMaster WHERE objectId= #{id}")
    int selectUserId(String id);
	
	// デバイストークンの更新
	@Update("UPDATE userMaster SET deviceToken = #{deviceToken} WHERE objectId = #{uid}")
	void updateDeviceToken(@Param("uid") String uid, @Param("deviceToken") String deviceToken) ;
	
	// ユーザID情報の登録
	@Insert("INSERT INTO userMaster ( objectId, lastPushDate, alarm ) VALUES ( #{id}, #{lastPushDate}, false )")
	void insertUserId(@Param("id") String id, @Param("lastPushDate") Date lastPushDate);
	
	// ユーザのアラーム情報の更新
	@Update("UPDATE userMaster SET "
			+ "alarm = #{alarm}, "
			+ "wakeUpTime = #{wakeUpTime}, "			
			+ "departureStation = #{departureStation}, "
			+ "arrivalStation = #{arrivalStation}, "
			+ "departureStationCode = #{departureStationCode}, "
			+ "arrivalStationCode = #{arrivalStationCode}, "
			+ "company = #{company} "
			+ "WHERE objectId = #{uid}")
	
	void updateAlarmInfo(@Param("uid") String uid, 			
			@Param("alarm") Boolean alarm,
			@Param("wakeUpTime") Date wakeUpTime,
			@Param("departureStation") String departureStation,
			@Param("arrivalStation") String arrivalStation,
			@Param("departureStationCode") String departureStationCode,
			@Param("arrivalStationCode") String arrivalStationCode,
			@Param("company") String company) ;
	
}
