package edu.ecnu.ykyl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ecnu.ykyl.model.SelfInfoRepository;

@Service
public class SelfInfoService {

	@Autowired
	private SelfInfoRepository repo;
	
	public void updateInfoName(String username,String phone){
		repo.updateInfoName(username,phone);
	}
	
	public void updateInfoGender(String gender,String phone){
		repo.updateInfoGender(gender,phone);
	}
	
	public void updateInfoGrade(String grade,String phone){
		repo.updateInfoGrade(grade,phone);
	}

	public void updateInfoSchoolAndDistrict(String school, String school_district, String phone) {
		repo.updateInfoSchoolAndDistrict(school,school_district,phone);
	}
	
	public String checkUserPassword(String phone){
		return repo.checkUserPassword(phone);
	}

	public void updateUserPassword(String password, String phone) {
		repo.updateUserPassword(password,phone);	
	}
	
	
}
