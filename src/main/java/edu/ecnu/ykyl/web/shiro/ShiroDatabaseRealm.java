package edu.ecnu.ykyl.web.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ecnu.ykyl.entity.Constants;
import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.service.UserService;

public class ShiroDatabaseRealm extends AuthorizingRealm{
	
	@Autowired
	private UserService userService;

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)token;
		String username = usernamePasswordToken.getUsername();
		String password = String.valueOf(usernamePasswordToken.getPassword());
		User loginUser =userService.findUserByPhone(username);
		//可以通过与当前执行的Subject交互来获取Session
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(true);

		if(loginUser==null){
			throw new UnknownAccountException("用户名不存在");
		}
		if(!password.equals(loginUser.getPassword())){
			throw new IncorrectCredentialsException("密码不正确");
		}
		
		session.setAttribute(Constants.LOGIN_USER, loginUser);
		return new SimpleAuthenticationInfo(loginUser.getPhone(), 
				loginUser.getPassword(),
				ByteSource.Util.bytes(loginUser.getPhone()), getName());
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

}
