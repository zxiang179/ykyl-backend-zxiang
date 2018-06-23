package edu.ecnu.ykyl.web.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ecnu.ykyl.entity.User;
import edu.ecnu.ykyl.service.UserService;

@Component
public class MyRealm extends AuthorizingRealm{
	
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		return null;
	}

	/**
	 * 用户认证：执行subject.login()时进行身份认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String phone = (String)token.getPrincipal();
		
		User user = userService.findUserByPhone(phone);
		try {
			if(user!=null){
				AuthenticationInfo authcInfo =
						new SimpleAuthenticationInfo(user.getPhone(), user.getPassword(),"XX");
				return authcInfo;
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
