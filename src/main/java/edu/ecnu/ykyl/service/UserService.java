package edu.ecnu.ykyl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.model.UserQuestionHistRepository;
import edu.ecnu.ykyl.model.UserRepository;
import edu.ecnu.ykyl.service.util.MyParser;

@Service
public class UserService extends MyParser{
	@Autowired
	private UserRepository repo;
	@Autowired
	private UserQuestionHistRepository userQuestionHistRepository;
	
	/**
	 * 更新用户的历史记录
	 * @param uid
	 */
	public void updatePKHist(long uid,double pkRate,Map<String,String> pkHistMap){
		String pkHist = null;
		String pkRateStr = String.valueOf(pkRate);
		JSONObject jsonObject = JSONObject.fromObject(pkHistMap);
		pkHist = jsonObject.toString();
		repo.updateUserPKHist(pkHist, pkRateStr, uid);
		System.out.println("updatePKHist success");
	}
	
	/**
	 * 查找用户的PK历史记录
	 * @param uid
	 * @return
	 */
	public Map<String,String> findPKHist(Long uid){
		User user = repo.findUserById(uid);
		String pkRecord = user.getPKRecord();
		Map<String, String> map = new HashMap<String,String>();		
		if(pkRecord==null){
			map.put("success", "0");
			map.put("fail", "0");
			map.put("Info", "还差30场晋级LV2");
			return map;
		}
		map = parserPKHist(pkRecord);
		return map;
	}
	
	/**
	 * 查找用户的PK历史记录Str
	 * @param uid
	 * @return
	 */
	public String findPKHistStr(Long uid){
		User user = repo.findUserById(uid);
		String pkRecordStr = user.getPKRecord();
		Map<String, String> map = new HashMap<String,String>();		
		if(pkRecordStr==null){
			return "";
		}
		return pkRecordStr;
	}
	
	/**
	 * 统计用户的答题数量
	 * @param uid
	 * @return
	 */
	public Map<String,Integer> findTotalAnsweredQuestions(long uid){
		int[] a = new int[50];
		Map<String,Integer> map = new HashMap<String,Integer>();
		String hist = userQuestionHistRepository.findSelectedHist(uid);
		int correct = 0;
		int wrong = 0;
		int count = 0;
		List<Map<String, Object>> histList = parserJsonHist(hist);
		System.out.println("=================");
		System.out.println(histList);
		System.out.println("=================");
		for (Map<String, Object> m : histList) {
			// 保存历史记录中每个知识点对应题目的数量
			a[(int) m.get("kpId") - 1]++;
			//获取答对和答错的题目
			if((int)m.get("status")==1)correct++;
			if((int)m.get("status")==2)wrong++;
		}
		
		for(int i=0;i<50;i++){
			count+=a[i];
		}
		map.put("count", count);
		map.put("correct", correct);
		map.put("wrong", wrong);
		return map;
	}
	
	/**
	 * 根据phone查找pkRoomId
	 * @param phone
	 * @return
	 */
	public Long getPKRoomIdByPhone(String phone){
		return repo.getPKRoomIdByPhone(phone);
	}
	
	/**
	 * 根据phone修改用户的pkRoomId
	 * @param PKRoomID
	 * @param phone
	 */
	public void updatePKRoomIdByPhone(Long PKRoomID,String phone){
		repo.updatePKRoomIdByPhone(PKRoomID, phone);
		
	}

	public List<User> getUser() {
		return repo.findAll();
	}

	public User findUserByPhone(String phone) {
		return repo.findUserByPhone(phone);
	}
	
	public User findUserById(String id){
    	return repo.findUserById(Long.valueOf(id));
    }

	// 用户登录
	public User login(String phone, String password) {
		return repo.findUserByPhoneAndPassword(phone, password);
	}

	// register
	public void register(User user) {
		repo.save(user);
	}

	// validate
	/*
	 * public boolean validatePhoneNumber(String phone){ User user = new User();
	 * user=repo.findUserByPhone(phone); if(user==null){ //没有重复用户 return true;
	 * }else{ //该用户已经存在 return false; } }
	 */

	// newRegister
	public boolean validatePhoneNumber(String phone, String password) {
		User user = new User();
		user = repo.findUserByPhone(phone);
		if (user == null) {
			// 没有重复用户
			repo.newRegister(phone, password);
			return true;
		} else {
			// 该用户已经存在
			return false;
		}
	}

	// userTotalNumber
	public long getUserNumber() {
		long userNumber = repo.count();
		return userNumber;
	}

	public void updateUser(String name, String school, String gender,
			String grade, String school_district, String phone) {
		repo.updateUser(name, school, gender, grade, school_district, phone);

	}
	
	public boolean ifExist(String name){
    	User user=repo.findUserByName(name);
    	if(user==null){
    		//没有重复用户名
    		return true;
    	}else{
    		//该用户名已经存在
    		return false;
    	}
    }

}
