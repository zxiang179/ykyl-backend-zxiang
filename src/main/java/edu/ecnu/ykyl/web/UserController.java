package edu.ecnu.ykyl.web;

import static edu.ecnu.ykyl.Constant.API_USER;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.replyentity.ReplyPKUser;
import edu.ecnu.ykyl.service.UserService;

@RestController
@RequestMapping(API_USER)
public class UserController {
	
    @Autowired
    private UserService service;
    
    /**
     * 统计用户的答题数量
     * @return
     */
    @RequestMapping(value="/recordAnsweredQuestionCount",method= GET)
    public Map<String,Integer> recordAnsweredQuestionCount(HttpServletRequest request){
    	Map<String,Integer> map = new HashMap<String,Integer>();
    	HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.LOGIN_USER);
    	long uid = user.getId();
    	map = service.findTotalAnsweredQuestions(uid);
    	return map;
    }
    
    //http://localhost:8888/api/user/loginByShiro?phone=13127879084&password=123456
    //http://localhost:8888/html/websocket.html
    @RequestMapping(value="/loginByShiro",method= GET)
    public String loginByShiro(String phone,String password,HttpServletRequest request){
    	try {
    		Subject subject = SecurityUtils.getSubject();
    		UsernamePasswordToken token = new UsernamePasswordToken(phone,password);
			subject.login(token);
			Session session=subject.getSession();
			System.out.println("sessionId:"+session.getId());
			System.out.println("sessionHost:"+session.getHost());
			System.out.println("sessionTimeout:"+session.getTimeout());
			System.out.println("sesssion User:"+session.getAttribute(Constants.LOGIN_USER));
			session.setAttribute("info", "Session的数据");
			
			User user = new User();
			user = (User)session.getAttribute(Constants.LOGIN_USER);
			ReplyPKUser pkUser = new ReplyPKUser();
			HttpSession httpSession = request.getSession(true);
			pkUser = userToPkUser(user);
			httpSession.setAttribute(Constants.SESSION_USER, pkUser);
			
			return "login success";
		} catch (Exception e) {
			e.printStackTrace();
			return "login failed";
		}
    	
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/user", method = GET)
    @ResponseBody
    public List<User> user() {
    	System.out.println("user");
        return service.getUser();
    }
    
    @RequestMapping(value="/register",method=GET)
    @ResponseBody
    //register
    public User register(String phone,String password,String name,
    		String grade,String schoolDistrict,String school,String gender){
    	User user = new User();
    	user.setPhone(phone);
    	user.setName(name);
    	user.setPassword(password);
    	user.setGrade(grade);
    	user.setSchool(school);
    	user.setGender(gender);
    	user.setSchoolDistrict(schoolDistrict);
    	service.register(user);
    	return user;
    }
    
    //new Register
    @RequestMapping(value="/newRegister",method=GET)
    @ResponseBody
    public String newRegister(String phone,String password,HttpServletRequest request){
    	HttpSession session = request.getSession(true);
    	boolean result = service.validatePhoneNumber(phone, password);
    	if(result==true){
    		//没有重复用户
    		long userNumberTemp=service.getUserNumber();
    		String userNumber=Long.toString(userNumberTemp);
    		User user = new User();
        	user.setPassword(password);
        	user.setPhone(phone);
        	session.setAttribute(Constants.LOGIN_USER, user);
    		return userNumber;
    	}else{
    		String userNumber="false";
    		return userNumber;
    	}
    }
    
    
    @RequestMapping(value="/updateUser",method=GET)
    @ResponseBody
    public String updateUser(String name,String school,String gender,
			String grade,String school_district,HttpServletRequest request){
    	
    	String result=""+service.ifExist(name);
    	HttpSession session = request.getSession();
    	User user = (User)session.getAttribute(Constants.LOGIN_USER);
    	String phone = user.getPhone();
    	if(result.equals("true")){
	    	service.updateUser(name, school, gender, grade, school_district, phone);
	    	User uptoDateUser=service.findUserByPhone(phone);
	    	session.setAttribute(Constants.LOGIN_USER, uptoDateUser); 	
	    	System.out.println("update success===========");
	    	return "update success";
    	}else{
    		System.out.println("update failure===========");
    		return "update failure";
    	}
    }
    
    /**
	 * 实现用户到PKUser的转换
	 * @param user
	 * @return
	 */
	public ReplyPKUser userToPkUser(User user) {
		ReplyPKUser pkUser = new ReplyPKUser();
		pkUser.setName(user.getName());
		pkUser.setGrade(user.getGrade());
		pkUser.setSchool(user.getSchool());
		pkUser.setAvatar(user.getAvatar());
		pkUser.setPkRate(user.getPKRate());
		pkUser.setPKInfo(user.getPKRecord());
		return pkUser;
	}
    
}

   