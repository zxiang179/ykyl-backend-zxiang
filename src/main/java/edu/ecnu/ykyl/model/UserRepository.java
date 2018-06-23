package edu.ecnu.ykyl.model;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import edu.ecnu.ykyl.entity.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * 根据phone查找pkRoomId
	 * @param phone
	 * @return
	 */
	@Query(value="SELECT pk_room_id FROM t_user WHERE phone=?1",nativeQuery=true)
	Long getPKRoomIdByPhone(String phone);
	
	/**
	 * 根据phone修改用户的pkRoomId
	 * @param PKRoomID
	 * @param phone
	 */
	@Modifying
	@Query(value="UPDATE t_user SET pk_room_id=?1 WHERE phone=?2",nativeQuery=true)
	void updatePKRoomIdByPhone(Long PKRoomID,String phone);
	
	// 登录
	@Query("from User where phone = ?1 and password = ?2")
	User findUserByPhoneAndPassword(String phone, String password);

	// 验证电话号码是否重复
	@Query("from User where phone =?1")
	User findUserByPhone(String phone);
	
	//通过id找user
	@Query("from User where id =?1")
	User findUserById(Long id);	

	// 注册
	@Modifying
	@Query(value = "insert into t_user(phone,password) values(?1,?2)", nativeQuery = true)
	void newRegister(String phone, String password);

	// 更新
	// UPDATE t_user SET NAME='t'
	// ,school='ECNU',gender='male',grade='postgrade' WHERE id='2'
	/*@Modifying
	@Query(value = "update t_user set name=?1,school=?2,gender=?3,grade=?4,school_district=?5"
			+ " where phone=?6", nativeQuery = true)
	void updateUser(String name, String school, String gender, String grade,
			String school_district, String phone);*/
	
	@Modifying
	@Query(value="update t_user set name=?1,school=?2,gender=?3,grade=?4,school_district=?5"
			+ " where phone=?6",nativeQuery=true)
	void updateUser(String name,String school,String gender,
			String grade,String school_district,String phone);
	
	@Modifying
	@Query(value="UPDATE t_user SET pkrecord=?1 ,pkrate = ?2 WHERE id = ?3",nativeQuery=true)
	void updateUserPKHist(String pkHist,String pkrate,long uid);
	
	
	//返回新注册用户的id号
	@Query(value="select max(id) from t_user",nativeQuery=true)
    long countUser();
	
	//验证用户名是否重复
	@Query("from User where name =?1")
	User findUserByName(String name);	

}
