package edu.ecnu.ykyl.web;

import static edu.ecnu.ykyl.Constant.API_SELFINFO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.replyentity.ReplyUser;
import edu.ecnu.ykyl.service.SelfInfoService;

@RestController
@RequestMapping(API_SELFINFO)
public class SelfInfoController {
	
	 @Autowired
	 private SelfInfoService service;

	    @RequestMapping(value = "/getUser", method = GET)
	    @ResponseBody
	    public ReplyUser getUser(HttpServletRequest request) {
	    	HttpSession session = request.getSession();
	    	User user = (User)session.getAttribute(Constants.LOGIN_USER);
	    	ReplyUser replyUser = new ReplyUser();
	    	replyUser.setGender(user.getGender());
	    	replyUser.setGrade(user.getGrade());
	    	replyUser.setId(user.getId());
	    	replyUser.setName(user.getName());
	    	replyUser.setPhone(user.getPhone());
	    	replyUser.setSchool(user.getSchool());
	        return replyUser;
	    }
	    
	    
	    @RequestMapping(value = "/updateUserName", method = GET)
	    @ResponseBody
	    public int updateUserName(HttpServletRequest request,String username) {
	    	int flag=1;
	    	HttpSession session = request.getSession();
	    	User user=(User)session.getAttribute(Constants.LOGIN_USER);
	    	String phone=user.getPhone();
	    	service.updateInfoName(username, phone);
	    	user.setName(username);
	        session.setAttribute(Constants.LOGIN_USER, user);
	        return flag;
	    }
	    
	    @RequestMapping(value = "/updateUserGender", method = GET)
	    @ResponseBody
	    public int updateUserGender(HttpServletRequest request,String gender) {
	    	int flag=1;
	    	HttpSession session = request.getSession();
	    	User user=(User)session.getAttribute(Constants.LOGIN_USER);
	    	String phone=user.getPhone();
	    	service.updateInfoGender(gender,phone);
	    	user.setGender(gender);
	    	session.setAttribute(Constants.LOGIN_USER, user);
	    	return flag;
	    }
	    
	    @RequestMapping(value = "/updateUserGrade", method = GET)
	    @ResponseBody
	    public int updateUserGrade(HttpServletRequest request,String grade) {
	    	int flag=1;
	    	HttpSession session = request.getSession();
	    	User user=(User)session.getAttribute(Constants.LOGIN_USER);
	    	String phone=user.getPhone();
	    	service.updateInfoGrade(grade, phone);
	    	user.setGrade(grade);
	    	session.setAttribute(Constants.LOGIN_USER, user);
	    	return flag;
	    }
	    
	    @RequestMapping(value = "/updateUserSchoolAndDistrict", method = GET)
	    @ResponseBody
	    public int updateUserSchoolAndDistrict(HttpServletRequest request,String school,String school_district) {
	    	int flag=1;
	    	HttpSession session = request.getSession();
	    	User user=(User)session.getAttribute(Constants.LOGIN_USER);
	    	String phone=user.getPhone();
	    	service.updateInfoSchoolAndDistrict(school,school_district, phone);
	    	user.setSchool(school);
	    	session.setAttribute(Constants.LOGIN_USER, user);
	    	return flag;
	    }
	    
	    @RequestMapping(value = "/checkUserPassword", method = GET)
	    @ResponseBody
	    public int checkUserPassword(HttpServletRequest request,String oldPassword,String newPassword) {
	    	int flag;
	    	String userPassword=null;
	    	HttpSession session = request.getSession();
	    	User user=(User)session.getAttribute(Constants.LOGIN_USER);
	    	String phone=user.getPhone();
	    	userPassword = service.checkUserPassword(phone);
	    	if(userPassword.equals(oldPassword)){
	    		flag=1;
	    		service.updateUserPassword(newPassword, phone);
	    		return flag;
	    	}
	    	else{
	    		flag=0;
	    		return flag;
	    	}    
	    }
	    
	    /*@RequestMapping(value = "/updateUserPassword", method = GET)
	    @ResponseBody
	    public int updateUserPassword(HttpServletRequest request,String password) {
	    	int flag=1;
	    	HttpSession session = request.getSession();
	    	ReplyUser replyUser=(ReplyUser)session.getAttribute("replyUser");
	    	String phone=replyUser.getPhone();
            service.updateUserPassword(password,phone);
            return flag;
	    	}*/
	    }    

