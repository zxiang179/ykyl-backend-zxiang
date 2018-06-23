package edu.ecnu.ykyl.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.User;

@Repository
@Transactional
public interface SelfInfoRepository extends JpaRepository<User, Long> {
	
	
	//更新用户名
	@Modifying
	@Query(value="update t_user set name=?1 where phone=?2",nativeQuery=true)
	void updateInfoName(String username,String phone);
	
	//更新用户性别
	@Modifying
	@Query(value="update t_user set gender=?1 where phone=?2",nativeQuery=true)
	void updateInfoGender(String gender,String phone);
	
	//更新用户年级
	@Modifying
	@Query(value="update t_user set grade=?1 where phone=?2",nativeQuery=true)
	void updateInfoGrade(String grade,String phone);

	//更新用户学校及学校所在区
	@Modifying
	@Query(value="update t_user set school=?1,school_district=?2 where phone=?3",nativeQuery=true)
	void updateInfoSchoolAndDistrict(String school, String school_district, String phone);
	
	//查询用户密码
	@Query(value="select password from t_user where phone=?1",nativeQuery=true)
    String checkUserPassword(String phone);

	//更新用户密码
	@Modifying
	@Query(value="update t_user set password=?1 where phone=?2",nativeQuery=true)
	void updateUserPassword(String password,String phone);
	
	
}
